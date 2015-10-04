package com.homeportal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import com.homeportal.bean.UserBean;
import com.homeportal.dao.IConnectionDao;
import com.homeportal.dao.impl.IConnectionDaoImpl;


public class BillingStatementGenerator 
{

	private static final IConnectionDao DAO = new IConnectionDaoImpl();
	private static final String HYPHEN = "-";
	private static final int ZERO = 0;
	private static final int THIRTYONE = 31;
	
	private static final String JASPER_FILES_PATH = MessageBundle.getProperty("jasperFilesPath");
	private static final String JASPER_NAME = MessageBundle.getProperty("jasperName");
	private static final String REPORT_LOCATION = MessageBundle.getProperty("reportLocation");
	
	
	
	
	 public static void main(String[] args) throws Exception
	 {
		 BillingStatementGenerator generator = new BillingStatementGenerator();
		 
		 Map<String, String> userIdRoleIdMap = new HashMap<String, String>();
		 Set<Entry<String, String>> entrySet = null;
		 double amount = 0.00;
		 double totalCost = 0.00;
		 
		 String userId = null;
		 String roleId = null;
		 
		 userIdRoleIdMap = generator.getUserIdAndRoleId();
		 
		 
		 Iterator it = userIdRoleIdMap.keySet().iterator();
		 
         while(it.hasNext())
         {
        	 userId = (String) it.next();
        	 roleId = userIdRoleIdMap.get(userId);
        	 
        	 
        	 // PAST DUE
        	amount = generator.getPastDueFromLedger(Integer.parseInt(userId), "2013-06");
        	// System.out.println("AMOUNT PAST DUE: " + amount);
        	 
        	// INSERT PAST DUE
			generator.insertPastDue(Integer.parseInt(userId), amount, "2013-06");  	
			 
			 // get water reading
        	 generator.getWaterReading(Integer.parseInt(userId));
        	 
        	 
        	 System.out.println("ROLE ID BEFORE GET ASSOC DUES: " + roleId);
        	 if (Integer.parseInt(roleId) == 3) // SHOULD BE DYNAMIC FLAG IN INPUT IF ENABLED
        	 {
        		 // get assoc dues
				 generator.getAssocDues(Integer.parseInt(userId));
			}
        	 
		}
         
		 
         	// UPDATE SERVICE REQUEST TABLE for water reading and assoc dues
			generator.updateServiceRequest();
			
			
			//GENERATE STATEMENT
			List<UserBean> beans = generator.getHomeOwnerList();
			 
	        for (UserBean u : beans) {
	             System.out.println("list : " + u.getId() + u.getUsername()+" "+" " + u.getOwner().concat("_").concat(String.valueOf(u.getHouseId())));
	             
	             generator.generateStatement(u.getId(), u.getUsername(), u.getOwner().concat("_").concat(String.valueOf(u.getHouseId())),
	            		 u.getOwnerName(), u.getOwnerAddress(), "07/01/2013", "07/31/2013");  //TODO: CHANGE THIS TO DYNAMIC DATES
	             
	            generator.insertStatementHistory(u.getId(), u.getUsername());
	        }
			
			
			
	 }
	 
	 
	 
	 public void test()
	 {
		 
		 
		 
		 
	 }
		 
		 
	 public void generateStatement(int userId, String statementDate, String homeportalName, String owner, 
			 String address, String startCutOff, String endCutOff)
	 {
	        
         Date d = new Date();   
         SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMMM dd,yyyy" );   
         Calendar cal = Calendar.getInstance();    
         cal.setTime(d);    
         cal.add(Calendar.DATE, 15);    
         String convertedDate=dateFormat.format(cal.getTime());    
         System.out.println("Date increase by one.."+convertedDate);
         
         
         
         SimpleDateFormat dateToFormat = new SimpleDateFormat("yyyy-MM-dd");
         String dateTo = dateToFormat.format(d);
         System.out.println("Date To.."+dateTo);
        
        Map params = new HashMap();
        params.put("start_co", startCutOff);
        params.put("end_co", endCutOff);
        params.put("user_id", userId);
        params.put("statementDate", statementDate);
        params.put("convertedDate", convertedDate);
        params.put("owner", owner);
        params.put("address", address);
        
        Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
        
        try {
        	 
        	connection = connectionDao.getConnection();
            
        	
        	System.out.println("Before filling out report...");
        	System.out.println("File path: " + JASPER_FILES_PATH + JASPER_NAME);
        	
            // fill the report
        	System.out.println("RACS params SIZE: " + params.size());
        	System.out.println("RACS connection: " + connection);
        	
        	
           //  JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_PATH + JASPER_NAME, params, connection); // returns NullPointerException
        	
        	JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_PATH + JASPER_NAME, params, connection);
        	
        	 
            System.out.println("after filling out report...");
            
            //OLD VERSION
            String outputFile = REPORT_LOCATION  + homeportalName.concat("_").concat(statementDate) + ".pdf";
            System.out.println("output file is " + outputFile);
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
            exporterPDF.exportReport();
           
            
        } catch (Exception ex) {
            String connectMsg = "Could not create the report " + ex.getMessage() + ex.getLocalizedMessage();
            System.out.println("error : " + ex);
        }finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        }
    }
	
	 
	 
	/*
	 * Gets the user id from users
	 * */
	public Map<String, String> getUserIdAndRoleId() throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		
		int userId = 0;
		int roleId = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		String sql = "select id as userId, role_id as roleId from users where role_id = 3 or role_id = 4 order by role_id asc";
		
		con = DAO.getConnection();
			
			if (con != null)
			{
				statement = con.prepareStatement(sql);
				rs = statement.executeQuery();
				
				if (rs != null)
				{
					while(rs.next())
					{
						userId = rs.getInt("userId");
						roleId = rs.getInt("roleId");
						
						System.out.println("userId: " + userId);
						System.out.println("roleId: " + roleId);
						
						map.put(String.valueOf(userId), String.valueOf(roleId));
					}
					
					rs.close();
				}
				
				if (statement != null)
				{
					statement.close();
				}
			}
			
		
		System.out.println("map size: " + map.size());
		
		return map;
	}
	
	
	/*
	 * Inserts data to pastdue table
	 * */
	public void insertPastDue(int userId, double amount, String billingMonth) throws Exception
	{
		int insertedRow = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		StringBuffer sb = new StringBuffer();
		
		// String sql = "select id as userId from users where role_id = 3 or role_id = 4 order by role_id asc";
		
		con = DAO.getConnection();
			
			if (con != null)
			{
				sb.append("insert into pastdue (user_id, amount, billing_month) values (");
				sb.append(userId);
				sb.append(", ");
				sb.append(amount);
				sb.append(", '");
				sb.append(billingMonth);
				sb.append("')");
				
				System.out.println("sb : " +sb.toString());
				
				statement = con.prepareStatement(sb.toString());
				insertedRow = statement.executeUpdate()	;	
				
				
				/*if (rs != null)
				{
					
					System.out.println("INSERTED userId: " + userId);
			
					rs.close();
				}*/
				
				//rs.close();
				
				if (statement != null)
				{
					statement.close();
				}
				
				//con.close();
			}
			
		
		//System.out.println("USERIDLIST SIZE: " + userIdList.size());
		//return insertedRow;
	}
	
	
	/*
	 * Gets data from LEDGER table
	 * */
	public double getPastDueFromLedger(int userId, String billingMonth) throws Exception
	{
		double amount = 0.00;
		
		StringBuffer sb = new StringBuffer();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String endDate = null;
		
		
		if (userId > 0 && hasValue(billingMonth))
		{
			endDate = formatDate(billingMonth);
			
			sb.append("select sum(amount) as amount from (select IFNULL(sum(amount),0) as amount from ledger where user_id =");
			sb.append(userId);
			sb.append(" and type = 'C' and created_at between '2013-01-01' and '");
			sb.append(endDate);
			sb.append("' UNION ALL select IFNULL(sum(amount),0) as amount from ledger where user_id = '");
			sb.append(userId);
			sb.append("' and type = 'D' and created_at between '2013-01-01' and '");
			sb.append(endDate);
			sb.append("') ledger");
			
			con = DAO.getConnection();
				
			if (con != null)
			{
				statement = con.prepareStatement(sb.toString());
				rs = statement.executeQuery();
					
				if (rs != null)
				{
					rs.next();
					
					amount = rs.getDouble("amount");
					
					System.out.println("AMOUNT: " + amount);
					rs.close();
				}
					
				if (statement != null)
				{
					statement.close();
				}
					
				// con.close();
			}
			
		}
		
		
		return amount;
	}
	
	
	
	public void getWaterReading(int userId) throws Exception
	{
		double totalCost = 0.00;
		
		StringBuffer sb = new StringBuffer();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String remarks = null;
		
		
		
		if (userId > 0)
		{
			sb.append("select billed_to as userId, billing_month, ((wr.end_reading - wr.start_reading) * r.amount) as  totalCost, ");
			sb.append("concat('Consumption ', (wr.end_reading - wr.start_reading), '@', r.amount ) as remarks  from water_reading wr, rates r where billed_to = '");
			sb.append(userId);
			sb.append("' and r.id = 2");
			
			System.out.println("SQL: " + sb.toString());
			
			con = DAO.getConnection();
				
			if (con != null)
			{
				statement = con.prepareStatement(sb.toString());
				rs = statement.executeQuery();
					
				if (rs != null)
				{
					//rs.next();
					
					if (rs.next() != false)
					{
						totalCost = rs.getDouble("totalCost");
						remarks = rs.getString("remarks");
						System.out.println("--before insert");
						// insert result to service_request table
			        	 insertWaterReadingToServiceRequest(userId, totalCost, remarks);
						System.out.println("--after insert");
					}
					
					
					
					System.out.println("totalCost: " + totalCost);
					System.out.println("remarks: " + remarks);
					
					rs.close();
				}
					
				if (statement != null)
				{
					statement.close();
				}
					
				// con.close();
			}
		
		}
		
		
		
	}
	
	
	
	 
	 
	 public int insertWaterReadingToServiceRequest(int userId, double totalCost, String remarks) throws Exception
	 {
		 System.out.println("Inserting to Service Request...");
		 
		 int insertedRow = 0;
		 Connection con = null;
		 PreparedStatement statement = null;
		 ResultSet rs = null;
			
		 StringBuffer sb = new StringBuffer();
			
		 con = DAO.getConnection();
				
		if (con != null)
		{
			sb.append("insert into service_request (user_id,total_cost,remarks,preferred_date,service_id,status) values ");
			sb.append("(");
			sb.append(userId);
			sb.append(",");
			sb.append(totalCost);
			sb.append(",'");
			sb.append(remarks);
			sb.append("', DATE_FORMAT(now(),'%m/%d/%Y'),'17','New')");

			System.out.println("sb : " +sb.toString());
					
			statement = con.prepareStatement(sb.toString());
			insertedRow = statement.executeUpdate()	;	
					
		
			if (statement != null)
			{
				statement.close();
			}
					
					//con.close();
			}
		
		System.out.println("insertedRow to Service_Request: " + insertedRow);
		 return insertedRow;
	 }
	
	 
	 
	 
	 public void getAssocDues(int userId) throws Exception
		{
			double totalCost = 0.00;
			
			StringBuffer sb = new StringBuffer();
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			String remarks = null;
			
			
			
			if (userId > 0)
			{
				sb.append("select u.id as user_id, (h.lot_area * r.amount) as totalCost, concat(h.lot_area,'sqm@', r.amount) as remarks from house h, ");
				sb.append("owner o , users u, rates r where r.id = 1 and u.id = '");
				sb.append(userId);
				sb.append("' and h.owner_id = o.id and o.id = '");
				sb.append(userId);
				sb.append("';");
				
				System.out.println("SQL: " + sb.toString());
				
				con = DAO.getConnection();
				
				System.out.println("CONNECTION: " + con);	
				if (con != null)
				{
					statement = con.prepareStatement(sb.toString());
					rs = statement.executeQuery();
						
					System.out.println("RESULT SET: " + rs);
					if (rs != null)
					{
						//rs.next();
						
						System.out.println("BEFORE...");
						if (rs.next() != false)
						{
							totalCost = rs.getDouble("totalCost");
							remarks = rs.getString("remarks");
							System.out.println("--before insert");
							
							// INSERT ASSOC DUES
							insertAssocDuesToServiceRequest(userId, totalCost, remarks);
							System.out.println("--after insert");
						}
						
						System.out.println("AFTER...");
						
						
						System.out.println("totalCost: " + totalCost);
						System.out.println("remarks: " + remarks);
						
						rs.close();
					}
						
					if (statement != null)
					{
						statement.close();
					}
						
					// con.close();
				}
			
			}
		}
		
	 
		
	 public int insertAssocDuesToServiceRequest(int userId, double totalCost, String remarks) throws Exception
	 {
		 System.out.println("Inserting Assoc Dues to Service Request...");
		 
		 int insertedRow = 0;
		 Connection con = null;
		 PreparedStatement statement = null;
		 ResultSet rs = null;
			
		 StringBuffer sb = new StringBuffer();
			
		 con = DAO.getConnection();
				
		if (con != null)
		{
			sb.append("insert into service_request (user_id,total_cost,remarks,preferred_date,service_id,status) values ");
			sb.append("(");
			sb.append(userId);
			sb.append(",");
			sb.append(totalCost);
			sb.append(",'");
			sb.append(remarks);
			sb.append("', DATE_FORMAT(now(),'%m/%d/%Y'),'16','New')");

			System.out.println("sb : " +sb.toString());
					
			statement = con.prepareStatement(sb.toString());
			insertedRow = statement.executeUpdate()	;	
					
		
			if (statement != null)
			{
				statement.close();
			}
					
					//con.close();
			}
		
		System.out.println("insertedRow to Service_Request: " + insertedRow);
		 return insertedRow;
	 }

	
	 public void insertStatementHistory(int userId, String statementDate)
	    {
	    	Connection connection = null;
	    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
	    
	        try {
	        	System.out.println("getting a connection for getHomeOwnerList()...");
	           
	            
	        	connection = connectionDao.getConnection();
	            
	            String sql = "insert into statement_history (user_id,report_name,report_date) values(" + userId + ",'" + statementDate + "',Now())";
	            System.out.println("sql is " + sql);
	            Statement statement = connection.createStatement();
	            int resultSet = statement.executeUpdate(sql);
	            
	        } catch (SQLException ex) {
	        	
	        	
	        }finally
	        {
	        	if(connection != null)
	        	{
	        		connectionDao.closeConnection();
	        	}
	        }
	    }
	   
	 
	 public void updateServiceRequest() throws Exception
	 {
		 System.out.println("Updating Service Request...");
		 
		 int insertedRow = 0;
		 Connection con = null;
		 PreparedStatement statement = null;
		 ResultSet rs = null;
			
		con = DAO.getConnection();
				
		if (con != null)
		{
			// update service_request set status = 'Done' where service_id = '16' or service_id = '17'
					
			String sql = "update service_request set status = 'Done' where service_id = '16' or service_id = '17'";
			
			
			System.out.println("sb : " + sql);
					
			statement = con.prepareStatement(sql);
			insertedRow = statement.executeUpdate();	
					
		
			if (statement != null)
			{
				statement.close();
			}
					
					//con.close();
			}
		
			System.out.println("insertedRow to Service_Request: " + insertedRow);

	 }
	 
	 
	 
	    public List<UserBean> getHomeOwnerList() throws Exception
	    {
	    	Connection con = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
				
			con = DAO.getConnection();
				
	    
	        List<UserBean> list = new ArrayList<UserBean>();
	        try {
	        	System.out.println("getting a connection for getHomeOwnerList()...");
	                       
	        	con = DAO.getConnection();
	           
	            String sql = "select ar.id, h.id as houseId, concat(MONTHNAME(STR_TO_DATE(MONTH(Now()),'%m')),'-',YEAR(Now())) as postedDate, Concat(o.firstname, o.lastname) as 'PayeeName', Concat(o.firstname,' ', o.lastname) as 'owner', Concat(h.addr_number, ' ', h.addr_street , ' ', IFNULL(h.Phase,' ' )) as 'address'  from owner o join users ar on o.user_id = ar.id join house h on h.owner_id = o.id where o.user_id = ar.id and ar.role_id in (3,4) and ar.status = 'A' UNION select ar.id, h.id as houseId, concat(MONTHNAME(STR_TO_DATE(MONTH(Now()), '%m')),'-',YEAR(Now())) as postedDate, Concat(o.firstname, o.lastname) as 'PayeeName', Concat(o.firstname,' ', o.lastname) as 'owner', Concat(h.addr_number, ' ', h.addr_street , ' ', IFNULL(h.Phase,' ' ))  as 'address' from leesee o join users ar on o.user_id = ar.id join house h on h.id = o.house_id where o.user_id = ar.id and ar.role_id in (3,4) and ar.status = 'A'";
  
	            statement = con.prepareStatement(sql);
				rs = statement.executeQuery();
				
				while (rs.next()) {
	                UserBean bean = new UserBean();
	                bean.setId(rs.getInt("id"));
	                bean.setHouseId(rs.getInt("houseId"));
	                bean.setUsername(rs.getString("postedDate"));
	                bean.setOwner(rs.getString("payeeName"));
	                bean.setOwnerName(rs.getString("owner"));
	                bean.setOwnerAddress(rs.getString("address"));
	                list.add(bean);
	            }
	            
	         } catch (SQLException ex) {
	            // Logger.getLogger(JasperReportUtil.class.getName()).log(Level.SEVERE, null, ex);
	        	// logger.error("Error in getting home owners list.", ex);
	        	 
	        }finally
	        {
	        	if(con != null)
	        	{
	        		con.close();
	        	}
	        }
	        
	        return list;
	    }
	 
	public String formatDate(String dateString)
	{
		String formattedDate = null;
		String[] tokens = null;
		int previousMonth = 0;
		int currentMonth = 0;
		
		if (dateString != null)
		{
			tokens = dateString.split(HYPHEN);
			
			if (tokens != null && tokens.length == 2)
			{
				currentMonth = Integer.parseInt(tokens[1]);
				previousMonth = Integer.parseInt(tokens[1]) - 1;
				
				if (currentMonth < 11)
				{
					formattedDate = tokens[0] + HYPHEN + ZERO + String.valueOf(previousMonth) + HYPHEN + THIRTYONE;
				}
				else
				{
					formattedDate = tokens[0] + HYPHEN + String.valueOf(previousMonth) + HYPHEN + THIRTYONE;
				}
			}
		}
		
		System.out.println("DATE: " + formattedDate);
		return formattedDate;
	}
	
	
	public boolean hasValue(String inputString)
	{
		if (inputString != null && !inputString.equals(""))
		{
			return true;
		}
		
		return false;
	}

}
	

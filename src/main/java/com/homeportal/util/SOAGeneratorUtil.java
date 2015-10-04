package com.homeportal.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.validation.Valid;

import org.apache.log4j.Logger;

import com.homeportal.dao.impl.IConnectionDaoImpl;
import com.homeportal.model.SOAHeader;




/**
 * Generates the monthly statement of account and updates the soa_header, soa_details and soa_summary tables
 * */
public class SOAGeneratorUtil implements FerndaleConstants
{
	private static Logger logger = Logger.getLogger(SOAGeneratorUtil.class);

	public static void main(String[] args) throws SQLException
	{
		String billingMonth = null;
		String startDate = null;
		String endDate = null;
		
		if (args != null && args.length > 0)
		{
			billingMonth = args[0];
			
			if (ValidationUtil.hasValue(billingMonth))
			{
				startDate = billingMonth + "-01";
				endDate = billingMonth + "-31";
			}
		}
		
		IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
	    Connection conn = null;
	   
	    if (connectionDao != null)
	   	{
	   		conn = connectionDao.getConnection();
	   	}
	   	
	    String sqlstmt;
	    Statement st = null;
	    ResultSet rs = null;
	    
	    Statement stInsert = null;
	    ResultSet rsInsert = null;
	
	    Statement stSelect = null;
	    ResultSet rsSelect = null;
	
	    Statement stUpdate = null;
	    ResultSet rsUpdate = null;
    
	    //update association due that is billing_month is null
	    sqlstmt = "update association_due set billing_month = '"
	            + billingMonth +"', posted_date = now(), status = 'Done' where billing_month is null";
	    
	    stUpdate = conn.createStatement();
	    stUpdate.executeUpdate(sqlstmt);
	    stUpdate.close();
	    
	    //update water_reading due that is billing_month is null
	    sqlstmt = "update water_reading set billing_month = '"
	            + billingMonth +"', posted_date = now(), status = 'Done' where billing_month is null";
	    
	    stUpdate = conn.createStatement();
	    stUpdate.executeUpdate(sqlstmt);
	    stUpdate.close();
    
	    
	    /*sqlstmt = "select h.id as houseId, u.id as userId, o.firstname, "
	            + "o.lastname, h.addr_number as houseAddrNumber, "
	            + "h.addr_street as houseAddrStreet from  house h, users u, owner o "
	            + "where o.user_id = u.id and u.status = 'A' and h.owner_id = o.id "
	            + "union all "
	            + "select h.id as houseId, u.id as userId, l.firstname, l.lastname, "
	            + "h.addr_number as houseAddrNumber, h.addr_street as houseAddrStreet "
	            + "from leesee l, house h, users u where l.user_id = u.id "
	            + "and u.status = 'A' and l.house_id = h.id";*/
	    
	    sqlstmt = "select h.id as houseId, u.id as userId, concat(o.firstname, ' ', o.lastname) as name, "
	    		+ "concat(h.addr_number, ' ', h.addr_street) as address " 
	    		+ "from  house h, users u, owner o " 
	    		+ "where o.user_id = u.id and u.status = 'A' and h.owner_id = o.id " 
	    		+ "union all "
	    		+ "select h.id as houseId, u.id as userId, "
	    		+ "concat(l.firstname, ' ', l.lastname) as name, "
	    		+ "concat(h.addr_number, ' ', h.addr_street) as address "
	    		+ "from leesee l, house h, users u where l.user_id = u.id "
	    		+ "and u.status = 'A' and l.house_id = h.id";
	    
	    st = conn.createStatement();
	    System.out.println(sqlstmt);
	    rs = st.executeQuery(sqlstmt);

    
	    while (rs.next())
	    {
	    	//chris
		Double balance = 0.00;
            	Double payments = 0.00;
            	Double charges = 0.00;

		SOAHeader soaHeader = new SOAHeader();
	        soaHeader.setHouseId(Integer.parseInt(rs.getString(1)));
	        soaHeader.setUserId(Integer.parseInt(rs.getString(2)));
	        soaHeader.setName(rs.getString(3));
	        soaHeader.setAddress(rs.getString(4));
	        soaHeader.setBillingMonth(billingMonth);
	        
	        sqlstmt = "insert into soa_header "
	                + "(user_id,house_id,billing_month,name,address,created_at,updated_at) "
	                + "values "
	                + "('" +soaHeader.getUserId() + "','" + soaHeader.getHouseId()+"','" + soaHeader.getBillingMonth() 
	                + "','" +soaHeader.getName() + "','" + soaHeader.getAddress() + "',now(),now())";
	                
	        
	        System.out.println(sqlstmt);
	        
	        stInsert = conn.createStatement();
	        stInsert.executeUpdate(sqlstmt);
	        stInsert.close();
        
	        
	        Statement stlastID = null;
	        ResultSet rslastID = null;
	        sqlstmt = "SELECT LAST_INSERT_ID()";
	        stlastID = conn.createStatement();
	        System.out.println(sqlstmt);
	        rslastID = stlastID.executeQuery(sqlstmt);
	        rslastID.next();
	        
	        soaHeader.setId(Integer.parseInt(rslastID.getString(1)));
	        rslastID.close();
	        stlastID.close();
	        
	        System.out.println("soaHeader.getID() = " +soaHeader.getId());
	        
	        //insert into summary
	        
	        sqlstmt = "insert into soa_summary (user_id,house_id,billing_month,created_at,updated_at) " // ask Chris what values to insert to payment, etc.
	                + "values "
	                + "('" +soaHeader.getUserId() + "','" +soaHeader.getHouseId() 
	                + "','" +soaHeader.getBillingMonth()+"',now(),now())";
	        
	        System.out.println(sqlstmt);
		        
	        stInsert = conn.createStatement();
	        stInsert.executeUpdate(sqlstmt);
	        stInsert.close(); 
	        
	        // get the following in ledger 
	        // all the charges 
	        
	        sqlstmt = "select ifnull(abs(sum(amount)),0) "
	                + "from ledger "
	                + "where type = 'C' and "
	                + "date_format(created_at,'%Y-%m-%d') between "
	                + "'" + startDate + "' and '" + endDate
	                + "' and user_id = '" +soaHeader.getUserId() + "' and "
	                + "house_id = '" +soaHeader.getHouseId() + "'"; 
	        
	        stUpdate = conn.createStatement();
	        System.out.println(sqlstmt);
	        rsUpdate = stUpdate.executeQuery(sqlstmt);
	
	        while (rsUpdate.next())  {
	            
		    //chris
		    charges = Double.parseDouble(rsUpdate.getString(1));	

	            sqlstmt = "update soa_summary set charges = ' "
	                    + charges + "' where "
	                    + "user_id = '" +soaHeader.getUserId() + "' and "
	                    + "house_id = '" +soaHeader.getHouseId() + "' and "
	                    + "billing_month = '" +soaHeader.getBillingMonth()+ "'";
	            
	            System.out.println(sqlstmt);
	            stUpdate = conn.createStatement();
	            stUpdate.executeUpdate(sqlstmt);
	            stUpdate.close(); 
	        }
	        
	        sqlstmt = "select ifnull(abs(sum(amount)),0) "
	                + "from ledger "
	                + "where type = 'D' and "
	                + "date_format(created_at,'%Y-%m-%d') between "
	                + "'" + startDate + "' and '" + endDate
	                + "' and user_id = '" +soaHeader.getUserId() + "' and "
	                + "house_id = '" +soaHeader.getHouseId() + "'"; 
	        
	        stUpdate = conn.createStatement();
	        System.out.println(sqlstmt);
	        rsUpdate = stUpdate.executeQuery(sqlstmt);
	
	        while (rsUpdate.next())  {

			//chris
		     payments = Double.parseDouble(rsUpdate.getString(1));	
	            
	            sqlstmt = "update soa_summary set payment = ' "
	                    + payments + "' where "
	                    + "user_id = '" +soaHeader.getUserId() + "' and "
	                    + "house_id = '" +soaHeader.getHouseId() + "' and " 
	                    + "billing_month = '" +soaHeader.getBillingMonth()+ "'";
	            System.out.println(sqlstmt);
	            stUpdate = conn.createStatement();
	            stUpdate.executeUpdate(sqlstmt);
	            stUpdate.close(); 
	        }
	        
	        sqlstmt = "select ifnull(sum(amount),0) "
	                + "from ledger "
	                + "where "
		        + "user_id = '" +soaHeader.getUserId() + "' and "
	                + "house_id = '" +soaHeader.getHouseId() + "'"; 
	        
	        stUpdate = conn.createStatement();
	        System.out.println(sqlstmt);
	        rsUpdate = stUpdate.executeQuery(sqlstmt);
	
	        while (rsUpdate.next())  {

			balance = Double.parseDouble(rsUpdate.getString(1));
                balance = balance - charges;
	            
	            sqlstmt = "update soa_summary set balance = ' "
	                    + balance + "' where "
	                    + "user_id = '" +soaHeader.getUserId() + "' and "
	                    + "house_id = '" +soaHeader.getHouseId() + "' and "
	                    + "billing_month = '" +soaHeader.getBillingMonth()+ "'";
	            System.out.println(sqlstmt);
	            stUpdate = conn.createStatement();
	            stUpdate.executeUpdate(sqlstmt);
	            stUpdate.close(); 
	        }
	        
	        rsUpdate.close();
	        stUpdate.close();
	        
	        // get soa details
		Double total_balance = 0.00;
            total_balance = balance + charges;
            sqlstmt = "update soa_summary set total_balance = '"
                    +total_balance+ "' where "
                        + "user_id = '" +soaHeader.getUserId() + "' and "
                        + "house_id = '" +soaHeader.getHouseId() + "' and "
                        + "billing_month = '" +soaHeader.getBillingMonth()+ "'";
            
            System.out.println(sqlstmt);
            stUpdate = conn.createStatement();
            stUpdate.executeUpdate(sqlstmt);
            stUpdate.close(); 
	        
	    }
    
	    rs.close();
	    st.close();
	}   
   
}

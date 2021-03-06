/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;

import com.homeportal.bean.UserBean;
import com.homeportal.dao.impl.IConnectionDaoImpl;

/**
 *
 * @author Peter
 */

public class JasperReportUtil {
	private static Logger logger = Logger.getLogger(JasperReportUtil.class);

    //@Autowired
    ServletContext context;
    
    private static final String JASPER_FILES_PATH = MessageBundle.getProperty("jasperFilesPath");
    private static final String JASPER_NAME = MessageBundle.getProperty("jasperName");
    private static final String COLLECTIONS_NAME = MessageBundle.getProperty("collectiblesName");
    private static final String REPORT_LOCATION = MessageBundle.getProperty("reportLocation");
    private static final String REPORT_LOCATION_OR = MessageBundle.getProperty("reportLocationOr");
    private static final String REPORT_URL = MessageBundle.getProperty("reportUrl");
    private static final String REPORT_URL_OR = MessageBundle.getProperty("reportUrlOr");
    private static final String BILLING_PAYMENT_JASPER_FILENAME = MessageBundle.getProperty("billingPaymentJasperFilename");
    private static final String CUT_OFF_DATE = MessageBundle.getProperty("cutOffDate");
    
    
    public JasperReportUtil()
    {
    	
    }
    

    public void generateStatement(int userId, String statementDate, String homeportalName, String owner, String address){
        
         Date d = new Date();   
         SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMMM dd,yyyy" );   
         Calendar cal = Calendar.getInstance();    
         cal.setTime(d);    
         cal.add(Calendar.DATE, 15);    
         String convertedDate=dateFormat.format(cal.getTime());    
         System.out.println("Date increase by one.."+convertedDate);
         
         SimpleDateFormat dateFromFormat = new SimpleDateFormat("yyyy-MM-dd");
         Calendar calFrom = Calendar.getInstance();    
         //cal.setTime(d);    
         int month = calFrom.get(Calendar.MONTH)-1;
         int year = calFrom.get(Calendar.YEAR);
         calFrom.set(year, month, Integer.parseInt(CUT_OFF_DATE));
         //calFrom.add(Calendar.YEAR);
         String dateFrom = dateFromFormat.format(calFrom.getTime());
         System.out.println("Date From.."+dateFrom);
         
         SimpleDateFormat dateToFormat = new SimpleDateFormat("yyyy-MM-dd");
         String dateTo = dateToFormat.format(d);
         System.out.println("Date To.."+dateTo);
        
        Map params = new HashMap();
        params.put("user_id", userId);
        params.put("statementDate", statementDate);
        params.put("convertedDate", convertedDate);
        params.put("owner", owner);
        params.put("address", address);
        
        Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
        
        try {
        	//System.out.println("RACS getting a connection from JasperReportUtil");
            logger.info("RACS getting a connection from JasperReportUtil");
            
        	connection = connectionDao.getConnection();
            
        	
        	System.out.println("RACS before filling out report...");
        	System.out.println("RACS File path: " + JASPER_FILES_PATH + JASPER_NAME);
        	
            // fill the report
        	System.out.println("RACS params SIZE: " + params.size());
        	System.out.println("RACS connection: " + connection);
        	
        	
           //  JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_PATH + JASPER_NAME, params, connection); // returns NullPointerException
        	
        	JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_PATH + JASPER_NAME, params, connection);
        	
        	 
            System.out.println("after filling out report...");
            
            //OLD VERSION
           /* String outputFile = REPORT_LOCATION  + homeportalName.concat("_").concat(statementDate) + ".pdf";
            System.out.println("output file is " + outputFile);
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
            exporterPDF.exportReport();
           */ 
            
            String fileName = "C:\\newworkspace\\CHRIS\\HOMEPORTAL_UAT\\ayalaferndalehomes\\src\\main\\webapp\\WEB-INF\\reports\\viewPortalStatement.jasper";  
            String outFileName = REPORT_LOCATION  + homeportalName.concat("_").concat(statementDate) + ".pdf";
            
            JasperPrint print = JasperFillManager.fillReport(fileName, params, connection); 
        	JRExporter exporter =new net.sf.jasperreports.engine.export.JRPdfExporter();  
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,outFileName);  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,print);  
            exporter.exportReport();
            
            
            
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
    
    public void generateCollections(){
        System.out.println(" *** went to generate collections *** ");
         Date d = new Date();   
         SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM-yyyy" );
         Calendar cal = Calendar.getInstance();    
         //cal.setTime(d);    
         // for test int month = cal.get(Calendar.MONTH)-1;
         int month = cal.get(Calendar.MONTH);
         int year = cal.get(Calendar.YEAR);
         cal.set(year, month,1);
         String dateTo = dateFormat.format(cal.getTime());
         System.out.println(" Statement date is .."+dateTo);
        
        Map params = new HashMap();
        params.put("collectibleDate", dateTo.toUpperCase());
        
        Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
        
        try {
        	System.out.println("getting a connection from JasperReportUtil");
            logger.info("getting a connection from JasperReportUtil");
            
        	connection = connectionDao.getConnection();
            
        	
        	System.out.println("before filling out report...");
        	System.out.println("File path: " + JASPER_FILES_PATH + COLLECTIONS_NAME);
        	
            // fill the report
            JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_PATH + COLLECTIONS_NAME, params, connection);
            //System.out.println("after filling out report...");
            
            String outputFile = REPORT_LOCATION  + dateTo.concat("_collections") + ".pdf";
            System.out.println("output file is " + outputFile);
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
            exporterPDF.exportReport();
            insertCollectionsHistory(dateTo);
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
    
    public List<UserBean> getHomeOwnerList()
    {
    	Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
    
        List<UserBean> list = new ArrayList<UserBean>();
        try {
        	System.out.println("getting a connection for getHomeOwnerList()...");
            logger.info("getting a connection for getHomeOwnerList()...");
            
        	connection = connectionDao.getConnection();
           
            String sql = "select ar.id, h.id as houseId, concat(MONTHNAME(STR_TO_DATE(MONTH(Now()),'%m')),'-',YEAR(Now())) as postedDate, Concat(o.firstname, o.lastname) as 'PayeeName', Concat(o.firstname,' ', o.lastname) as 'owner', Concat(h.addr_number, ' ', h.addr_street , ' ', IFNULL(h.Phase,' ' )) as 'address'  from owner o join users ar on o.user_id = ar.id join house h on h.owner_id = o.id where o.user_id = ar.id and ar.role_id in (3,4) and ar.status = 'A' UNION select ar.id, h.id as houseId, concat(MONTHNAME(STR_TO_DATE(MONTH(Now()), '%m')),'-',YEAR(Now())) as postedDate, Concat(o.firstname, o.lastname) as 'PayeeName', Concat(o.firstname,' ', o.lastname) as 'owner', Concat(h.addr_number, ' ', h.addr_street , ' ', IFNULL(h.Phase,' ' ))  as 'address' from leesee o join users ar on o.user_id = ar.id join house h on h.id = o.house_id where o.user_id = ar.id and ar.role_id in (3,4) and ar.status = 'A'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                UserBean bean = new UserBean();
                bean.setId(resultSet.getInt("id"));
                bean.setHouseId(resultSet.getInt("houseId"));
                bean.setUsername(resultSet.getString("postedDate"));
                bean.setOwner(resultSet.getString("payeeName"));
                bean.setOwnerName(resultSet.getString("owner"));
                bean.setOwnerAddress(resultSet.getString("address"));
                list.add(bean);
            }
            
         } catch (SQLException ex) {
            // Logger.getLogger(JasperReportUtil.class.getName()).log(Level.SEVERE, null, ex);
        	 logger.error("Error in getting home owners list.", ex);
        	 
        }finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        }
        
        return list;
    }
    
    public void updateJobOrderStatus()
    {
        Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
    
        try {
        	System.out.println("getting a connection for updateJobOrderStatus()...");
            logger.info("getting a connection for updateJobOrderStatus()...");
            
        	connection = connectionDao.getConnection();
            
            String sql = "update job_orders set status = 1 where status = 0";
            System.out.println("sql is " + sql);
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(sql);
            
        } catch (SQLException ex) {
        	logger.error("Error in updateJobOrderStatus.", ex);
        	
        }finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        }
    }
    
    public void insertStatementHistory(int userId, String statementDate)
    {
    	Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
    
        try {
        	System.out.println("getting a connection for getHomeOwnerList()...");
            logger.info("getting a connection for getHomeOwnerList()...");
            
        	connection = connectionDao.getConnection();
            
            String sql = "insert into statement_history (user_id,report_name,report_date) values(" + userId + ",'" + statementDate + "',Now())";
            System.out.println("sql is " + sql);
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(sql);
            
        } catch (SQLException ex) {
        	logger.error("Error in inserting statement history.", ex);
        	
        }finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        }
    }
    
    public void insertCollectionsHistory(String statementDate)
    {
    	Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
    
        try {
        	System.out.println("insertCollectionsHistory...");
               logger.info("getting a connection for getHomeOwnerList()...");
            
        	connection = connectionDao.getConnection();
            
            String sql = "insert into collections_history (report_name,report_date) values('"+statementDate + "',Now())";
            System.out.println("sql is " + sql);
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(sql);
            
        } catch (SQLException ex) {
        	logger.error("Error in inserting collections history.", ex);
        	
        }finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        }
    }

    
    
    /*public void generateORForPortal(Map params, String orNumber){

    	Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
    	
        try {
        	System.out.println("getting a connection from JasperReportUtil");
            logger.info("getting a connection from JasperReportUtil");
            
        	connection = connectionDao.getConnection();
            
            // fill the report
            JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_PATH + BILLING_PAYMENT_JASPER_FILENAME, params, connection);
            
            String outputFile = REPORT_LOCATION + "/"  + orNumber + ".pdf";
            System.out.println("output file is " + outputFile);
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
            exporterPDF.exportReport();

        } catch (Exception ex) {
            String connectMsg = "Could not create the report " + ex.getMessage() + ex.getLocalizedMessage();
            //logger.info(connectMsg);
            System.out.println("error : " + ex);
            
        }finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        }
    }*/

    
    public static void main(String args[])
    {
       String date = ""; 
       System.out.println("JASPER_FILES_PATH: " + JASPER_FILES_PATH);
        JasperReportUtil jru = new JasperReportUtil();
        List<UserBean> beans = jru.getHomeOwnerList();
        for (UserBean u : beans) {
             System.out.println("list : " + u.getId() + u.getUsername()+" "+" " + u.getOwner().concat("_").concat(String.valueOf(u.getHouseId())));
             jru.generateStatement(u.getId(), u.getUsername(), u.getOwner().concat("_").concat(String.valueOf(u.getHouseId())),u.getOwnerName(),u.getOwnerAddress());
             jru.insertStatementHistory(u.getId(), u.getUsername());
        }
        jru.generateCollections();
        jru.updateJobOrderStatus();
            /**
            Map params = new HashMap();
            String mode = "Cheque";
            int current = 1081;
            if(mode.equalsIgnoreCase("Cheque"))
            {
            params.put("p_cheque", "436565656767");
            params.put("p_bank", "RCBC Cainta");
            }
            params.put("p_or_number", String.valueOf(current));
            params.put("p_mode", mode);

            jru.generateORForPortal(params, String.valueOf(current));
            //jru.generateStatement();
             */
        

    }
}

package com.homeportal.util;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import com.homeportal.dao.impl.IConnectionDaoImpl;



/*
 * Utility class for generating Official Receipt (OR)
 * */
public class ORGeneratorUtil
{
	private static Logger logger = Logger.getLogger(ORGeneratorUtil.class);
	
	 private static final String JASPER_FILES_DIR = MessageBundle.getProperty("jasper.files.dir");
	 private static final String OFFICIAL_RECEIPT_DIR = MessageBundle.getProperty("official.receipt.dir");
	 private static final String OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME = MessageBundle.getProperty("official.receipt.template.jasper.filename");
	 private static final String ADMIN_OR_JASPER_FILENAME = MessageBundle.getProperty("adminORJasperFilename");
	   
	
   
    public static void generateOR(Map<String, Object> params, String orNumber) throws IOException 
    {
    	// String DeployType = "PROD"; USE THIS TO SWITCH, CREATE 2 ENTRIES TO PROPERTIES FILE   
    	logger.info("Generating PDF for OR number " + orNumber + "...");
    	System.out.println("Generating PDF for OR number " + orNumber + "...");
    	
    	logger.info("PAYEE ADRESS: " + params.get("p_payee_address"));
    	
    	Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
    	String outputFile = "";
    	
        try {
            System.out.println("getting a connection from AdminBillController...");
            logger.info("Getting a connection from AdminBillController...");
            
        	connection = connectionDao.getConnection();
        	
        	if(connection != null)
        	{
        		System.out.println("is connection closed? " + connection.isClosed());
        		logger.info("is connection closed? " + connection.isClosed());
        	}
        	else
        	{
        		logger.info("CONNECTION IS NULL.");
        	}
        	
             /*if(userId == Integer.parseInt(FERNDALE_USERID))
             {
             JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_PATH + ADMINOR_JASPER_FILENAME, params, connection);
             System.out.println("after filling out the report...");
             // String outputFile = context.getRealPath(REPORT_LOCATION) + "\\" + orNumber + ".pdf";
             outputFile = REPORT_LOCATION + orNumber + ".pdf";
             JRAbstractExporter exporterPDF = new JRPdfExporter();
             exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
             exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
             exporterPDF.exportReport();    
             }else
             {*/
             
        	System.out.println("=================xchris");
        	// SAVE THE OR TO THE SPECIFIED LOCATION
        	System.out.println(OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME);
        	System.out.println(JASPER_FILES_DIR);
        	JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_DIR + OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME, params, connection);
             System.out.println("after filling out the report...");
             // String outputFile = context.getRealPath(REPORT_LOCATION) + "\\" + orNumber + ".pdf";
             outputFile = OFFICIAL_RECEIPT_DIR + orNumber + ".pdf";
             JRAbstractExporter exporterPDF = new JRPdfExporter();
             exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
             exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
             exporterPDF.exportReport();
            
             //}	
             System.out.println("output file is " + outputFile);
            
            
            
        } catch (Exception ex) 
        {
            String connectMsg = "Could not create the report " + ex.getMessage() + ex.getLocalizedMessage();
            System.out.println(connectMsg);
            
            //logger.info(connectMsg);
            System.out.println("error : " + ex);
        
        } finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        }
    }

    
    

	
}

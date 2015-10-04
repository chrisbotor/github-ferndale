package com.homeportal.web;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.homeportal.dao.impl.IConnectionDaoImpl;
import com.homeportal.model.OfficialReceipt;
import com.homeportal.model.OfficialReceiptDetails;
import com.homeportal.model.OfficialReceiptHeader;
import com.homeportal.model.SOAHeader;
import com.homeportal.model.SOASummary;
import com.homeportal.service.BillingService;
import com.homeportal.service.OfficialReceiptService;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.DateTimeUtil;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.OwnerUtil;
import com.homeportal.util.ValidationUtil;



/**
 * Handles and retrieves download request
 */

@Controller
public class PDFGeneratorController
{
	private static Logger logger = Logger.getLogger(PDFGeneratorController.class);

	private static final String WHITE_SPACE = " ";
	private static final String CASH = "Cash";
	private static final String CHEQUE = "Cheque";
	private static final String NAME_SEPARATOR = " / ";
	
	private static final String JASPER_FILES_DIR = MessageBundle.getProperty("jasper.files.dir");
	private static final String DAILY_COLLECTION_JASPER_FILENAME = MessageBundle.getProperty("collections.daily.jasper.filename");
	private static final String MONTHLY_COLLECTION_JASPER_FILENAME = MessageBundle.getProperty("collections.monthly.jasper.filename");
	private static final String DETAILED_DAILY_COLLECTION_JASPER_FILENAME = MessageBundle.getProperty("detailed.collections.daily.jasper.filename");
	private static final String DETAILED_MONTHLY_COLLECTION_JASPER_FILENAME = MessageBundle.getProperty("detailed.collections.monthly.jasper.filename");
	private static final String SUMMARY_MONTHLY_COLLECTION_JASPER_FILENAME = MessageBundle.getProperty("summary.collections.monthly.jasper.filename");
	// private static final String SUMMARY_MONTHLY_COLLECTION_JASPER_FILENAME = "";
	private static final String OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME = MessageBundle.getProperty("official.receipt.template.jasper.filename");
	private static final String MONTHLY_STATEMENT_TEMPLATE_JASPER_FILENAME = MessageBundle.getProperty("monthly.statement.template.jasper.filename");
	

	private OfficialReceiptService orService;
	private BillingService billingService;
	
	
   
    /** Used by Daily Collections to generate PDF
     * 
     * @param response
     * @param request
     * @param session
     * @throws ServletException
     * @throws IOException
     */
     @RequestMapping(value = "/download/daily-collection-pdf.action", method = RequestMethod.GET)
     public void generateDailyCollectionPDF(HttpServletResponse response, HttpServletRequest request,HttpSession session) 
    		 throws ServletException, IOException, ParseException 
    {
    	 String dailyCollectionDate = (String) request.getParameter("dailyCollectionDate");
    	 String detailed = request.getParameter("detailed");
    	 
    	 logger.info("******* Generating PDF for daily collection for the date: " + dailyCollectionDate);
    	 logger.info("DETAILED? " + detailed);
    	
    	 Map<String, Object> params = new HashMap<String, Object>();
    	 params.put("date", dailyCollectionDate);
    	 
    	 Connection connection = null;
         IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
         
         String jasperFilename = null;
         String generatedPDFFilename = null;
         
         try {
         	
         	connection = connectionDao.getConnection();
         	
         	if (connection == null)
         	{
         		logger.error("connection is NULL!");
         		System.out.println("connection is NULL!");
         	}
         	
         	if (ValidationUtil.hasValue(detailed) && ConstantsUtil.TRUE.equals(detailed))
         	{
         		jasperFilename = DETAILED_DAILY_COLLECTION_JASPER_FILENAME;
             	generatedPDFFilename =  DateTimeUtil.convertStringToDailyCollectionFormat(dailyCollectionDate) + "_detailed_daily_collection.pdf";
            }
         	else
         	{
         		jasperFilename = DAILY_COLLECTION_JASPER_FILENAME;
             	generatedPDFFilename =  DateTimeUtil.convertStringToDailyCollectionFormat(dailyCollectionDate) + "_daily_collection.pdf";
         	}
         	
         	
         	// jasperFilename = DAILY_COLLECTION_JASPER_FILENAME;
         	// generatedPDFFilename =  DateTimeUtil.convertStringToDailyCollectionFormat(dailyCollectionDate) + "_daily_collection.pdf";
         	
         	logger.info("##### jasperFilename: " + jasperFilename);
         	logger.info("generatedPDFFilename: " + generatedPDFFilename);
         	
         	fillReport(response, jasperFilename, generatedPDFFilename, params, connection);
         } 
         catch (Exception ex) 
         {
        	 logger.error("Could not create the daily collection report " + ex.getMessage() + ex.getLocalizedMessage());
        	 
        	 System.out.println("Could not create the daily collection report " + ex.getMessage() + ex.getLocalizedMessage());
         }
         finally
         {
         	if(connection != null)
         	{
         		connectionDao.closeConnection();
         	}
         	
         }
     }
     
     
     
     
     /** Used by Monthly Collections to generate PDF
      * 
      * @param response
      * @param request
      * @param session
      * @throws ServletException
      * @throws IOException
      */
      @RequestMapping(value = "/download/monthly-collection-pdf.action", method = RequestMethod.GET)
      public void generateMonthlyCollectionPDF(HttpServletResponse response, HttpServletRequest request,HttpSession session) 
     		 throws ServletException, IOException, ParseException 
     {
    	  System.out.println("\n*** Generating PDF for monthly collection...");
    	  logger.info("\n*** Generating PDF for monthly collection...");
    	  
    	  // MONTHLY COLLECTION ONLY FOR A SINGLE DATE
    	  String monthlyCollectionDate = (String) request.getParameter("monthlyCollectionDate");
    	  
    	  // WITH DATE RANGE
    	  String monthlyCollectionDateFrom = (String) request.getParameter("monthlyCollectionDateFrom");
    	  String monthlyCollectionDateTo = (String) request.getParameter("monthlyCollectionDateTo");
    	  
    	  // WITH DATE RANGE BUT SUMMARY ONLY
    	  String summary = (String) request.getParameter("summary");
    	  
    	 
    	  // DEBUGGING
    	  logger.info("monthlyCollectionDate: " + monthlyCollectionDate);
    	  logger.info("monthlyCollectionDateFrom: " + monthlyCollectionDateFrom);
    	  logger.info("monthlyCollectionDateTo: " + monthlyCollectionDateTo);
    	  logger.info("SUMMARY ONLY? " + summary);
    	  
    	  System.out.println("monthlyCollectionDate: " + monthlyCollectionDate);
    	  System.out.println("monthlyCollectionDateFrom: " + monthlyCollectionDateFrom);
    	  System.out.println("monthlyCollectionDateTo: " + monthlyCollectionDateTo);
    	  System.out.println("SUMMARY ONLY? " + summary);
    	  
    	
    	  // CREATE MAP OF PARAMETERS TO BE PASSED TO THE Jasper file
    	  Map<String, Object> params = new HashMap<String, Object>();

    	  Connection connection = null;
          IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
         
          String jasperFilename = null;
          String generatedPDFFilename = null;

          
          try 
          {
        	 connection = connectionDao.getConnection();
        	 
        	 if (ValidationUtil.hasValue(monthlyCollectionDateFrom) && ValidationUtil.hasValue(monthlyCollectionDateTo))
        	 {
        		
        		 params.put("date", monthlyCollectionDateFrom);
        		 params.put("date1", monthlyCollectionDateTo);
        		 
        		 
        		 // for monthly SUMMARY
        		 if (ValidationUtil.hasValue(summary) && Boolean.valueOf(summary))
        		 {
        			 System.out.println("******** GETTING JASPER FILE FOR MONTHLY SUMMARY...");
        			 logger.info("******** GETTING JASPER FILE FOR MONTHLY SUMMARY...");
        			 
        			 jasperFilename = SUMMARY_MONTHLY_COLLECTION_JASPER_FILENAME;
        			 generatedPDFFilename =  DateTimeUtil.convertStringToDailyCollectionFormat(monthlyCollectionDateFrom) + "_"
	 										+ DateTimeUtil.convertStringToDailyCollectionFormat(monthlyCollectionDateTo) + "_summary_monthly_collection.pdf";
        		 }
        		 else
        		 {
        			 // for monthly DETAILED
            		 jasperFilename = DETAILED_MONTHLY_COLLECTION_JASPER_FILENAME;
                   	 generatedPDFFilename =  DateTimeUtil.convertStringToDailyCollectionFormat(monthlyCollectionDateFrom) + "_"
                   			 				+ DateTimeUtil.convertStringToDailyCollectionFormat(monthlyCollectionDateTo) + "_detailed_monthly_collection.pdf";
                }
        	 }
        	 else
        	 {
        		 // for a single date only 
        		 jasperFilename = MONTHLY_COLLECTION_JASPER_FILENAME;
               	 generatedPDFFilename =  DateTimeUtil.convertStringToMontlyCollectionFormat(monthlyCollectionDate) + "_monthly_collection.pdf";
               	 params.put("date", monthlyCollectionDate);
        	 }
        	 
        	 // CREATE THE REPORT PDF
        	 fillReport(response, jasperFilename, generatedPDFFilename, params, connection);
               
         }
         catch (Exception ex) 
         {
         	 logger.error("Could not create the daily collection report " + ex.getMessage() + ex.getLocalizedMessage());
         }
         finally
         {
          	if(connection != null)
          	{
          		connectionDao.closeConnection();
          	}
          	
         }
      }
      
      
      
      
      /** Used by View User OR history
       *  This method gets the userId and houseId first from the session (this is when it is the first time the home owner paid).
       *  For subsequent downloads of an OR, this method gets the userId and houseId from the request.
       *  
       * @param response
       * @param request
       * @param session
       * @throws ServletException
       * @throws IOException
       */
       @RequestMapping(value = "/download/user-or-history-pdf.action", method = RequestMethod.GET)
       public void generateORpdfForUser(HttpServletResponse response, HttpServletRequest request, HttpSession session) 
      		 throws ServletException, IOException, ParseException 
      {
    	   String userIdParam = request.getParameter("requestorUserId");
    	   String houseIdParam = request.getParameter("requestorHouseId");
    	   int orNumber = Integer.parseInt(request.getParameter("orNumber"));
    	   
    	   OfficialReceipt or = null;
    	   OfficialReceiptHeader orHeader = null;
    	   OfficialReceiptDetails orDetails = null;
    	   
    	   
    	   int userId = 0;
    	   int houseId = 0;
    	   
    	   // USER ID
    	   if (ValidationUtil.hasValue(userIdParam))
    	   {
    		   userId = Integer.parseInt(userIdParam);
    	   }
    	   else
    	   {
    		   userId = (Integer) session.getAttribute("userId");
    	   }
    	   
    	  
    	   // HOUSE ID
    	   if (ValidationUtil.hasValue(houseIdParam))
    	   {
    		   houseId = Integer.parseInt(houseIdParam);
    	   }
    	   else
    	   {
    		   houseId = (Integer) session.getAttribute("houseId");
    	   }
    	   
    	   
    	   System.out.println("*** userId: " + userId);
    	   System.out.println("houseId: " + houseId);
    	   System.out.println("orNumber: " + orNumber);
    	   
    	   logger.info("*** userId: " + userId);
           logger.info("houseId: " + houseId);
           logger.info("orNumber: " + orNumber);
           
           
           // this is used to get the EXCESS amount paid 
    	   int jobOrderId = 0;
    	   int orHeaderId = 0;
    	   Double excessPayment = new Double("0.00");
           
    	   // VARIABLE FOR THE MAP
    	   String modeOfPayment = null;
    	   String chequeNumber = null;
    	   String bank = null;
    	   String payeeName = null;
    	   String payeeAddress = null;
    	   Date orDate = null;
    	   String formattedORDate = null;
    	   
    	   
           // get the OR
           or = orService.getOR(userId, houseId, orNumber);
           
           if (or != null)
           {
        	   bank = or.getBankName();
        	   chequeNumber = or.getChequeNumber();
        	   jobOrderId = or.getJobOrderId();
        	   
        	   logger.info("bank: " + bank);
        	   logger.info("chequeNumber: " + chequeNumber);
        	   logger.info("jobOrderId: " + jobOrderId);
        	   
        	   System.out.println("bank: " + bank);
        	   System.out.println("chequeNumber: " + chequeNumber);
        	   System.out.println("jobOrderId: " + jobOrderId);
        	   
        	   
        	   if (chequeNumber != null && !chequeNumber.equals(""))
        	   {
        		   modeOfPayment = CHEQUE;
        	   }
        	   else
        	   {
        		   modeOfPayment = CASH;
        	   }
        	   
        	   logger.info("modeOfPayment: " + modeOfPayment);
        	   System.out.println("modeOfPayment: " + modeOfPayment);
        	}
           
           
           
           // get the OR header
           orHeader = orService.getORHeader(userId, houseId, orNumber);
           
           if (orHeader != null)
           {
        	   payeeName = orHeader.getPayeeName();
        	   payeeAddress = orHeader.getPayeeAddress();
        	   orDate = orHeader.getOrDate();
        	   
        	   if (orDate != null)
        	   {
        		   final String format = "MM/dd/yyyy";
        		   formattedORDate = DateTimeUtil.convertDateToString(orDate, format); // RACS
        	   }
        	   
        	   // If OR Header is not NULL, get the OR Details object using the OR Header id and the jobOrderId from the OR object
        	   // then get the EXCESS amount paid from the OR Details (06/03/2015)
        	   orHeaderId = orHeader.getId();
        	   
        	   logger.info("payeeName: " + payeeName);
        	   logger.info("payeeAddress: " + payeeAddress);
        	   logger.info("orDate: " + formattedORDate);
        	   
        	   System.out.println("payeeName: " + payeeName);
        	   System.out.println("payeeAddress: " + payeeAddress);
        	   System.out.println("orDate: " + formattedORDate);
        	}
           
           
           // get the OR Details
           orDetails = orService.getORDetails(orHeaderId, jobOrderId);
           if (orDetails != null)
           {
        	   excessPayment = orDetails.getExcess();
           }
           
           
           // build the Map of params
           Map<String, Object> params = new HashMap<String, Object>();
           params.put("p_or_number", String.valueOf(orNumber));
           params.put("p_mode", modeOfPayment);
           params.put("p_cheque", chequeNumber);
           params.put("p_bank", bank);
           // params.put("p_payee_name", session.getAttribute("p_payee_name"));
           params.put("p_payee_address", payeeAddress);
           params.put("p_date", formattedORDate);
           params.put("p_excess_payment", excessPayment);
           
           
           
           // set the co-owner in the OR pdf generated if there is a co-owner for the house
           Map<String, String> coOwnerDetails = OwnerUtil.getCoOwner(userId);
           StringBuilder sb = new StringBuilder();
           
           if (coOwnerDetails != null && coOwnerDetails.size() > 0)
           {
        	   logger.info("WITH co-owner");
           	
           		// append co-owner to owner
           		sb.append(payeeName).append(NAME_SEPARATOR)
           		.append(coOwnerDetails.get("coFirstName").toUpperCase())
           		.append(WHITE_SPACE)
           		.append(coOwnerDetails.get("coLastName").toUpperCase());
           	
           		params.put("p_payee_name", sb.toString());
           }
           else
           {
           		logger.info("NO co-owner");
           		params.put("p_payee_name", payeeName);
           }
           
           		logger.info("payeeName: " + payeeName);
           
           
           Connection connection = null;
           IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
           
           String jasperFilename = null;
           String generatedPDFFilename = null;
            
           try 
           {
          	 connection = connectionDao.getConnection();
          	 jasperFilename = OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME;
             generatedPDFFilename =  orNumber + ".pdf"; // TODO
             	
          	 fillReport(response, jasperFilename, generatedPDFFilename, params, connection);
                 
           }
           catch (Exception ex) 
           {
           	 logger.error("Could not create the daily collection report " + ex.getMessage() + ex.getLocalizedMessage());
           }
           finally
           {
            	if(connection != null)
            	{
            		connectionDao.closeConnection();
            	}
           }
       }
      
      
      
       /** Used to generate user monthly statement PDF
        * 
        * @param response
        * @param request
        * @param session
        * @throws ServletException
        * @throws IOException
        */
        @RequestMapping(value = "/download/user-statement-pdf.action", method = RequestMethod.GET)
        public void generateUserMonthlyStatementPDF(HttpServletResponse response, HttpServletRequest request, HttpSession session) 
       		 throws ServletException, IOException, ParseException 
       {
        	// get userId and houseId and billingMonth from the request
        	// once we have those data, get the SOAHeader using userId, houseId and billingMonth
        	// once we have the SOAHeader, use it to get the name and assign it to billName, then get address and assign it to billAddress
        	// once we go the SOAHeader id, use it to get the SOASummary
        	// once we have the SOASummary, use it to get the billBalance, billCharge and billPayment
        	
        	try
        	{
        		int billUserId = Integer.parseInt(request.getParameter("userId"));
        		int billHouseId = Integer.parseInt(request.getParameter("houseId"));
        		String billMonth = request.getParameter("billingMonth");
        		
        		String billName = null;
        		String billAddress = null;
        		
        		SOASummary soaSummary = null;
        		String billBalance = null;
        		String billCharge = null;
        		String billPayment = null;
        		String billTotalBalance = null;
        		
        		SOAHeader soaHeader = billingService.getSOAHeader(billUserId, billHouseId, billMonth);
        		
        		if (soaHeader != null)
        		{
        			billName = soaHeader.getName();
        			billAddress = soaHeader.getAddress();
        			
        			soaSummary = billingService.getSOASummary(soaHeader.getId());
        			
        			if (soaSummary != null)
        			{
        				billBalance = String.valueOf(soaSummary.getBalance());
        				billCharge = String.valueOf(soaSummary.getCharges());
        				billPayment = String.valueOf(soaSummary.getPayment());
        				billTotalBalance = String.valueOf(soaSummary.getTotalBalance());
        			}
        		}
        		
        		
        		// ####################  CREATE THE PDF  ########################
        		 // build the Map of params
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("billUserId", billUserId);
                params.put("billHouseId", billHouseId);
                params.put("billMonth", billMonth);
                params.put("billName", billName);
                params.put("billAddress", billAddress);
                params.put("billBalance", billBalance);
                params.put("billCharge", billCharge);
                params.put("billPayment", billPayment);
                params.put("billTotalBalance", billTotalBalance);
                
                
                Connection connection = null;
                IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
                
                String jasperFilename = null;
                String generatedPDFFilename = null;
                 
                try 
                {
               	 connection = connectionDao.getConnection();
               	 jasperFilename = MONTHLY_STATEMENT_TEMPLATE_JASPER_FILENAME;
                 generatedPDFFilename =  billName + "_" + billMonth + ".pdf"; // TODO
                  	
               	 fillReport(response, jasperFilename, generatedPDFFilename, params, connection);
                      
                }
                catch (Exception ex) 
                {
                	 logger.error("Could not create the daily collection report " + ex.getMessage() + ex.getLocalizedMessage());
                }
                finally
                {
                 	if(connection != null)
                 	{
                 		connectionDao.closeConnection();
                 	}
                }
        	}
        	catch (Exception ex)
        	{
        		logger.error("Error encountered when creating monthly statement PDF. " + ex.getMessage());
        	}
      }
       
        
      public void fillReport(HttpServletResponse response, String jasperFilename, String generatedPDFFilename, 
    		  		Map<String, Object> params, Connection connection)
      {
    	  System.out.println("Filling the report...");
    	  logger.info("Filling the report...");
    	  
    	  try
    	  {
    		  JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_DIR + jasperFilename, params, connection);
              JRAbstractExporter exporterPDF = new JRPdfExporter();
              exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
              
              try
              {
            	  exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
              }
              catch(IOException ioex)
              {
            	  System.out.println("Encountered error during setting parameters to the Jasper File. " + ioex.getMessage());
            	  logger.info("Encountered error during setting parameters to the Jasper File. " + ioex.getMessage());
              }
              
              response.setHeader("Content-disposition", "attachment; filename=\"" + generatedPDFFilename + "\"");
              response.setContentType("application/pdf");
              exporterPDF.exportReport();
              
              System.out.println("PDF successfully created.");
        	  logger.info("PDF successfully created.");
    	  }
    	  catch(JRException jrex)
    	  {
    		  System.out.println("Encountered error during filling of the report. \n" + jrex.getMessage());
        	  logger.info("Encountered error during filling of the report. \n" + jrex.getMessage());
    	  }
    }
      
      
      
      /**
       * @param orService the orService to set
       */
      @Autowired
      public void setOfficialReceiptService(OfficialReceiptService orService) 
      {
          this.orService = orService;
      }
      
      
      /**
       * @param billingService the billingService to set
       */
      @Autowired
      public void setBillingService(BillingService billingService) 
      {
          this.billingService = billingService;
      }
   
}
package com.homeportal.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.homeportal.dao.impl.IConnectionDaoImpl;
import com.homeportal.service.BillingService;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.OwnerUtil;

/**
 * Handles and retrieves download request
 */
@Controller
@RequestMapping("/main")
public class MainController {
	private static Logger logger = Logger.getLogger(MainController.class);
	private static final String WHITE_SPACE = " ";

    @Autowired
    ServletContext context;
    private BillingService billingService;
    //protected static Logger logger = Logger.getLogger("controller");
    
    private static final String JASPER_FILES_DIR = MessageBundle.getProperty("jasper.files.dir");
    private static final String REPORT_URL = MessageBundle.getProperty("reportUrl");
    private static final String OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME = MessageBundle.getProperty("official.receipt.template.jasper.filename");
    private static final String DAILY_PAYMENTS_JASPER_FILENAME = MessageBundle.getProperty("dailyPaymentsJasperFilename");
    private static final String ADMIN_OR_JASPER_FILENAME = MessageBundle.getProperty("adminORJasperFilename");
    private static final String REPORT_LOCATION = MessageBundle.getProperty("reportLocation");
    private static final String OFFICIAL_RECEIPT_DIR = MessageBundle.getProperty("official.receipt.dir");
    private static final String FERNDALE_USERID = MessageBundle.getProperty("ferndale_userId");
    
    public MainController()
    {
       
    }

    /**
     * Handles and retrieves the download page
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String getDownloadPage() {
        //logger.debug("Received request to show download page");
    	// Do your work here. Whatever you like
    	// i.e call a custom service to do your business
    	// Prepare a model to be used by the JSP page

    	// This will resolve to /WEB-INF/jsp/downloadpage.jsp
        return "downloadpage";
    }
    
   @RequestMapping(value = "/download/statement/pdf.action", method = RequestMethod.GET)
   public void generateStatements(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String name = request.getParameter("description").toString();
        
        String fileUrl = "file:///" + REPORT_LOCATION + name + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".pdf");
        BufferedInputStream  bis = null;
        BufferedOutputStream bos = null;
        System.out.println("file url : " + fileUrl);
        try {
        
        URL url = new URL (fileUrl);
        URLConnection urlc = url.openConnection();
        // Use Buffered Stream for reading/writing.
        bis = new BufferedInputStream(urlc.getInputStream());
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        // Simple read/write loop.
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        //return new ModelAndView("");
    } catch(final MalformedURLException e) {
        System.out.println ( "MalformedURLException."+e.toString() );
        //return new ModelAndView("billHistory");
    } catch(final IOException e) {
        System.out.println ( "IOException." + e.toString());
        //return new ModelAndView("billHistory");
    } finally {
        if (bis != null)
            bis.close();
        if (bos != null)
            bos.close();
    }
    }
   

   @RequestMapping(value = "/download/or/pdf.action", method = RequestMethod.GET)
   public void generateOrHistory(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
        System.out.println("generateOrHistory...............");
        String name = request.getParameter("orNumber").toString();
        
        String fileUrl = "file:///" + OFFICIAL_RECEIPT_DIR + name + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".pdf");
        BufferedInputStream  bis = null;
        BufferedOutputStream bos = null;
        try {

        URL url = new URL (fileUrl);
        URLConnection urlc = url.openConnection();
        // Use Buffered Stream for reading/writing.
        bis = new BufferedInputStream(urlc.getInputStream());
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        // Simple read/write loop.
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        //return new ModelAndView("");
    } catch(final MalformedURLException e) {
        System.out.println ( "MalformedURLException."+e.toString() );
        //return new ModelAndView("billHistory");
    } catch(final IOException e) {
        System.out.println ( "IOException." + e.toString());
        //return new ModelAndView("billHistory");
    } finally {
        if (bis != null)
            bis.close();
        if (bos != null)
            bos.close();
    }
    }
    
   
   /** used by Billing Payment to generate PDF
    * 
    * @param response
    * @param request
    * @param session
    * @throws ServletException
    * @throws IOException
    */
   // RACS TODO
    @RequestMapping(value = "/download/billing/pdf.action", method = RequestMethod.GET)
    public void generateORFromBilling(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
    		throws ServletException, IOException 
    {
    	System.out.println("******* Generating PDF for this bill...");
    	logger.info("******* Generating PDF for this bill...");
    	
    	// get the values from the session
    	String userId = (String) session.getAttribute("userId");
    	String houseId = (String) session.getAttribute("houseId");
    	String payeeName = (String) session.getAttribute("payeeName");
    	String payeeAddress = (String) session.getAttribute("payeeAddress");
    	String modeOfPayment = (String) session.getAttribute("modeOfPayment");
    	String chequeNumber = (String) session.getAttribute("chequeNumber");
    	String bank = (String) session.getAttribute("bank");
    	String orNumber = (String) session.getAttribute("orNumber");
    	String orDate = (String) session.getAttribute("orDate");
    	Double excessPayment = (Double) session.getAttribute("excessPayment");
    	
    	logger.info("userId: " + userId);
		logger.info("houseId: " + houseId);
		logger.info("payeeName: " + payeeName);
		logger.info("payeeAddress: " + payeeAddress);
		logger.info("modeOfPayment: " + modeOfPayment);
		logger.info("chequeNumber: " + chequeNumber);
		logger.info("bank: " + bank);
		logger.info("orNumber: " + orNumber);
		logger.info("orDate: " + orDate);
		logger.info("excessPayment: " + excessPayment);
		
    	
       logger.info("============== Getting parameters from the Session and adding them to params Map...");
        
       // get values from the session
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("p_or_number", orNumber);
        params.put("p_mode", modeOfPayment);
        params.put("p_cheque", chequeNumber);
        params.put("p_bank", bank);
        // params.put("p_payee_name", session.getAttribute("p_payee_name"));
        params.put("p_payee_address", payeeAddress);
        params.put("p_date", orDate);
        params.put("p_excess_payment", excessPayment);
        
        // set the co-owner in the OR pdf generated if there is a co-owner for the house
        Map<String, String> coOwnerDetails = OwnerUtil.getCoOwner(Integer.parseInt(userId));
        StringBuilder sb = new StringBuilder();
        
        if (coOwnerDetails != null && coOwnerDetails.size() > 0)
        {
        	logger.info("WITH co-owner");
        	System.out.println("WITH co-owner");
        	
        	// append co-owner to owner
        	sb.append(payeeName).append(" / ")
        	.append(coOwnerDetails.get("coFirstName").toUpperCase())
        	.append(WHITE_SPACE)
        	.append(coOwnerDetails.get("coLastName").toUpperCase());
        	
        	params.put("p_payee_name", sb.toString());
        }
        else
        {
        	logger.info("NO co-owner");
        	System.out.println("NO co-owner");
        	params.put("p_payee_name", payeeName);
        }
        
        logger.info("################ payee_name: " + params.get("p_payee_name"));
        logger.info("Number of parameters put in the params Map: " + params.size());
        
  
        Connection connection = null;
        IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
        
        try 
        {
        	System.out.println("\nGetting a connection from generateORFromBilling()...");
        	logger.info("*** Getting a connection from generateORFromBilling()...");
        	
        	connection = connectionDao.getConnection();
            
            // fill the report
        	System.out.println("userId: " + userId);
        	logger.info("userId: " + userId);
        	
            if(Integer.parseInt(userId) == Integer.parseInt(FERNDALE_USERID))
            {
            	System.out.println("Printing OR for admin account... ");
            	logger.info("Printing OR for admin account... ");
            	
            	JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_DIR + ADMIN_OR_JASPER_FILENAME, params, connection);
	            JRAbstractExporter exporterPDF = new JRPdfExporter();
	            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
	            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
	            exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
	            response.setHeader("Content-disposition", "attachment; filename=\"" +  orNumber + ".pdf" + "\"");
	            response.setContentType("application/pdf");
	            exporterPDF.exportReport();    
	            
            }
            else
            {
            	System.out.println("Printing OR for user account... ");
            	logger.info("Printing OR for user account... ");
            	
            	for (Map.Entry<String, Object> entry : params.entrySet())
            	{
            	    System.out.println("PARAM: " + entry.getKey() + "/" + entry.getValue());
            	    logger.info("PARAM: " + entry.getKey() + "/" + entry.getValue());
            	}
            	
            	System.out.println("Using Jasper Directory: " + JASPER_FILES_DIR);
            	System.out.println("OR template jasper filename: " + OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME);
            	
            	logger.info("Using Jasper Directory: " + JASPER_FILES_DIR);
            	logger.info("OR template jasper filename: " + OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME);
            	
            	JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_DIR + OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME, params, connection);
	            JRAbstractExporter exporterPDF = new JRPdfExporter();
	            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
	            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
	            exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
	            response.setHeader("Content-disposition", "attachment; filename=\"" +  orNumber + ".pdf" + "\"");
	            response.setContentType("application/pdf");
	            exporterPDF.exportReport();
	            
	            System.out.println("PDF successfully created.");
            	logger.info("PDF successfully created.");
            }
        }
        catch (Exception ex) 
        {
            String connectMsg = "Could not create the report " + ex.getMessage() + ex.getLocalizedMessage();
            System.out.println("Error encountered during creation of PDF : " + ex.getMessage());
            logger.error("Error encountered during creation of PDF : " + ex.getMessage());
        
        }
        finally
        {
        	if(connection != null)
        	{
        		connectionDao.closeConnection();
        	}
        	session.removeAttribute("orNumber");
        }
    }

    
    @RequestMapping(value = "/download/pdf.action", method = RequestMethod.GET)
    public void generateOR(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws ServletException, IOException {
        System.out.println("went to generate OR..........");

        Map params = new HashMap();
        params.put("p_or_number", request.getParameter("orNumber"));

        Connection connection = null;
        IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
        
        try {
        	System.out.println("\nGetting a connection..");
        	connection = connectionDao.getConnection();
            
            // fill the report
            JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_DIR + OFFICIAL_RECEIPT_TEMPLATE_JASPER_FILENAME, params, connection);
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            response.setHeader("Content-disposition", "attachment; filename=\"" + request.getParameter("orNumber") + ".pdf" + "\"");
            response.setContentType("application/pdf");
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
        	session.removeAttribute("orNumber");
        	session.removeAttribute("excessPayment");
        }
    }
    
    @RequestMapping(value = "/download/collections/pdf.action", method = RequestMethod.GET)
   public void generateCollections(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String name = request.getParameter("postedDate").toString();
        
        String fileUrl = "file:///"+REPORT_LOCATION + name + "_collections.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + name + "_collections.pdf");
        BufferedInputStream  bis = null;
        BufferedOutputStream bos = null;
        System.out.println("file url : " + fileUrl);
        try {
        
        URL url = new URL (fileUrl);
        URLConnection urlc = url.openConnection();
        // Use Buffered Stream for reading/writing.
        bis = new BufferedInputStream(urlc.getInputStream());
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        // Simple read/write loop.
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        //return new ModelAndView("");
    } catch(final MalformedURLException e) {
        System.out.println ( "MalformedURLException."+e.toString() );
        //return new ModelAndView("billHistory");
    } catch(final IOException e) {
        System.out.println ( "IOException." + e.toString());
        //return new ModelAndView("billHistory");
    } finally {
        if (bis != null)
            bis.close();
        if (bos != null)
            bos.close();
    }
    }
    
    
    /** used by Billing Payment to generate PDF
    * 
    * @param response
    * @param request
    * @param session
    * @throws ServletException
    * @throws IOException
    */
    @RequestMapping(value = "/download/dailyPayments/pdf.action", method = RequestMethod.GET)
    public void generateDailyPayments(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws ServletException, IOException, ParseException {
        System.out.println("collectibleDate is : " + request.getParameter("collectibleDate"));
        //System.out.println("param collectibleDate is : " + collectibleDate);
        Map params = new HashMap();
        String[] dates = request.getParameter("collectibleDate").split(" ");
        System.out.println(dates[1]);
        System.out.println(dates[2]);
        System.out.println(dates[3]);
        
       String str = dates[1]+" "+dates[2]+" "+dates[3];
       DateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
       Date dates2 = (Date)formatter.parse(str);
       DateFormat out = new SimpleDateFormat("MM/dd/yyyy");
        
        params.put("collectibleDate", out.format(dates2));

        Connection connection = null;
        IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
        
        try {
        	System.out.println("\nGetting a connection from generateDailyPayments()...");
        	connection = connectionDao.getConnection();
            
            // fill the report
            // JasperPrint jasperprint = JasperFillManager.fillReport(context.getRealPath(REPORT_PATH) + "\\" + BILLING_PAYMENT_JASPER_FILENAME, params, connection);
            JasperPrint jasperprint = JasperFillManager.fillReport(JASPER_FILES_DIR + DAILY_PAYMENTS_JASPER_FILENAME, params, connection);
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            response.setHeader("Content-disposition", "attachment; filename=\"DailyPayments_" + out.format(dates2) + ".pdf" + "\"");
            response.setContentType("application/pdf");
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


    /**
     * @param billingService the billingService to set
     */
    @Autowired
    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }
}
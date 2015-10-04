/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.BillingBean;
import com.homeportal.model.LedgerSummary;
import com.homeportal.model.OfficialReceipt;
import com.homeportal.model.OrSeries;
import com.homeportal.service.AdjustmentService;
import com.homeportal.service.AmenitiesRequestService;
import com.homeportal.service.AssociationDueService;
import com.homeportal.service.BillingService;
import com.homeportal.service.LedgerService;
import com.homeportal.service.OfficialReceiptService;
import com.homeportal.service.OrSeriesService;
import com.homeportal.service.ServiceRequestService;
import com.homeportal.service.WaterReadingService;
import com.homeportal.util.CommonUtil;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.DateTimeUtil;
import com.homeportal.util.MessageBundle;



/**
 *
 * @author racs
 */
@Controller
public class AdminBillController {
	private static Logger logger = Logger.getLogger(AdminBillController.class);
	private static final String WHITE_SPACE = " ";
	
	
    @Autowired
    ServletContext context;
    
    private BillingService billingService;
    private OfficialReceiptService officialReceiptService;
    private AmenitiesRequestService amenitiesRequestService;
    private ServiceRequestService serviceRequestService;
    private OrSeriesService orSeriesService;
    private AssociationDueService assocDueService;
    private WaterReadingService waterReadingService;
    private AdjustmentService adjustmentService;
    private LedgerService ledgerService;
    
    
    private static final String FERNDALE_USERID = MessageBundle.getProperty("ferndale_userId");
    
    public AdminBillController()
    {
       
    }
    
    
    /* Gets the search box where Payee Name and mode of payment can be entered
     * 
     * */
    @RequestMapping("/admin-bill.action")
    String getBill(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	logger.info("Getting bill for payee: " + request.getParameter("payeeName"));
    	
    	String userId = request.getParameter("userId");
		String houseId = request.getParameter("houseId");
		String payeeName = request.getParameter("payeeName");
		String payeeAddress = request.getParameter("payeeAddress");
		String modeOfPayment = request.getParameter("modeOfPayment");
		String chequeNumber = request.getParameter("chequeNumber");
		String bank = request.getParameter("bank");
		
		logger.info("userId: " + userId);
		logger.info("houseId: " + houseId);
		logger.info("payeeName: " + payeeName);
		logger.info("payeeAddress: " + payeeAddress);
		logger.info("modeOfPayment: " + modeOfPayment);
		logger.info("chequeNumber: " + chequeNumber);
		logger.info("bank: " + bank);
		
		
		// set values in the session
		session.setAttribute("userId", userId);
        session.setAttribute("houseId", houseId);
        session.setAttribute("payeeName", payeeName);
        session.setAttribute("payeeAddress", payeeAddress);
        session.setAttribute("modeOfPayment", request.getParameter("modeOfPayment"));
        session.setAttribute("chequeNumber", chequeNumber);
        session.setAttribute("bank", bank);
        
        return "admin-bill";
    }
    
    
    
    
    /*
     * Gets the list of bill to be paid by the Payee
     * 
     * */
    @RequestMapping("/admin/viewListBill.action")			// TODO
    public @ResponseBody
    Map<String, ? extends Object> viewMonthlyBill(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	System.out.println("Getting view of monthly bill...");
    	logger.info("Getting view of monthly bill...");
    	
    	// get the values from the session
    	String userId = (String) session.getAttribute("userId");
    	String houseId = (String) session.getAttribute("houseId");
    	String payeeName = (String) session.getAttribute("payeeName");
    	
    	// unc-comment these when debugging is needed
    	/*String payeeAddress = (String) session.getAttribute("payeeAddress");
    	String modeOfPayment = (String) session.getAttribute("modeOfPayment");
    	String chequeNumber = (String) session.getAttribute("chequeNumber");
    	String bank = (String) session.getAttribute("bank");
    	
    	logger.info("userId: " + userId);
		logger.info("houseId: " + houseId);
		logger.info("payeeName: " + payeeName);
		logger.info("payeeAddress: " + payeeAddress);
		logger.info("modeOfPayment: " + modeOfPayment);
		logger.info("chequeNumber: " + chequeNumber);
		logger.info("bank: " + bank);*/
		
		
        // get the list of monthly bills for a user
    	System.out.println("******* Getting the list of monthly bill for the homeowner: " + payeeName);
        logger.info("******* Getting the list of monthly bill for the homeowner: " + payeeName);
        List<BillingBean> bill = billingService.viewBillingByPortal(Integer.parseInt(userId), Integer.parseInt(houseId), payeeName);
        
        List<BillingBean> bills = new ArrayList<BillingBean>();
            
            for (BillingBean billingBean : bill)
            {
            	billingBean.setAmount(CommonUtil.formatDouble(billingBean.getAmount(), ConstantsUtil.TWO_DECIMAL));
            	billingBean.setPaidAmount(CommonUtil.formatDouble(billingBean.getPaidAmount(), ConstantsUtil.TWO_DECIMAL));
            	billingBean.setBalance(CommonUtil.formatDouble(billingBean.getBalance(), ConstantsUtil.TWO_DECIMAL));
            	
            	bills.add(billingBean);
            	
            	 
                /*DecimalFormat decim = new DecimalFormat("0.00");
                billingBean.setAmount(Double.parseDouble(decim.format(billingBean.getAmount())));
                billingBean.setPaidAmount(Double.parseDouble(decim.format(billingBean.getPaidAmount())));
                billingBean.setBalance(Double.parseDouble(decim.format(billingBean.getBalance())));
                bills.add(billingBean);*/
            }
            return getMap(bill);
    }
    
    
    
    
    /*
     * Method that sets the parameters to be used by the OR generation util
     * 
     * */
    @RequestMapping(value="/admin/payBill.action")
    public @ResponseBody
    Map<String, ? extends Object> allocateBills(@RequestParam Object data, HttpServletRequest request, HttpSession session)  
    			throws Exception 
    {
    	System.out.println("*** Paying bill...");
    	logger.info("*** Paying bill...");
    	
    	
    	// get the values from the session
    	String userId = (String) session.getAttribute("userId");
    	String houseId = (String) session.getAttribute("houseId");
    	String payeeName = (String) session.getAttribute("payeeName");
    	String payeeAddress = (String) session.getAttribute("payeeAddress");
    	String modeOfPayment = (String) session.getAttribute("modeOfPayment");
    	String chequeNumber = (String) session.getAttribute("chequeNumber");
    	String bank = (String) session.getAttribute("bank");
    	
    	
    	// un-comment these when debugging is needed
    	logger.info("userId: " + userId);
		logger.info("houseId: " + houseId);
		logger.info("payeeName: " + payeeName);
		logger.info("payeeAddress: " + payeeAddress);
		logger.info("modeOfPayment: " + modeOfPayment);
		logger.info("chequeNumber: " + chequeNumber);
		logger.info("bank: " + bank);
		
    	
    	// create new OR object
        OfficialReceipt or = new OfficialReceipt();
        
        int current = 0;
        
        try {
            List<OrSeries> orSeries = orSeriesService.getSeriesNumber();
            
            for (OrSeries os : orSeries) {
                current = os.getCurrent();
            }
            
            List<BillingBean> alloc = billingService.getAllocated(data);
            
            logger.info("============================  DEBUGGING BILLING ===================================");
            logger.info("Number of J.O. to pay: " + alloc.size());
            
            System.out.println("============================  DEBUGGING BILLING ===================================");
            System.out.println("Number of J.O. to pay: " + alloc.size());
            
            
            // GET THE TOTAL BALANCE and TOTAL AMOUNT TO PAY (to calculate the excess payment)
            Double totalBalance = new Double("0.00");
            Double totalAmountToPay = new Double("0.00");
            Double excessPayment = new Double("0.00");
            
            // format to 2 decimal places
            DecimalFormat decim = new DecimalFormat("0.00");
            
            for (BillingBean bb : alloc) 
            {
            	System.out.println("\nAmount: " + bb.getAmount());
            	System.out.println("Balance: " + bb.getBalance());
            	System.out.println("Amount to pay: " + bb.getAmountToPay());
            	System.out.println("Order Type Id: " + bb.getOrderTypeId());
            	
            	logger.info("\nAmount: " + bb.getAmount());
            	logger.info("Balance: " + bb.getBalance());
            	logger.info("Amount to pay: " + bb.getAmountToPay());
            	logger.info("Order Type Id: " + bb.getOrderTypeId());
            	
            	/*totalBalance = totalBalance + Double.parseDouble(decim.format(bb.getBalance()));
            	totalAmountToPay = totalAmountToPay + Double.parseDouble(decim.format(bb.getAmountToPay()));*/
            	
            	totalBalance = totalBalance + CommonUtil.formatDouble(bb.getBalance(), ConstantsUtil.TWO_DECIMAL);
            	totalAmountToPay = totalAmountToPay + CommonUtil.formatDouble(bb.getAmountToPay(), ConstantsUtil.TWO_DECIMAL);
            }
            
            System.out.println("\n*** TOTAL BALANCE: " + totalBalance);
            System.out.println("*** TOTAL AMOUNT TO PAY: " + totalAmountToPay);
            
            logger.info("\n*** TOTAL BALANCE: " + totalBalance);
            logger.info("*** TOTAL AMOUNT TO PAY: " + totalAmountToPay);
            
            excessPayment = totalBalance - totalAmountToPay;
            
            System.out.println("excessPayment: " + excessPayment);
            
            // if excessPayment is a NEGATIVE number, it means there is really an excess
            if (excessPayment < 0)
            {
            	excessPayment = Math.abs(excessPayment);
            	
            	System.out.println("EXCESS PAYMENT: " + excessPayment);
                logger.info("EXCESS PAYMENT: " + excessPayment);
                
                session.setAttribute("excessPayment", excessPayment);
            }
            else
            {
            	session.setAttribute("excessPayment", null);
            }
            
            
            System.out.println("============================  END DEBUGGING BILLING ===================================");
            logger.info("============================  END DEBUGGING BILLING ===================================");
            
            
            // GET THE TOTAL AMOUNT PAID FOR PENALTY THEN UPDATE THE PENALTY COLUMN IN THE ledger_summary table
            Double totalAmountPaidPenalty = new Double("0.00");
            
            for (BillingBean bb : alloc) 
            {
                System.out.println("id : "+bb.getId() + " userId : " + bb.getUserId() + " amount to Pay : " + bb.getAmountToPay() 
                		+" paid amount :" + bb.getPaidAmount() +" request id : "+bb.getRequestId() + " order type id : " + bb.getOrderTypeId());
                
                or.setOrNumber(current);
                or.setJobOrderId(bb.getId());
                
                logger.info("Job Order ID: " + bb.getId());
                logger.info("order type id: " + bb.getOrderTypeId());
                
                or.setUserId(bb.getUserId());
                or.setAmount(bb.getAmountToPay());
                or.setCreatedAt(new Date());
                or.setUpdatedAt(new Date());
                or.setStatus("T");
                
                // requested by Chris to be added on 2013-07-22
                or.setBankName(bank);
                or.setChequeNumber(chequeNumber);
                
                Double paidAmount = 0.0;
                Double amount = 0.0;
                if(bb.getPaidAmount() == 0){
                    paidAmount = bb.getAmountToPay();
                }else{
                    paidAmount = bb.getPaidAmount() + bb.getAmountToPay();
                }
                
                amount = bb.getAmount() - paidAmount;
                
                
                // SAVE OR
                logger.info("Saving this official receipt in the database. OR number: " + current);
                System.out.println("############### Saving this official receipt in the database. OR number: " + current);
                
                or.setHouseId(Integer.parseInt(houseId));
                
                officialReceiptService.payBills(or);
                
                
                // UPDATE PAID AMOUNT
                if(bb.getOrderTypeId() == ConstantsUtil.JOB_ORDER_TYPE_AMENITY_REQUEST)
                {
                	System.out.println("Paying amenity request...");
                	logger.info("Paying amenity request...");
                    amenitiesRequestService.updatePaidAmount(paidAmount, bb.getRequestId(), amount);
                }
                
                if(bb.getOrderTypeId() == ConstantsUtil.JOB_ORDER_TYPE_SERVICE_REQUEST)
                {
                	System.out.println("Paying service request...");
                	logger.info("Paying service request...");
                    serviceRequestService.updatePaidAmount(paidAmount, bb.getRequestId(), amount);
                }
                
                if(bb.getOrderTypeId() == ConstantsUtil.JOB_ORDER_TYPE_WATER_BILL)
                {
                	System.out.println("Paying water bill...");
                	logger.info("Paying water bill...");
                	waterReadingService.updateWaterReadingPaidAmount(paidAmount, bb.getRequestId(), amount);
                }
                
                if(bb.getOrderTypeId() == ConstantsUtil.JOB_ORDER_TYPE_ASSOC_DUE)
                {
                	System.out.println("Paying assoc due...");
                	logger.info("Paying assoc due...");
                	assocDueService.updateAssocDuePaidAmount(paidAmount, bb.getRequestId(), amount);
                }
                
                if(bb.getOrderTypeId() == ConstantsUtil.JOB_ORDER_TYPE_ADJUSTMENT)
                {
                	System.out.println("Paying adjustment...");
                	logger.info("Paying adjustment...");
                	adjustmentService.updateAdjustmentPaidAmount(paidAmount, bb.getRequestId());
                }
                
                if(bb.getOrderTypeId() == ConstantsUtil.JOB_ORDER_TYPE_PENALTY)
                {
                	System.out.println("Paying penalty...");
                	logger.info("Paying penalty...");
                	
                	System.out.println("AMOUNT PAID FOR PENALTY: " + paidAmount);
                	totalAmountPaidPenalty = totalAmountPaidPenalty + paidAmount;
                	
                	adjustmentService.updateAdjustmentPaidAmount(paidAmount, bb.getRequestId());
                }
            }
            

            System.out.println("totalAmountPaidPenalty: " + totalAmountPaidPenalty);
            // get the current ledger_summary penalty
            LedgerSummary ls = ledgerService.getLedgerSummaryByHouseId(Integer.parseInt(houseId));
            Double currentLedgerSummaryPenalty = new Double("0.00");
            Double newLedgerSummaryPenalty = new Double("0.00");
            
            
            if (ls != null)
            {
            	currentLedgerSummaryPenalty = CommonUtil.formatDouble(ls.getPenalty(), ConstantsUtil.TWO_DECIMAL);
            	newLedgerSummaryPenalty = currentLedgerSummaryPenalty - totalAmountPaidPenalty;
            	
            	// newLedgerSummaryPenalty = current - penaltyTotalAmountPaid
                System.out.println("newLedgerSummaryPenalty: " + newLedgerSummaryPenalty);
            	
                // update the ledger_summary table for the newLedgerSummaryPenalty
            	ls.setPenalty(newLedgerSummaryPenalty);
            	ledgerService.updateLedgerSummary(ls);
            }
            
            
            // UPDATE OR SERIES
            orSeriesService.updateCurrentNumber(current + 1);
            
            
            logger.info("=========== GETTING THE BILLS TO BE PAID FOR BY THE OWNER... ====================");
            List<BillingBean> bill = billingService.viewBillingByPortal(Integer.parseInt(userId), Integer.parseInt(houseId), payeeName);
            
            
            // set the parameters in the session
            logger.info("Setting OR number in the Session...");
            session.setAttribute("orNumber", String.valueOf(current));
            
            // SET THE OR DATE FOR THE CREATION OF THE OR PDF
            final String format = "MM/dd/yyyy";
            session.setAttribute("orDate", DateTimeUtil.convertDateToString(or.getCreatedAt(), format));
            
            /*session.setAttribute("p_mode", billerModeOfPayment);
            session.setAttribute("p_cheque", billerChequeNumber);
            session.setAttribute("p_bank", billerBank);
            session.setAttribute("p_payee_name", billerPayeeName);
            session.setAttribute("p_payee_address", billerPayeeAddress);*/
            
            
            // populate parameters for OR Jasper Report 
            /*Map<String, Object> jasperParams = new HashMap<String, Object>();
            jasperParams.put("p_or_number", current);
            jasperParams.put("p_mode", modeOfPayment);
            jasperParams.put("p_cheque", chequeNumber);
            jasperParams.put("p_bank", bank);
            // jasperParams.put("p_payee_name", payeeName);
            jasperParams.put("p_payee_address", payeeAddress);
            
            // format the date first into "MM-dd-yyyy" before it will be sent to PDF generating controller
            final String format = "MM-dd-yyyy";
            jasperParams.put("p_date", DateTimeUtil.convertDateToString(or.getCreatedAt(), format));
            
            // set the co-owner in the OR pdf generated if there is a co-owner for the house
            Map<String, String> coOwnerDetails = OwnerUtil.getCoOwner(Integer.parseInt(userId));
            StringBuilder sb = new StringBuilder();
            
            if (coOwnerDetails != null && coOwnerDetails.size() > 0)
            {
            	// append co-owner to owner
            	sb.append(payeeName).append(" / ")
            	.append(coOwnerDetails.get("coFirstName").toUpperCase())
            	.append(WHITE_SPACE)
            	.append(coOwnerDetails.get("coLastName").toUpperCase());
            	
            	jasperParams.put("p_payee_name", sb.toString());
            }
            else
            {
            	jasperParams.put("p_payee_name", payeeName);
            }*/
            
            
            return getMap(bill);
            
        } catch (Exception e) {
            System.out.println("went to error ----------------- " + e.getMessage()); // CHRIS
            return getModelMapError("Error trying to save payment for the bill.");
        }
    }
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param bill
     * @return
     */
    private Map<String, Object> getMap(List<BillingBean> bill) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", bill.size());
        modelMap.put("data", bill);
        modelMap.put("success", true);
        System.out.println("model map " + modelMap);
        return modelMap;
    }
    
     /**
     * Generates modelMap to return in the modelAndView in case of exception
     *
     * @param msg message
     * @return
     */
    private Map<String, Object> getModelMapError(String msg) {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("message", msg);
        modelMap.put("success", false);

        return modelMap;
    }

    /**
     * @param billingService the billingService to set
     */
    @Autowired
    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }

    /**
     * @param officialReceiptService the officialReceiptService to set
     */
    @Autowired
    public void setOfficialReceiptService(OfficialReceiptService officialReceiptService) {
        this.officialReceiptService = officialReceiptService;
    }

    /**
     * @param amenitiesRequestService the amenitiesRequestService to set
     */
    @Autowired
    public void setAmenitiesRequestService(AmenitiesRequestService amenitiesRequestService) {
        this.amenitiesRequestService = amenitiesRequestService;
    }

    /**
     * @param serviceRequestService the serviceRequestService to set
     */
    @Autowired
    public void setServiceRequestService(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    /**
     * @param orSeriesService the orSeriesService to set
     */
    @Autowired
    public void setOrSeriesService(OrSeriesService orSeriesService) {
        this.orSeriesService = orSeriesService;
    }
    
    
    /**
     * @param associationDueService the associationDueService to set
     */
    @Autowired
    public void setAssociationDueService(AssociationDueService associationDueService) {
        this.assocDueService = associationDueService;
    }

    
    /**
     * @param waterReadingService the waterReadingService to set
     */
    @Autowired
    public void setWaterReadingService(WaterReadingService waterReadingService)
    {
        this.waterReadingService = waterReadingService;
    }
    
    /**
     * @param adjustmentService the adjustmentService to set
     */
    @Autowired
    public void setAdjustmentService(AdjustmentService adjustmentService)
    {
        this.adjustmentService = adjustmentService;
    }
   
    
    /**
     * @param ledgerService the ledgerService to set
     */
    @Autowired
    public void setLedgerService(LedgerService ledgerService)
    {
        this.ledgerService = ledgerService;
    }
    
}

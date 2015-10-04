/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.model.AssociationDue;
import com.homeportal.model.House;
import com.homeportal.model.Rates;
import com.homeportal.service.AssociationDueService;
import com.homeportal.service.HouseService;
import com.homeportal.service.RatesService;
import com.homeportal.util.ConstantsUtil;

/**
 *
 * @author Peter
 */
@Controller
public class AdminAssocDuesController 
{
	private static Logger logger = Logger.getLogger(AdminAssocDuesController.class);
	private AssociationDueService assocDueService;
	private HouseService houseService;
	private RatesService ratesService;
    
	
    @RequestMapping("/admin-assoc-dues-yearly")
    String view(HttpSession session) throws Exception 
    {
    	logger.info("went to view of AdminAssocDuesController...");
    	System.out.println("went to view of AdminAssocDuesController");
       
    	return "admin-assoc-dues-yearly";
    }
    
    
    // ###############################################
    
    @RequestMapping(value="/admin-get-total-assoc-due.action")  // THIS SHOULD BE NAMED AS GET THE ASSOC DUE TO BE PAID, CREATION SHOULD BE DONE WHEN 'SUBMIT' IS CLICKED!!!
    public @ResponseBody
    Map<String, ? extends Object> getTotalAssociationDues(@RequestParam("userId") String userId, @RequestParam("houseId") String houseId, 
    													HttpSession session) throws Exception {
    	System.out.println("went to view of getTotalAssociationDues() method...");
    	
    	try
    	{
    		int uId = Integer.parseInt(userId);
    		int hId = Integer.parseInt(houseId);
    		
    		int lotArea = 0;
    		double rate = 0.00;
    		double totalAssocDue = 0.00;
    		
    		
    		// get the lot_area from the HOUSE table using house_id and get the rate from the RATES table using the code 'assocdue'
    		House house = houseService.getHouseByHouseId(hId);
    		Rates rates = ratesService.getRatesByCode(ConstantsUtil.ASSOC_DUE_CODE);
    		
    		if (house != null)
    		{
    			lotArea = house.getLotArea();
    			System.out.println("lotArea: " + lotArea);
    		}
    		
    		if (rates != null)
    		{
    			rate = rates.getAmount();
    			System.out.println("rate: " + rate);
    			
    		}
    		
    		totalAssocDue = (lotArea * rate) * ConstantsUtil.ASSOC_DUE_MODE_OF_PAYMENT_ANNUAL;
    		
    		logger.info("Total Association Due WITHOUT discount yet: " + totalAssocDue);
    		System.out.println("Total Association Due WITHOUT discount yet: " + totalAssocDue);
    		
    		Map<String, Object> modelMap = new HashMap<String, Object>(4);
    		modelMap.put("lotArea", lotArea);
            modelMap.put("rate", rate);
    		modelMap.put("data", totalAssocDue);
            modelMap.put("success", true);
            System.out.println(modelMap);
          
            return modelMap;
        } 
    	catch (Exception e)
    	{
    		// logger.error("Error retrieving user from database.", e);
    		System.out.println("Error getting association dues from database. " + e.getMessage());
    		return getModelMapError("Error getting association dues from database. " + e.getMessage());
    	}
    }
    
    
    
    
    /**
     * Used to submit payment for annual association dues
     * */
     @RequestMapping(value="/admin-pay-assoc-dues-yearly.action", method=RequestMethod.POST)
     public @ResponseBody 
     Map<String, ? extends Object> submitPaymentAnnualAssocDues(HttpServletRequest request, HttpSession session) throws Exception
     {
         logger.info("Submitting payment for annual association dues...");
         System.out.println("\nSubmitting payment for annual association dues...");
         
         try
     	{
     		System.out.println("userId: " + request.getParameter("requestorUserId"));
     		System.out.println("houseId: " + request.getParameter("requestorHouseId"));
     		System.out.println("amount: " + request.getParameter("amount"));
     		System.out.println("postedDate: " + request.getParameter("postedDate")); // THIS CAUSES ERROR, FOR THE MEANTIME THIS IS EMPTY STRING
     		System.out.println("rate: " + request.getParameter("rate"));
     		System.out.println("remarks: " + request.getParameter("remarks"));
     		System.out.println("lotArea: " + request.getParameter("lotArea"));
     		System.out.println("concatenatedRemarks: " + request.getParameter("concatenatedRemarks"));
     		
     		
     		
     		int userId = Integer.parseInt(request.getParameter("requestorUserId"));
     		int houseId = Integer.parseInt(request.getParameter("requestorHouseId"));
     		// String postedDate = "TODO"; // TODO
     		double rate = Double.parseDouble(request.getParameter("rate"));
     		String remarks = request.getParameter("remarks");
     		double lotArea = Double.parseDouble(request.getParameter("lotArea"));
     		double amount = Double.parseDouble(request.getParameter("amount"));
     		String postedDate = request.getParameter("postedDate");
     		String newRemarks = request.getParameter("concatenatedRemarks") + " - " + remarks;
     		
     		AssociationDue assocDue = assocDueService.createAssociationDue(userId, houseId, "", postedDate, rate, newRemarks, lotArea, amount);
     		// boolean updated = assocDueService.updateAssocDueNewAmountToPay(userId, houseId, amount, remarks);
     		
     		// ONCE ASSOC DUE IS CREATED WITH STATUS 'NEW', UPDATE IT TO STATUS 'DONE' AND BILLED = 1 SO THAT THE TRIGGER WILL DO ITS JOB
     		boolean updated = false;
     		if (assocDue != null)
     		{
     			assocDue.setStatus(ConstantsUtil.DONE);
     			assocDue.setBilled(1);
     			
     			updated = assocDueService.updateAssociationDue(assocDue);
     		}
     		
     		
     		Map<String, Object> map = new HashMap<String, Object>(2);
     		map.put("data", assocDue);
     		
     		if (updated)
     		{
     			map.put("success",Boolean.TRUE);
     		}
     		else
     		{
     			map.put("success",Boolean.FALSE);
     		}
     		
     		return map;
         } 
     	catch (Exception e)
         {
             return getModelMapError("Error trying to create association due: " + e.getMessage());
         }
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

    
    
    /*private Map<String, Object> getMap(List<AssociationDue> assocDueList) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", assocDueList.size());
        modelMap.put("data", assocDueList.get(0));
        modelMap.put("success", true);
        System.out.println(modelMap);
        return modelMap;
    }*/
    
    
    
    /**
     * @param assocDueService the assocDueService to set
     */
    @Autowired
    public void setAssociationDueService(AssociationDueService assocDueService) {
        this.assocDueService = assocDueService;
    }
    
    
    /**
     * @param houseService the houseService to set
     */
    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }
    
    
    /**
     * @param ratesService the ratesService to set
     */
    @Autowired
    public void setRatesService(RatesService ratesService) {
        this.ratesService = ratesService;
    }
}

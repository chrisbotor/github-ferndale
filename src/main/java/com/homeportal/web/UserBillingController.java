/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.BillingBean;
import com.homeportal.service.BillingService;
import com.homeportal.util.HomePortalUtil;
import com.homeportal.util.ValidationUtil;


/**
 *
 * @author racs
 */
@Controller
public class UserBillingController
{
	private static Logger logger = Logger.getLogger(UserBillingController.class);
    
    private BillingService billingService;
    
	
    
    /**
     * Gets the jsp page for viewing the user/owner's outstanding balances
     * */
    @RequestMapping("/user-balances.action")
    String viewUserBalances() throws Exception
    {
    	return "user-balances";
    }
    
    
    
    /**
     * Gets the jsp page for viewing the user/owner's monthly statement
     * */
    @RequestMapping("/user-statement.action")
    String view(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	logger.info("******* Getting a list of monthly statement by the home owner...");
    	
    	String houseIdParam = request.getParameter("houseId");
    	int houseId = 0;
    	
    	if (ValidationUtil.hasValue(houseIdParam))
    	{
    		houseId = Integer.parseInt(houseIdParam);
    	}
    	else
    	{
    		logger.error("House ID is NULL.");
    	}
    	
    	
    	// save the houseId in the Session
        session.setAttribute("houseId", houseId);
        
        return "user-statement";
    }
    
    
    
    /**
     * Gets all the user/owner's outstanding balances from the database
     * **/
    @RequestMapping("/user-get-outstanding-balances.action")
    public @ResponseBody
    Map<String, ? extends Object> getOutstandingBalances(HttpSession session, HttpServletRequest request) 
    		throws Exception 
    {
    	String houseIdParam = (String) request.getParameter("houseId");
    	
    	int userId = (Integer) session.getAttribute("userId");
    	int houseId = 0;
        
        if (ValidationUtil.hasValue(houseIdParam))
        {
        	houseId = Integer.parseInt(houseIdParam);
        }
        else
        {
        	logger.info("houseId is NULL. " + houseIdParam);
        }
        
    	
    	try {
    		
    		logger.info("======================================= Getting outstanding balances for this owner ===================================");
        	System.out.println("======================================= Getting outstanding balances for this owner ===================================");
        	
    		logger.info("userId: " + userId);
    		logger.info("houseId: " + houseId);
    		System.out.println("userId: " + userId);
    		System.out.println("houseId: " + houseId);
    		
    		// this gets the balances
    		List<BillingBean> bill = billingService.getUserOutstandingBalances(userId, houseId);
    		
    		System.out.println("Got this number of bills: " + bill.size());
    		
    		
             BillingBean bean = new BillingBean();
             double amount = 0.0;
             double paidAmount = 0.0;
             double bal = 0.0;
             bean.setDescription("Total");
             for (BillingBean bb : bill) {
                 amount = amount + bb.getAmount();
                 paidAmount = paidAmount + bb.getPaidAmount();
                 bal = amount - paidAmount;
            }
             
             bean.setAmount(HomePortalUtil.formatAmount(amount));
             bean.setPaidAmount(HomePortalUtil.formatAmount(paidAmount));
             bean.setBalance(HomePortalUtil.formatAmount(bal));
             bill.add(bean);
             
             return getMap(bill);
             
        } catch (Exception e) {
             return getModelMapError("Error getting monthly bill for this owner with userId: " + userId + ". EXCEPTION " + e.getMessage());
        }
    }
    
    
    
    /**
     * Gets the user/owner's monthly statement (up to 6 months only)
     * */
    @RequestMapping("/user-get-monthly-statement.action")
    public @ResponseBody
    Map<String, ? extends Object> viewStatement(HttpSession session, HttpServletRequest request) throws Exception 
    {
        try {
        	 System.out.println("Getting a monthly statement...");
        	 logger.info("Getting a monthly statement...");
        	 
        	 int userId = (Integer) session.getAttribute("userId");
        	 int houseId = (Integer) session.getAttribute("houseId");
        	 
        	 String url = "userId=" + userId + "&houseId=" + houseId;
             
             List<BillingBean> bill = billingService.getUserMonthlyStatementList(userId, houseId);
             
             List<BillingBean> newBill = new ArrayList<BillingBean>();
             String desc = "";
             BillingBean b = new BillingBean();
             
             StringBuilder sb = new StringBuilder(); 
             
             for (BillingBean billingBean : bill)
             {
            	desc = url + "&billingMonth=" + billingBean.getReportName();
            	
            	// desc = billingBean.getDescription()+"_"+String.valueOf(billingBean.getHouseId())+"_"+billingBean.getReportName(); // important
                b.setDescription(desc);
                b.setPostedDate(billingBean.getPostedDate());
                b.setPayeeName(billingBean.getPayeeName());
                session.setAttribute("billName", desc);
                newBill.add(b);
            }
             
            return getMap(newBill);
            
        }
        catch (Exception e) 
        {
        	logger.error("Error retrieving Home Owner monthly statement.", e);
             return getModelMapError("Error retrieving Home Owner monthly statement." + e.toString());
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
}

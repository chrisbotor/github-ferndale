/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.web;

import java.util.ArrayList;
import java.util.Date;
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

import com.homeportal.model.OfficialReceipt;
import com.homeportal.service.OfficialReceiptService;
import com.homeportal.util.ValidationUtil;

/**
 *
 * @author racs
 */
@Controller
public class ORController 
{
	private static Logger logger = Logger.getLogger(ORController.class);

	private OfficialReceiptService orService;
	
    
    
    /**
     * Gets a list of OR already paid by the home owner
     * */
    @RequestMapping("/user-or-history")
    String getORList(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	logger.info("******* Getting a list of OR already paid by the home owner...");
    	
    	Integer userId = (Integer) session.getAttribute("userId");
    	int houseId = 0;
    	
    	if (ValidationUtil.hasValue(request.getParameter("houseId")))
    	{
    		houseId = Integer.parseInt(request.getParameter("houseId"));
    	}
    	
    	logger.info("Owner userId: " + userId);
        logger.info("Owner houseId: " + houseId);
        
        // save the houseId in the Session
        session.setAttribute("houseId", houseId);
        
        return "user-or-history";
    }
    

    
    /**
     * Displays a page for the user so he can view the latest 10 OR created during payment
     * */
    @RequestMapping("/user-display-or.action")
    public @ResponseBody
    Map<String, ? extends Object> displayOR(HttpSession session) throws Exception 
    {
    	System.out.println("\n=== getting OR to display...");
    	
        try
        {
             Integer userId = (Integer) session.getAttribute("userId");
             Integer houseId = (Integer) session.getAttribute("houseId");
             
             System.out.println("userId: " + userId);
             System.out.println("houseId: " + houseId);
             
             
            /* logger.info("Owner houseId to be set in the session: " + houseId);
             session.setAttribute("houseId", houseId);*/
             
             List<OfficialReceipt> orList = orService.getORList(userId, houseId);
             
             // List<BillingBean> bill = billingService.viewOrByPortal(userId);
             
             return getMap(orList);
             
        }
        catch (Exception e)
        {
             return getModelMapError("Error trying to get OR list for home owner view." + e.toString());
        }
    }

    

    /**
     * Displays a page for the user so he can view the latest 10 OR created during payment 
     * (this should be the same as displayOR method, need to optimize this in the future)
     * */
    @RequestMapping("/admin-display-or.action")
    public @ResponseBody
    Map<String, ? extends Object> adminDisplayOR(HttpSession session) throws Exception 
    {
    	System.out.println("\n=== getting OR to display...");
    	logger.info("\n=== getting OR to display...");
    	
        try
        {
        	List<OfficialReceipt> orList = new ArrayList<OfficialReceipt>();
        	List<OfficialReceipt> orFromDB = new ArrayList<OfficialReceipt>();
        			
             Integer userId = (Integer) session.getAttribute("userId");
             Integer houseId = (Integer) session.getAttribute("houseId");
             Integer orNumber = (Integer) session.getAttribute("orNumber");
             
             System.out.println("userId: " + userId);
             System.out.println("houseId: " + houseId);
             System.out.println("orNumber: " + orNumber);
             
             /*logger.info("Owner houseId to be set in the session: " + houseId);
             session.setAttribute("houseId", houseId);*/
             
             
             // this gets OR when ADMIN selected "Search by OR number"
             if (orNumber > 0)
             {
            	 orFromDB = orService.getORByORNumber(orNumber); // it produces multiple display for 1 OR number, so just get the first result
            	 
            	 if (orFromDB != null && orFromDB.size() > 0)
            	 {
            		 orList.add(orFromDB.get(0));
            	 }
             }
             else
             {
            	// this gets OR when ADMIN selected "Search by requestor"
            	 orFromDB = orService.getORList(userId, houseId);
            	 
            	 if (orFromDB != null && orFromDB.size() > 0)
            	 {
            		 System.out.println("Number of OR results: " + orFromDB.size());
            		 
            		 for (OfficialReceipt or : orFromDB)
                	 {
                		 if (or != null && or.getStatus() != null && or.getStatus().equals("T"))  // display only those OR that are still active
                		 {
                			 orList.add(or); 
                		 }
                	 }
            		 
            	 }
            }
             
            
             return getMap(orList);
             
        }
        catch (Exception e)
        {
             return getModelMapError("Error trying to get OR list for." + e.toString());
        }
    }
    
    
    
    
    /**
     * Cancels a given OR number (sets the STATUS column of official_receipt table to 'F')
     * */
    @RequestMapping("/admin-cancel-or.action")
    public @ResponseBody
    Map<String, ? extends Object> cancelOR(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	String orNumberParam = request.getParameter("orNumber");
    	
    	System.out.println("\n=== cancelling OR number " + orNumberParam);
    	logger.info("\n=== cancelling OR number " + orNumberParam);
    	
        try
        {
        	List<OfficialReceipt> cancelledORList = new ArrayList<OfficialReceipt>();
        	int orNumber = 0;
        	
        	if (ValidationUtil.hasValue(orNumberParam))
        	{
        		orNumber = Integer.parseInt(orNumberParam);
        		
        		if (orNumber > 0)
                {
        			List<OfficialReceipt> orList = orService.getORByORNumber(orNumber);
               	 
	               	 if (orList != null)
	               	 {
	               		 for (OfficialReceipt or : orList)
	               		 {
	               			 if (or != null)
	               			 {
	               				 or.setStatus("F");
	               				 or.setUpdatedAt(new Date());
	    	               		 orService.cancelOR(or);
	               			 }
	               		 }
	               	}
                }
                else
                {
               	 //orList = orService.getORList(userId, houseId);
                }
        	}
        	
        	return getMap(cancelledORList);
             
        }
        catch (Exception e)
        {
             return getModelMapError("Error trying to cancel OR number " + orNumberParam + ": " + e.toString());
        }
    }
    
    /**
	 * Displays the search box where admin can specify search criteria
	 * */
    @RequestMapping("/admin-search-or-to-cancel")
    String getORSearch(HttpSession session) throws Exception 
    {
    	 logger.info("went to view of ORController...");
    	 System.out.println("went to view of ORController...");
    	
        /* session.removeAttribute("reqId");
         session.removeAttribute("desc");
         session.removeAttribute("reqStatus");
         session.removeAttribute("reqDate");*/
         
         return "admin-search-or-to-cancel";
    }
    
    
    
    /**
	 * Display search OR results in grid view so admin can cancel it
	 * */
    @RequestMapping("/admin-display-or-to-cancel.action")
    String displayORToCancel(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	logger.info("\nSearching for OR to cancel...");
    	System.out.println("\nSearching for OR to cancel...");
    	
    	String userIdParam = request.getParameter("requestorUserId");
    	String houseIdParam = request.getParameter("requestorHouseId");
    	String orNumberParam = request.getParameter("orNumber");
     	
 	   	logger.info("USER ID: " + userIdParam);
 	   	logger.info("HOUSE ID: " + houseIdParam);
 	   	logger.info("OR NUMBER: " + orNumberParam);
 	   
 	   	System.out.println("USER ID: " + userIdParam);
 	   	System.out.println("HOUSE ID: " + houseIdParam);
 	   	System.out.println("OR NUMBER: " + orNumberParam);
 	   	
 	   	int userId = 0;
 	   	int houseId = 0;
 	   	int orNumber = 0;
 	   	
 	   	if (ValidationUtil.hasValue(userIdParam))
 	   	{
 	   		userId = Integer.parseInt(userIdParam); 
 	   	}
 	   	
 	   	if (ValidationUtil.hasValue(houseIdParam))
	   	{
 	   		houseId = Integer.parseInt(houseIdParam);
	   	}
	   	
 	   	if (ValidationUtil.hasValue(orNumberParam))
	   	{
 	   		orNumber = Integer.parseInt(orNumberParam);
	   	}
	   	
	   
 	   	session.setAttribute("userId", userId);
 	   	session.setAttribute("houseId", houseId);
 	   	session.setAttribute("orNumber", orNumber);
	   
 	   	return "admin-display-or-to-cancel";
    }
    
    
    
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param orList
     * @return
     */
    private Map<String, Object> getMap(List<OfficialReceipt> orList) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", orList.size());
        modelMap.put("data", orList);
        modelMap.put("success", true);
        // System.out.println("model map " + modelMap);
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
     * @param orService the orService to set
     */
    @Autowired
    public void setOfficialReceiptService(OfficialReceiptService orService) {
        this.orService = orService;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.AmenityBean;
import com.homeportal.service.AdjustmentService;
import com.homeportal.service.SearchRequestsService;

/**
 *
 * @author Peter
 */
@Controller
public class AdminAdjustmentPenaltyController 
{
	private static Logger logger = Logger.getLogger(AdminAdjustmentPenaltyController.class);
	private AdjustmentService  adjustmentService;
    
	
    @RequestMapping("/admin-add-adjustment-penalty")
    String view(HttpSession session) throws Exception 
    {
    	logger.info("went to view of AdminAdjustmentPenaltyController...");
    	System.out.println("went to view of AdminAdjustmentPenaltyController");
       
    	return "admin-add-adjustment-penalty";
    }
    

    
    
   /**
    * Used to create adjustment or penalty
    * */
    @RequestMapping(value="/admin-create-adjustment-penalty.action", method=RequestMethod.POST)
    public @ResponseBody 
    Map<String, ? extends Object> createAdjustmentPenalty(HttpServletRequest request, HttpSession session) throws Exception
    {
        logger.info("Creating adjustment or penalty...");
        System.out.println("Creating adjustment or penalty...");
        
        try
    	{
    		String userIdParam = request.getParameter("requestorUserId");
    		String houseIdParam = request.getParameter("requestorHouseId");
    		
    		String type = request.getParameter("requestType");
    		String remarks = request.getParameter("remarks");
    		String amount = request.getParameter("amount");
    		String postedDate = request.getParameter("postedDate");
    		
    		
    		System.out.println("userIdParam: " + userIdParam);
    		System.out.println("houseIdParam: " + houseIdParam);
    		System.out.println("type: " + type);
    		System.out.println("remarks: " + remarks);
    		System.out.println("amount: " + amount);
    		System.out.println("postedDate: " + postedDate);
    		
    		adjustmentService.createAdjustment(Integer.parseInt(userIdParam), Integer.parseInt(houseIdParam), type, remarks, 
    							Double.valueOf(amount), postedDate);
    		
            Map<String, Object> map = new HashMap<String, Object>();
            // amenitiesRequestService.create(ar);
            
            map.put("success",Boolean.TRUE);
            
            return map;
        } 
    	catch (Exception e)
        {
            return getModelMapError("Error trying to create adjustment or penalty: " + e.getMessage());
        }
    }
    
   
	   
	   
   private Map<String, Object> getMapAR(List<AmenityBean> amenitys) {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", amenitys.size());
        modelMap.put("data", amenitys);
        modelMap.put("success", true);
        System.out.println(modelMap);
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
     * @param adjustmentService the adjustmentService to set
     */
    @Autowired
    public void setAdjustmentService(AdjustmentService adjustmentService) {
        this.adjustmentService = adjustmentService;
    }
    
}

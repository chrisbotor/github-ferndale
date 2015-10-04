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
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.JobOrderDefinitionBean;
import com.homeportal.service.JobOrderDefinitionService;
import com.homeportal.util.ValidationUtil;


/**
 *
 * @author Racs
 */
@Controller
public class JobOrderDefinitionController 
{
	private static Logger logger = Logger.getLogger(JobOrderDefinitionController.class);
  
   private JobOrderDefinitionService jobOrderDefinitionService;

   
   /**
    * Method used to load all the job orders for a specific request (i.e. if Amenity Request is selected, only job orders 
    * for amenities will be displayed.)
    * 
    * @param request - HttpServletRequest object
    * @param session - HttpSession object
    * 
    * */
   @RequestMapping(value="/common/get-job-order-definition.action")
   public @ResponseBody
   Map<String, ? extends Object> getJobOrderDefinitions(HttpServletRequest request, HttpSession session) throws Exception 
   {
	   System.out.println("Getting all the Job Order Definitions...");
	   logger.info("Getting all the Job Order Definitions...");
	   
	   String jobOrderTypeId = request.getParameter("jobOrderTypeId");
	   int jodTypeId = 0;
	   
	   
	   System.out.println("jobOrderTypeId " + jobOrderTypeId);
	   logger.info("jobOrderTypeId " + jobOrderTypeId);
	   
	   
	   if (ValidationUtil.hasValue(jobOrderTypeId))
	   {
		   System.out.println("Got jobOrderTypeId: " + jobOrderTypeId);
		   logger.info("Got jobOrderTypeId: " + jobOrderTypeId);
		   
		   jodTypeId = Integer.parseInt(jobOrderTypeId);	
	   }
	   
	   
	   try
	   {
	   		// int jodTypeId = Integer.parseInt(jobOrderTypeId);	// this should be catch properly so it will not throw an exception
	   				
	        List<JobOrderDefinitionBean> jodList = jobOrderDefinitionService.getJobOrderDefinitionList(jodTypeId);
	        return getMapJobOrderDefinition(jodList);
	        
	   }
	   catch (Exception e)
	   {
		   logger.error("Error encountered while retrieving job order definition from database: " + e.getMessage(), e);
		   return getModelMapError("Error encountered while retrieving job order definition from database.");
	   }
   }
   
    
   /**
    * Returns a map object for the JobOrderDefinition
    * 
    * @return modelMap
    * */
   private Map<String, Object> getMapJobOrderDefinition(List<JobOrderDefinitionBean> jodList) {

    	Map<String, Object> modelMap = new HashMap<String, Object>(3);

    	if (jodList != null && jodList.size() > 0)
    	{
    		  modelMap.put("total", jodList.size());
    	}
      
        modelMap.put("data", jodList);
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
    * @param jobOderDefinitionService the jobOderDefinitionService to set
    */
   @Autowired
   public void setJobOderDefinitionService(JobOrderDefinitionService jobOrderDefinitionService) {
       this.jobOrderDefinitionService = jobOrderDefinitionService;
   }
  

}

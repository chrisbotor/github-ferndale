/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.RequestorBean;
import com.homeportal.service.UserService;


/**
 *
 * @author Peter
 */
@Controller
public class AdminRequestorController 
{
	private static Logger logger = Logger.getLogger(AdminRequestorController.class);
	
    private UserService userService; 
    
    
    @RequestMapping(value="/admin/getRequestors")
    public @ResponseBody
    Map<String, ? extends Object> loadRequestor() throws Exception {
            List<RequestorBean> list = userService.getRequestors();
            return getMap(list);
    }
    
   
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param House
     * @return
     */
    private Map<String, Object> getMap(List<RequestorBean> requestors) 
    	throws SQLException
    {
    	try
    	{
    		 Map<String, Object> modelMap = new HashMap<String, Object>(3);

    	        modelMap.put("total", requestors.size());
    	        modelMap.put("data", requestors);
    	        modelMap.put("success", true);
    	        System.out.println(modelMap);
    	        return modelMap;
    	
    	} 
    	catch (Exception e) 
    	{
    		return getModelMapError("Error trying to get requestors for Admin create request function.");
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

    /**
     * @param userService the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    
}

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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.model.User;
import com.homeportal.service.UserService;

/**
 *
 * @author racs
 */
@Controller
public class UserController
{
	private static Logger logger = Logger.getLogger(UserController.class);
	
    private UserService userService;

    @RequestMapping(value = "/user/get-all-user.action")
    public @ResponseBody
    Map<String, ? extends Object> getAllUser() throws Exception 
    {
        try {
            List<User> users = userService.getAllUser();
            System.out.println("Got this number of users: " + users.size());
            return getMap(users);
        } catch (Exception e) {
            return getModelMapError("Error retrieving Users from database.");
        }
    }

    
    @RequestMapping(value = "/user/deactivate-user.action")
    public @ResponseBody
    // Map<String, ? extends Object> deactivateUser(@RequestParam("id") String userId, @RequestParam("userName") String userName) 
    Map<String, ? extends Object> deactivateUser(HttpServletRequest request)
    		throws Exception 
    {
    	logger.info("De-activating user " + request.getParameter("userName"));
    	
    	final String userId = request.getParameter("userId");
    	final String userName = request.getParameter("userName");
    /*	final String password = request.getParameter("password");
    	final String roleId = request.getParameter("roleId");
    	// final String status = request.getParameter("status");
    	final String createdAt = request.getParameter("createdAt");
    	final String updatedAt = request.getParameter("updatedAt");*/
    	
        try 
        {
        	List<User> users = new ArrayList<User>();
        	
        	logger.info("**** LA-LALALA");
        	
        	User user = new User();
        	
        	/*logger.info("**** BEFORE de activating");
        	
        	if (userId != null)
        	{
        		user.setId(Integer.parseInt(userId));
        	}
        	else
        	{
        		logger.info("userId is NULL.");
        	}
        	
        	
        	user.setUsername(userName);
        	user.setPassword(password);
        	user.setRole_Id(Integer.parseInt(roleId));
        	
        	// logger.info("**** BEFORE de activating");
        	user.setStatus("I");
        	
        	user.setCreatedAt(null); // TODO fix this!!
        	user.setUpdatedAt(null); // TODO fix this!!
        	
        	// logger.info("**** before de activating");
*/        	
        	
        	
        	userService.deactivateUser(Integer.parseInt(userId), userName);
            // System.out.println("Got this number of users: " + users.size());
            
            users.add(user);
            return getMap(users);
        } catch (Exception e) 
        {
            return getModelMapError("Error de-activating user.");
        }
    }
    
    

    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param owner
     * @return
     */
    private Map<String, Object> getMap(List<User> users) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", users.size());
        modelMap.put("data", users);
        modelMap.put("success", true);

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
     * @param userService the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    
}

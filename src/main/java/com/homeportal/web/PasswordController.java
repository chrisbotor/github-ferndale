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

import com.homeportal.model.User;
import com.homeportal.service.UserService;
import com.homeportal.util.PasswordUtil;

/**
 *
 * @author racs
 */
@Controller
public class PasswordController 
{
	private static Logger logger = Logger.getLogger(PasswordController.class);

	private UserService userService;
	
	
	/**
	 * Displays the search box where admin can specify the name of the user/home owner who needs password reset
	 * 
	 * */
    @RequestMapping("/admin-search-user-to-reset-password")
    String getUserSearch(HttpSession session) throws Exception 
    {
    	logger.info("******* Getting the search box to search for user/home owners...");
    	System.out.println("******* Getting the search box to search for user/home owners...");
    	
    	return "admin-search-user-to-reset-password";
    }
	
	
    
    /**
	 * Resets the password for the user/home owner
	 * 
	 * */
    @RequestMapping(value = "/admin-reset-user-password.action")
    public @ResponseBody
    Map<String, ? extends Object> deactivateUser(HttpServletRequest request)
    		throws Exception 
    {
    	final String userId = request.getParameter("userId");
    	final String userName = request.getParameter("userName");
    	
    	logger.info("******* Resetting password for user " + userName);
    	logger.info("userId: " + userId);
    	
    	System.out.println("******* Resetting password for user " + userName);
    	System.out.println("userId: " + userId);
    	
    	try 
        {
        	// reset the password
        	String newPassword = PasswordUtil.generatePassword();
        	logger.info("New Password: " + newPassword);
        	System.out.println("New Password: " + newPassword);
        	
        	// get the User
        	User user = userService.getUserByUserId(Integer.parseInt(userId));
        	user.setPassword(newPassword);
        	user.setUpdatedAt(new Date());
        	
        	boolean updated = userService.updateUser(user);
        	
        	Map<String, Object> map = new HashMap<String, Object>();
     		
        	if (updated)
     		{
     			map.put("success",Boolean.TRUE);
     			map.put("data", user.getPassword());
     		}
     		else
     		{
     			map.put("success",Boolean.FALSE);
     		}
     		
     		return map;
        } 
        catch (Exception e) 
        {
            return getModelMapError("Error de-activating user.");
        }
	
	
    }
	

    
    /**
     * Used to change password of a user/admin
     * */
    @RequestMapping("/change-password.action")
    String view() throws Exception {
        System.out.println("went to  Change Password view...");
        return "change-password";
    }

    
    /**
     * Used to update password in the database
     * */
    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> validate(@RequestParam("password1") String newPassword, HttpSession session) throws Exception
    {
    	String username = (String) session.getAttribute("username");   	 
    	
    	logger.info("******* Updating password for user " + username);
    	logger.info("New Password: " + newPassword);
    	System.out.println("******* Updating password for user: " + username);
    	System.out.println("New Password: " + newPassword);
    	
        try 
        {
        	// get user by username
            User user = userService.getUserByUsername(username);
            user.setPassword(newPassword);
            user.setUpdatedAt(new Date());
            
            
            boolean updated = userService.updateUser(user);
        	
        	Map<String, Object> map = new HashMap<String, Object>();
     		
        	if (updated)
     		{
        		map.put("data", user);
        		map.put("success", Boolean.TRUE);
            }
     		else
     		{
     			map.put("success",Boolean.FALSE);
     		}
     		
     		return map;

        } 
        catch (Exception ex)
        {
        	return getModelMapError("Error retrieving user from database." + ex.getMessage());
        }
    }

    
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param User
     * @return
     */
    private Map<String, Object> getMap(User user) {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("data", user);
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
     * @param UserService the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

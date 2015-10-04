/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.LesseeBean;
import com.homeportal.model.Lessee;
import com.homeportal.model.Owner;
import com.homeportal.service.LesseeService;
import com.homeportal.util.ValidationUtil;

/**
 *
 * @author Racs
 */
@Controller
public class LesseeController
{
	private static Logger logger = Logger.getLogger(LesseeController.class);
	private LesseeService lesseeService;
	

     @RequestMapping(value = "/leesee/view.action")
    public @ResponseBody
    Map<String, ? extends Object> view() throws Exception {
        try {
        	List<LesseeBean> leesee = lesseeService.getLeeseeList();
            System.out.println("leesee list is : " + leesee.size());
            return getMapBean(leesee);
        } catch (Exception e) {
            return getModelMapError("Error retrieving leesee from database.");
        }
    }

    
     
     @RequestMapping(value = "/leesee/create.action")
     public @ResponseBody
     Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<Lessee> leesees = lesseeService.create(data);
            return getMap(leesees);
        } catch (Exception e) {
            return getModelMapError("Error trying to create leesee.");
        }
    }
     
     

    @RequestMapping(value = "/leesee/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        try {
            List<Lessee> leesees = lesseeService.update(data);
            return getMap(leesees);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/leesee/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
        	lesseeService.delete(data);
            Map<String, Object> modelMap = new HashMap<String, Object>(3);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            return getModelMapError("Error trying to delete contact.");
        }
    }

    
    
    
    
    /**
     * Updates details for a specific lessee. This is used during update after Search for lessee
     * */
    @RequestMapping(value="/lessee/update-lessee.action")
    public @ResponseBody
    Map<String, ? extends Object> updateLessee(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("went to view of updateLessee() method...");
    	logger.info("went to view of updateLessee() method...");
    	
    	String userIdParam = request.getParameter("lesseeUserId");
    	String houseIdParam = request.getParameter("lesseeHouseId");
    	logger.info("CHRIS userId: " + userIdParam);
    	logger.info("houseId: " + houseIdParam);
    	
    	int userId = 0;
    	int houseId = 0;
    	
    	if (ValidationUtil.hasValue(userIdParam))
    	{
    		userId = Integer.parseInt(userIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(houseIdParam))
    	{
    		houseId = Integer.parseInt(houseIdParam);
    	}
    	
    	
    	Lessee lessee = lesseeService.getLesseeByUserIdHouseId(userId, houseId);
    	
    	if (lessee != null)
    	{
    		logger.info("LESSEE NOT NULL! "  + lessee.getUserId());
    		
    		lessee.setMiddlename(request.getParameter("lesseeMiddleName"));
    		
    		lessee.setCivilStatus(request.getParameter("lesseeCivilStatus"));
    		lessee.setMobileNumber(request.getParameter("lesseeMobileNumber"));
    		lessee.setHomeNumber(request.getParameter("lesseeHomeNumber"));
    		lessee.setEmailAddress(request.getParameter("lesseeEmailAddress"));
    		lessee.setWorkName(request.getParameter("lesseeWorkName"));
    		lessee.setWorkAddress(request.getParameter("lesseeWorkAddress"));
    		lessee.setWorkLandline(request.getParameter("lesseeWorkLandline"));
    		lessee.setWorkMobile(request.getParameter("lesseeWorkMobile"));
    		lessee.setWorkEmail(request.getParameter("lesseeWorkEmail"));
    		lessee.setBirthdate(request.getParameter("lesseeBirthDate"));
    		lessee.setUpdatedAt(new Date());
    		
    		lesseeService.updateLessee(lessee);
    	}
    	
    	
    	Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", 1);
        modelMap.put("data", lessee);
        modelMap.put("success", true);
        
        System.out.println("total " + 1);
        System.out.println("data " + lessee);

        return modelMap;
    }
    
    
    
    /**
     * Gets all the leesee from the database
     * 
     * */
    @RequestMapping(value="/lessee/get-all-lessee.action")
    public @ResponseBody
    Map<String, ? extends Object> getAllLessee() throws Exception
    {
    	logger.info("Getting all lessee from the database...");
    	System.out.println("Getting all lessee from the database...");
    	
    	List<Lessee> lesseeList = null;
    	
    	try
    	{
    		lesseeList = lesseeService.getAllLessee();
    		
    		System.out.println("Lessee size: " + lesseeList.size());
    		logger.info("RACS Lessee size: " + lesseeList.size());
    		
    		return getMap(lesseeList);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return getModelMapError("Error getting all lessee from the database.");
    	}
    }
    
    
    
    
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param leesee
     * @return
     */
    private Map<String, Object> getMap(List<Lessee> leesee) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        System.out.println("total " + leesee.size());
        System.out.println("data " + leesee);
        modelMap.put("total", leesee.size());
        modelMap.put("data", leesee);
        modelMap.put("success", true);

        return modelMap;
    }
    
    
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param leesee
     * @return
     */
    private Map<String, Object> getMapBean(List<LesseeBean> leesee) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        System.out.println("total " + leesee.size());
        System.out.println("data " + leesee);
        modelMap.put("total", leesee.size());
        modelMap.put("data", leesee);
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
     * @param lesseeService the lesseeService to set
     */
    @Autowired
    public void setLesseeService(LesseeService lesseeService) {
        this.lesseeService = lesseeService;
    }
}

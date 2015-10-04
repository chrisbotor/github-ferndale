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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.OwnerBean;
import com.homeportal.model.Owner;
import com.homeportal.service.OwnerService;

/**
 *
 * @author Peter
 */
@Controller
public class OwnerController
{
	private static Logger logger = Logger.getLogger(OwnerController.class);

    private OwnerService ownerService;

    
    @RequestMapping(value = "/owner/view.action")
    public @ResponseBody
    Map<String, ? extends Object> view() throws Exception {
        try {
            List<Owner> owner = ownerService.getOwnersList();
            System.out.println("owner list is : " + owner.size());
            return getMap(owner);
        } catch (Exception e) {
            return getModelMapError("Error retrieving Owner from database.");
        }
    }

     @RequestMapping(value = "/owner/fullname/view.action")
    public @ResponseBody
    Map<String, ? extends Object> getFullName() throws Exception {
        try {
            List<OwnerBean> owner = ownerService.getFullName();
            System.out.println("owner list is : " + owner.size());
            return getMapOwnerBean(owner);
        } catch (Exception e) {
            return getModelMapError("Error retrieving Owner from database.");
        }
    }

    @RequestMapping(value = "/owner/create.action")
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<Owner> owners = ownerService.create(data);
            return getMap(owners);
        } catch (Exception e) {
            return getModelMapError("Error trying to create contact.");
        }
    }

    @RequestMapping(value = "/owner/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        try {
            System.out.println("went to update of owner");
            List<Owner> owners = ownerService.update(data);
            return getMap(owners);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    
    @RequestMapping(value = "/owner/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
            ownerService.delete(data);
            Map<String, Object> modelMap = new HashMap<String, Object>(3);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            return getModelMapError("Error trying to delete owner.");
        }
    }

    
    
    /**
     * Gets all the owners from the database
     * */
    @RequestMapping(value="/owner/get-all-owner.action")
    public @ResponseBody
    Map<String, ? extends Object> getAllOwner() throws Exception
    {
    	logger.info("Getting all owner from the database...");
    	System.out.println("Getting all owner from the database...");
    	
    	List<Owner> ownerList = null;
    	
    	try
    	{
    		ownerList = ownerService.getAllOwner();
    		return getMap(ownerList);
    	}
    	catch(Exception e)
    	{
    		return getModelMapError("Error getting all owner from the database.");
    	}
    	
    }
    
    
    
    /**
     * Updates details for a specific owner
     * */
    @RequestMapping(value="/owner/update-owner.action")
    public @ResponseBody
    Map<String, ? extends Object> updateSingleOwner(HttpServletRequest request) throws Exception {
    	System.out.println("went to view of updateOwner() method...");
    	
    	List<Owner> list = new ArrayList<Owner>();
    	Owner owner = new Owner();
    	
    	if (request != null)
    	{
    		owner.setId(Integer.parseInt(request.getParameter("ownerId")));
    		owner.setUserId(Integer.parseInt(request.getParameter("ownerUserId")));
    		// owner.setFirstname(request.getParameter("ownerFirstName"));
    		// owner.setMiddlename((request.getParameter("ownerMiddleName")));
    		// owner.setLastname((request.getParameter("ownerLastName")));
    		
    		owner.setCivilStatus((request.getParameter("ownerCivilStatus")));
    		owner.setMobileNumber((request.getParameter("ownerMobileNumber")));
    		owner.setHomeNumber((request.getParameter("ownerHomeNumber")));
    		owner.setEmailAddress((request.getParameter("ownerEmailAddress")));
    		owner.setWorkName((request.getParameter("ownerWorkName")));
    		owner.setWorkAddress((request.getParameter("ownerWorkAddress")));
    		owner.setWorkLandline((request.getParameter("ownerWorkLandline")));
    		owner.setWorkMobile((request.getParameter("ownerWorkMobile")));
    		owner.setWorkEmail((request.getParameter("ownerWorkEmail")));
    		owner.setBirthdate((request.getParameter("ownerBirthDate")));
    		
    		ownerService.updateSingleOwner(owner);
    	}
    	
        return getMap(list);
    }
   
    
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param owner
     * @return
     */
    private Map<String, Object> getMap(List<Owner> owner) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", owner.size());
        modelMap.put("data", owner);
        modelMap.put("success", true);

        return modelMap;
    }

    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param owner
     * @return
     */
    private Map<String, Object> getMapOwnerBean(List<OwnerBean> owner) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", owner.size());
        modelMap.put("data", owner);
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
     * @param ownerService the ownerService to set
     */
    @Autowired
    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    
}

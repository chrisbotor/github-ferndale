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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.HouseBean;
import com.homeportal.model.House;
import com.homeportal.service.HouseService;
import com.homeportal.util.ValidationUtil;

/**
 *
 * @author RACS
 */
@Controller
public class HouseController
{
	private static Logger logger = Logger.getLogger(HouseController.class);
	
    private HouseService houseService;
    
    
    /**
     * Gets all House object from the database
     * */
    @RequestMapping(value="/house/get-all-house.action")
    public @ResponseBody
    Map<String, ? extends Object> getAllHouse(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	System.out.println("======= RACS Getting all house from the database...");
    	logger.info("Getting all house from the database...");
    	
    	List<House> list = null;
    	List<HouseBean> houseBeanList = new ArrayList<HouseBean>();
    	
    	String ownerIdParam = request.getParameter("ownerId");
    	String lesseeHouseIdParam = request.getParameter("lesseeId");
    	
    	System.out.println("OWNER ID: " + ownerIdParam);
    	System.out.println("LESSEE HOUSE ID: " + lesseeHouseIdParam);
    	
    	logger.info("OWNER ID: " + ownerIdParam);
    	logger.info("LESSEE HOUSE ID: " + lesseeHouseIdParam);
    	
 	   	int ownerId = 0;
 	   	int lesseeHouseId = 0;
 	   	
 	   	
 	   	if (ValidationUtil.hasValue(ownerIdParam))
 	   	{
 	   		ownerId = Integer.parseInt(ownerIdParam);
 	   		list = houseService.getHouseListByOwnerId(ownerId);
 	   		
 	   		Map<String, Object> modelMap = new HashMap<String, Object>(3);
 	   		
 	   		if(list != null && list.size() > 0)
 	   		{
 	   			for (House house: list)
 	   			{
 	   				if (house != null)
 	   				{
 	   					HouseBean houseBean = new HouseBean();
 	   					houseBean.setHouseId(house.getId());
 	   					houseBean.setOwnerId(house.getOwnerId());
 	   				
 	   					houseBean.setAddress(house.getAddressNumber() + " " + house.getAddressStreet());
 	   					houseBean.setAddressNumber(house.getAddressNumber());
 	   					houseBean.setAddressSt(house.getAddressStreet());
 	   					houseBean.setType(house.getType());
		 	   			houseBean.setPhase(house.getPhase());
			 	   		houseBean.setTitle(house.getTitle());
			 	   		houseBean.setRented(house.getRented());
			 	   		//houseBean.setLotArea(house.getLotArea());
	 	   					
 	   					houseBeanList.add(houseBean);
 	   				}
 	   			}
 	   			
	 	   		modelMap.put("total", houseBeanList.size());
	 	        modelMap.put("data", houseBeanList);
	 	        modelMap.put("success", true);
 	   		}
 	   		
 	   		return modelMap;
 	   	}
 	   	else if (ValidationUtil.hasValue(lesseeHouseIdParam))
 	   	{
 	   		House lesseeHouse = null;
 	   		lesseeHouseId = Integer.parseInt(lesseeHouseIdParam);
 	   		lesseeHouse = houseService.getHouseByHouseId(lesseeHouseId); // get house by house id
 	   		
 	   		Map<String, Object> modelMap = new HashMap<String, Object>(3);
 	   		
 	   		if (lesseeHouse != null)
			{
				HouseBean houseBean = new HouseBean();
				houseBean.setHouseId(lesseeHouse.getId());
				houseBean.setOwnerId(lesseeHouse.getOwnerId());
			
				houseBean.setAddress(lesseeHouse.getAddressNumber() + " " + lesseeHouse.getAddressStreet());
				houseBean.setAddressNumber(lesseeHouse.getAddressNumber());
				houseBean.setAddressSt(lesseeHouse.getAddressStreet());
				houseBean.setType(lesseeHouse.getType());
	   			houseBean.setPhase(lesseeHouse.getPhase());
	   			houseBean.setTitle(lesseeHouse.getTitle());
	   			houseBean.setRented(lesseeHouse.getRented());
	   			//houseBean.setLotArea(house.getLotArea());
					
				houseBeanList.add(houseBean);
			}
 	   	
 	   			modelMap.put("total", houseBeanList.size());
 	   			modelMap.put("data", houseBeanList);
 	   			modelMap.put("success", true);
 	   		
 	   		return modelMap;
 	   	}
 	   	else
 	   	{
 	   		list = houseService.getAllHouse();
 	   		return getMap(list);
 	   	}
 	}
    
    

    @RequestMapping(value = "/house/view.action")
    public @ResponseBody
    Map<String, ? extends Object> view() throws Exception {
        try {
            System.out.println("went to view of Houses");
            List<HouseBean> House = houseService.getHousesList();
            System.out.println("house list is : " + House.size());
            return getMapBean(House);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }

    @RequestMapping(value = "/house/create.action")
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<House> houses = houseService.create(data);
            return getMap(houses);
        } catch (Exception e) {
            return getModelMapError("Error trying to create house.");
        }
    }

    @RequestMapping(value = "/house/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        try {
            List<House> houses = houseService.update(data);
            return getMap(houses);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/house/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
            houseService.delete(data);
            Map<String, Object> modelMap = new HashMap<String, Object>(3);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            return getModelMapError("Error trying to delete contact.");
        }
    }

    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param House
     * @return
     */
    private Map<String, Object> getMap(List<House> house) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        // System.out.println("total " + house.size());
        // System.out.println("data " + house);
        modelMap.put("total", house.size());
        modelMap.put("data", house);
        modelMap.put("success", true);

        return modelMap;
    }
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param House
     * @return
     */
    private Map<String, Object> getMapBean(List<HouseBean> house) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        System.out.println("total " + house.size());
        System.out.println("data " + house);
        modelMap.put("total", house.size());
        modelMap.put("data", house);
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
     * @param houseService the houseService to set
     */
    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    
}

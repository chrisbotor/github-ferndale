/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.Amenity;
import com.homeportal.service.AmenityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Peter
 */
@Controller
public class AmenitiesController {
    
    private AmenityService amenityService;
    
    @RequestMapping(value = "/amenity/load.action")
    public @ResponseBody
    Map<String, ? extends Object> loadAmenities() throws Exception {
        try {
            System.out.println("loading amenities - AmenitiesController...");
            List<Amenity> ar = amenityService.getAmenityList();
            return getMap(ar);
        } catch (Exception e) {
            return getModelMapError("Error retrieving amenities from database.");
        }
    }
    
    @RequestMapping(value = "/amenity/create.action")
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<Amenity> v = amenityService.create(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to create house.");
        }
    }

    @RequestMapping(value = "/amenity/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        System.out.println("data : " + data);
        try {
            List<Amenity> v = amenityService.update(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/amenity/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
            amenityService.delete(data);
            Map<String, Object> modelMap = new HashMap<String, Object>(3);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            return getModelMapError("Error trying to delete contact.");
        }
    }
    
    private Map<String, Object> getMap(List<Amenity> amenity) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", amenity.size());
        modelMap.put("data", amenity);
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
     * @param amenityService the amenityService to set
     */
    @Autowired
    public void setAmenityService(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    
    
}

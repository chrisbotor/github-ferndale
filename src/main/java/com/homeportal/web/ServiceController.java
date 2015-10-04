/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.Services;
import com.homeportal.service.ServiceService;
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
public class ServiceController {

    private ServiceService servicesService;

    @RequestMapping(value = "/services/load.action")
    public @ResponseBody
    Map<String, ? extends Object> loadAmenities() throws Exception {
        try {
            System.out.println("loading amenities - AmenitiesController...");
            List<Services> ar = servicesService.getServicesList();
            return getMap(ar);
        } catch (Exception e) {
            return getModelMapError("Error retrieving amenities from database.");
        }
    }

    @RequestMapping(value = "/services/create.action")
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<Services> v = servicesService.create(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to create house.");
        }
    }

    @RequestMapping(value = "/services/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        try {
            List<Services> v = servicesService.update(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/services/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
            servicesService.delete(data);
            Map<String, Object> modelMap = new HashMap<String, Object>(3);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            return getModelMapError("Error trying to delete contact.");
        }
    }

    private Map<String, Object> getMap(List<Services> services) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", services.size());
        modelMap.put("data", services);
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
     * @param servicesService the servicesService to set
     */
    @Autowired
    public void setServiceService(ServiceService servicesService) {
        this.servicesService = servicesService;
    }
}

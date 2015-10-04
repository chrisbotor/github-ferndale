/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.Occupant;
import com.homeportal.service.OccupantService;
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
public class OccupantController {
    
    private OccupantService occupantService;
    
    @RequestMapping(value = "/occupant/view.action")
    public @ResponseBody
    Map<String, ? extends Object> view() throws Exception {
        try {
            
            
            List<Occupant> v = occupantService.getOccupantsList();
            System.out.println("Occupant list is : " + v.size());
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }

    @RequestMapping(value = "/occupant/create.action")
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<Occupant> v = occupantService.create(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to create house.");
        }
    }

    @RequestMapping(value = "/occupant/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        try {
            List<Occupant> v = occupantService.update(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/occupant/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
            occupantService.delete(data);
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
    private Map<String, Object> getMap(List<Occupant> v) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        System.out.println("total " + v.size());
        System.out.println("data " + v);
        modelMap.put("total", v.size());
        modelMap.put("data", v);
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
     * @param occupantService the occupantService to set
     */
    @Autowired
    public void setOccupantService(OccupantService occupantService) {
        this.occupantService = occupantService;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.bean.VehicleBean;
import com.homeportal.model.Vehicle;
import com.homeportal.service.VehicleService;
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
public class VehicleController {
    
    private VehicleService vehicleService;
    
    //@RequestMapping(value = "/vehicle/view.action")
    //public @ResponseBody
    //Map<String, ? extends Object> view() throws Exception {
        //try {
            //List<VehicleBean> v = vehicleService.getVehiclesList();
            //System.out.println("Vehicle list is : " + v.size());
            //return getMapBean(v);
        //} catch (Exception e) {
            //return getModelMapError("Error retrieving vehicle from database.");
        //}
    //}

    @RequestMapping(value = "/vehicle/create.action")
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<Vehicle> v = vehicleService.create(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to create house.");
        }
    }

    @RequestMapping(value = "/vehicle/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        try {
            System.out.println("--inside update");
            List<Vehicle> v = vehicleService.update(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/vehicle/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
            vehicleService.delete(data);
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
    private Map<String, Object> getMap(List<Vehicle> v) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        System.out.println("total " + v.size());
        System.out.println("data " + v);
        modelMap.put("total", v.size());
        modelMap.put("data", v);
        modelMap.put("success", true);

        return modelMap;
    }
    
    private Map<String, Object> getMapBean(List<VehicleBean> v) {

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
     * @param vehicleService the vehicleService to set
     */
    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    
    
    
}

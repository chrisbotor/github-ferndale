/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.Employee;
import com.homeportal.service.EmployeeService;
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
public class EmployeeController {

    private EmployeeService employeeService;

    

    @RequestMapping(value = "/employee/create.action")
    public @ResponseBody
    Map<String, ? extends Object> create(@RequestParam Object data) throws Exception {
        try {
            List<Employee> v = employeeService.create(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to create house.");
        }
    }

    @RequestMapping(value = "/employee/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        try {
            List<Employee> v = employeeService.update(data);
            return getMap(v);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/employee/delete.action")
    public @ResponseBody
    Map<String, ? extends Object> delete(@RequestParam Object data) throws Exception {
        try {
            employeeService.delete(data);
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
    private Map<String, Object> getMap(List<Employee> v) {

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
     * @param employeeService the employeeService to set
     */
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}

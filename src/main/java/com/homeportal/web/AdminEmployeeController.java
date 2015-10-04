/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.bean.EmployeeBean;
import com.homeportal.model.Employee;
import com.homeportal.service.EmployeeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class AdminEmployeeController {
    
    private EmployeeService employeeService;
    
    @RequestMapping("/admin-employeeRequest")
    String view(HttpServletRequest req, HttpSession session) throws Exception {
         System.out.println("went to view of AdminEmployeeController");
          try {
            System.out.println("went to try ... ");
          int lenght = session.getAttribute("reqId").toString().length();  
        } catch (NullPointerException e) {
          if(req.getParameter("id").length() == 0){
          session.setAttribute("reqId", req.getParameter("id").length());
          }else{
          session.setAttribute("reqId", req.getParameter("id"));
          }
          
          session.setAttribute("reqStatus", req.getParameter("stat"));
        }
        return "admin-employeeRequest";
    }
    
    @RequestMapping(value = "/admin/employee/view.action")
    public @ResponseBody
    Map<String, ? extends Object> viewEmployeeRequest(HttpSession session) throws Exception {
        try {
            System.out.println("went to viewEmployeeRequest...");
            int userId = Integer.parseInt(session.getAttribute("reqId").toString());
            String status = session.getAttribute("reqStatus").toString();
            List<EmployeeBean> v = employeeService.getEmployeesList(userId, status);
            return getEmployeeBean(v);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }
    
    @RequestMapping(value = "/admin/employee/update.action")
    public @ResponseBody
    Map<String, ? extends Object> updateEmployeeViaAdmin(EmployeeBean bean, HttpSession session) throws Exception {
        try {
            List<Employee> v = employeeService.update(bean);
            int userId = Integer.parseInt(session.getAttribute("reqId").toString());
            String status = session.getAttribute("reqStatus").toString();
            List<EmployeeBean> vv = employeeService.getEmployeesList(userId, status);
            return getEmployeeBean(vv);
        } catch (Exception e) {
            return getModelMapError("Error trying to update amenity request." + e.toString());
        }
    }
    
    private Map<String, Object> getEmployeeBean(List<EmployeeBean> v) {

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

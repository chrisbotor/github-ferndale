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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Peter
 */
@Controller
public class AdminVehicleController {
    
    private VehicleService vehicleService;
    
     @RequestMapping("/admin-vehicleRequest")
    String view(HttpServletRequest req, HttpSession session) throws Exception {
         System.out.println("went to view of AdminVehicleController");
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
        return "admin-vehicleRequest";
    }
     
    @RequestMapping(value = "/admin/vehicle/view.action")
    public @ResponseBody
    Map<String, ? extends Object> viewRequestedVehicleSticker(HttpSession session) throws Exception {
        System.out.println("went to viewRequestedVehicleSticker......");
        try {
            int userId = Integer.parseInt(session.getAttribute("reqId").toString());
            String status = session.getAttribute("reqStatus").toString();
            List<VehicleBean> v = vehicleService.getVehiclesList(userId, status);
            return getMapBean(v);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }
    
    @RequestMapping(value = "/admin/vehicle/update.action",method=RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> updateVehicleViaAdmin(VehicleBean bean, HttpSession session) throws Exception {
        System.out.println("went to updateVehicleViaAdmin......");
        try {
            List<Vehicle> v = vehicleService.update(bean);
            int userId = Integer.parseInt(session.getAttribute("reqId").toString());
            String status = session.getAttribute("reqStatus").toString();
            List<VehicleBean> vv = vehicleService.getVehiclesList(userId, status);
            return getMapBean(vv);
        } catch (Exception e) {
            return getModelMapError("Error trying to update vehicle request." + e.toString());
        }
    }

    /**
     * @param vehicleService the vehicleService to set
     */
    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
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
}

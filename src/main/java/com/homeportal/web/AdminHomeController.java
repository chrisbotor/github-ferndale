/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.EmployeeBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.bean.VehicleBean;
import com.homeportal.service.AmenitiesRequestService;
import com.homeportal.service.EmployeeService;
import com.homeportal.service.ServiceRequestService;
import com.homeportal.service.VehicleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Peter
 */
@Controller
public class AdminHomeController {
  
    private ServiceRequestService requestService;
    private AmenitiesRequestService  amenitiesRequestService;
    private VehicleService vehicleService;
    private EmployeeService employeeService;

    @RequestMapping("/admin-home")
    String view() throws Exception {
         System.out.println("went to view of AdminHomeController");
        return "admin-home";
    }

    @RequestMapping(value = "/admin/homeservices.action")
    public @ResponseBody
    Map<String, ? extends Object> viewServicesRequest(HttpSession session) throws Exception {
        try {
            System.out.println("went to  viewServicesRequest - AdminHomeController");
            List<ServiceBean> sr = requestService.getServiceRequestList();
            
            System.out.println("viewServicesRequest : " + sr.size());
            return getMapSR(sr);
        } catch (Exception e) {
            return getModelMapError("Error services from database.");  // RACS ERROR HERE
        }
    }

   
    
   /* @RequestMapping(value = "/admin/homeamenities.action")
    public @ResponseBody
    Map<String, ? extends Object> viewAmenitiesRequest() throws Exception {
        try {
            List<AmenityBean> ar = amenitiesRequestService.getAmenityList();
            return getMapAR(ar);
        } catch (Exception e) {
            return getModelMapError("Error retrieving amenities from database.");
        }
    }*/
   
    
    @RequestMapping(value = "/admin/homevehicle")
    public @ResponseBody
    Map<String, ? extends Object> viewRequestedVehicleSticker() throws Exception {
        try {
            List<VehicleBean> v = vehicleService.getVehiclesList();
            return getMapBean(v);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }
    /**
    @RequestMapping(value = "/admin/vehicle/update.action")
    public @ResponseBody
    Map<String, ? extends Object> updateVehicleViaAdmin(@RequestParam Object data) throws Exception {
        try {
            List<Vehicle> v = vehicleService.update(data);
            List<VehicleBean> vv = vehicleService.getVehiclesList();
            return getMapBean(vv);
        } catch (Exception e) {
            return getModelMapError("Error trying to update amenity request." + e.toString());
        }
    }*/
    
    @RequestMapping(value = "/admin/homeemployee")
    public @ResponseBody
    Map<String, ? extends Object> viewEmployeeRequest() throws Exception {
        try {
            System.out.println("went to viewEmployeeRequest...");
            List<EmployeeBean> v = employeeService.getEmployeesList();
            return getEmployeeBean(v);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }
    /**
    @RequestMapping(value = "/admin/employee/update.action")
    public @ResponseBody
    Map<String, ? extends Object> updateEmployeeViaAdmin(@RequestParam Object data) throws Exception {
        try {
            List<Employee> v = employeeService.update(data);
            List<EmployeeBean> vv = employeeService.getEmployeesList();
            return getEmployeeBean(vv);
        } catch (Exception e) {
            return getModelMapError("Error trying to update amenity request." + e.toString());
        }
    }*/
    
    private Map<String, Object> getEmployeeBean(List<EmployeeBean> v) {

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
    
    private Map<String, Object> getMapAR(List<AmenityBean> amenitys) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", amenitys.size());
        modelMap.put("data", amenitys);
        modelMap.put("success", true);
        System.out.println(modelMap);
        return modelMap;
    }

    private Map<String, Object> getMapSR(List<ServiceBean> sr) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", sr.size());
        modelMap.put("data", sr);
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
     * @param requestService the requestService to set
     */
    @Autowired
    public void setRequestService(ServiceRequestService requestService) {
        this.requestService = requestService;
    }
    

    /**
     * @param amenitiesRequestService the amenitiesRequestService to set
     */
    @Autowired
    public void setAmenitiesRequestService(AmenitiesRequestService amenitiesRequestService) {
        this.amenitiesRequestService = amenitiesRequestService;
    }

    /**
     * @param vehicleService the vehicleService to set
     */
    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * @param employeeService the employeeService to set
     */
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}

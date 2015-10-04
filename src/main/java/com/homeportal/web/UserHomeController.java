/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.model.Adjustment;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.Employee;
import com.homeportal.model.ServiceRequest;
import com.homeportal.model.Vehicle;
import com.homeportal.service.AdjustmentService;
import com.homeportal.service.AmenitiesRequestService;
import com.homeportal.service.EmployeeService;
import com.homeportal.service.ServiceRequestService;
import com.homeportal.service.VehicleService;

/**
 *
 * @author PSP36488
 */
@Controller
public class UserHomeController 
{
	private static Logger logger = Logger.getLogger(UserHomeController.class);
    
    private AmenitiesRequestService  amenitiesRequestService;
    private ServiceRequestService requestService;
    private AdjustmentService adjustmentService;
    
    private VehicleService vehicleService;
    private EmployeeService employeeService;

    @RequestMapping("/user-home")
    String myPortal(HttpSession session) throws Exception {
        System.out.println("went to User Home Controller...");
        String id = session.getAttribute("userId").toString();
        System.out.println("session is : " + id);
        return "user-home";
    }

    /**
     * Gets all the amenities for the user portal
     * It displays all status except cancelled
     * 
     * */
    @RequestMapping(value = "/home/amenities.action")
    public @ResponseBody
    Map<String, ? extends Object> viewAmenitiesRequest(@RequestParam("houseId") String houseId, HttpSession session) 
    		throws Exception {
        try {
            String id = session.getAttribute("userId").toString();
            
            List<AmenityBean> ar = amenitiesRequestService.getAmenityList(Integer.parseInt(id), Integer.parseInt(houseId));
            return getMapAR(ar);
        } catch (Exception e) {
            return getModelMapError("Error retrieving amenities from database.");
        }
    }

    @RequestMapping(value = "/home/services.action")
    public @ResponseBody
    Map<String, ? extends Object> viewServicesRequest(@RequestParam("houseId") String houseId, HttpSession session) 
    		throws Exception 
    {
        try 
        {
        	String userId = session.getAttribute("userId").toString();
        	
        	logger.info("Got useId:" + userId + " houseId: " + houseId);
        	
            List<ServiceBean> sr = requestService.getServiceRequestList(Integer.parseInt(userId), Integer.parseInt(houseId));
            return getMapSR(sr);
        } catch (Exception e) {
            return getModelMapError("Error services Owner from database.");
        }
    }
    
    
    // RACS
    /**
     * Used to get the adjustments for a specific user and it's corresponding house
     * */
    @RequestMapping(value = "/home/adjustments.action")
    public @ResponseBody
    Map<String, ? extends Object> viewUserAdjustment(@RequestParam("houseId") String houseId, HttpSession session) 
    		throws Exception 
    {
        try 
        {
        	String userId = session.getAttribute("userId").toString();
        	
        	System.out.println("Got userId:" + userId + ", houseId: " + houseId);
        	logger.info("Got userId:" + userId + ", houseId: " + houseId);
        	
            List<Adjustment> adjustments = adjustmentService.getAdjustmentByUserIdAndHouseId(Integer.parseInt(userId), Integer.parseInt(houseId));
            return getMapAdjustments(adjustments);
        } catch (Exception e) {
            return getModelMapError("Error getting adjustments for the user.");
        }
    }

    
    
    
    @RequestMapping(value = "/home/vehicle/view.action")
    public @ResponseBody
    Map<String, ? extends Object> viewVehicleRequestViaPortal(@RequestParam("houseId") String houseId, HttpSession session) 
    		throws Exception {
        try {
            String userId = session.getAttribute("userId").toString();
            List<Vehicle> v = vehicleService.getVehiclesList(Integer.parseInt(userId), Integer.parseInt(houseId));
            return getVehicleMap(v);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }
    
    @RequestMapping(value = "/home/employee/view.action")
    public @ResponseBody
    Map<String, ? extends Object> viewEmployeeRequestViaPortal(@RequestParam("houseId") String houseId, HttpSession session) 
    		throws Exception {
        try {
            String userId = session.getAttribute("userId").toString();
            List<Employee> v = employeeService.getEmployeesList(Integer.parseInt(userId), Integer.parseInt(houseId));
            return getEmployeeMap(v);
        } catch (Exception e) {
            return getModelMapError("Error retrieving house from database.");
        }
    }

    @RequestMapping(value = "/home/update/amenities.action")
    public @ResponseBody
    Map<String, ? extends Object> updateAmenityRequestViaPortal(@RequestParam Object data, @RequestParam("houseId") String houseIdParam, 
    		HttpSession session) throws Exception {
        try {
            int id = Integer.parseInt(session.getAttribute("userId").toString());
            int houseId = Integer.parseInt(houseIdParam) ;
            
            String uom = amenitiesRequestService.getUom(data);
            List<AmenitiesRequest> v = amenitiesRequestService.updateViaPortal(data,uom);
            List<AmenityBean> ar = amenitiesRequestService.getAmenityList(id, houseId);
            return getMapAR(ar);
        } catch (Exception e) {
            return getModelMapError("Error trying to update amenity request." + e.toString());
        }
    }

    @RequestMapping(value = "/home/update/services.action")
    public @ResponseBody
    Map<String, ? extends Object> updateServiceRequestViaPortal(@RequestParam Object data, @RequestParam("houseId") String houseIdParam, 
    		HttpSession session) throws Exception {
        try {
            int id = Integer.parseInt(session.getAttribute("userId").toString());
            int houseId = Integer.parseInt(houseIdParam) ;
            
            String uom = requestService.getUom(data);
            List<ServiceRequest> v = requestService.updateViaPortal(data,uom);
            List<ServiceBean> sr = requestService.getServiceRequestList(id, houseId);
            
            return getMapSR(sr);
            
        } catch (Exception e) {
            return getModelMapError("Error trying to update service request." + e.toString());
        }
    }

    @RequestMapping(value = "/home/update/vehicle.action")
    public @ResponseBody
    Map<String, ? extends Object> updateVehicleStickerViaPortal(@RequestParam Object data, @RequestParam("houseId") String houseIdParam, 
    		HttpSession session) throws Exception {
        try {
            int id = Integer.parseInt(session.getAttribute("userId").toString());
            int houseId = Integer.parseInt(houseIdParam) ;
            
            List<Vehicle> v = vehicleService.updateViaPortal(data);
            List<Vehicle> sv = vehicleService.getVehiclesList(id, houseId);
            
            return getVehicleMap(sv);
            
        } catch (Exception e) {
            return getModelMapError("Error trying to update vehicle sticker." + e.toString());
        }
    }
    
    @RequestMapping(value = "/home/employee/update.action")
    public @ResponseBody
    Map<String, ? extends Object> updateEmployeeViaPortal(@RequestParam Object data, @RequestParam("houseId") String houseIdParam,
    		HttpSession session) throws Exception {
        try {
            Integer id = Integer.parseInt(session.getAttribute("userId").toString());
            int houseId = Integer.parseInt(houseIdParam) ;
            
            List<Employee> v = employeeService.updateViaPortal(data);
            List<Employee> sv = employeeService.getEmployeesList(id, houseId);
            
            return getEmployeeMap(sv);
            
        } catch (Exception e) {
            return getModelMapError("Error trying to update vehicle sticker." + e.toString());
        }
    }

    private Map<String, Object> getMap(List<AmenitiesRequest> amenitys) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", amenitys.size());
        modelMap.put("data", amenitys);
        modelMap.put("success", true);
        System.out.println(modelMap);
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

    private Map<String, Object> getMapAdjustments(List<Adjustment> adjustments) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", adjustments.size());
        modelMap.put("data", adjustments);
        modelMap.put("success", true);
        System.out.println(modelMap);
        return modelMap;
    }
    
    private Map<String, Object> getVehicleMap(List<Vehicle> v) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        System.out.println("total " + v.size());
        System.out.println("data " + v);
        modelMap.put("total", v.size());
        modelMap.put("data", v);
        modelMap.put("success", true);

        return modelMap;
    }
    
     private Map<String, Object> getEmployeeMap(List<Employee> v) {

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
     * @param amenitiesRequestService the amenitiesRequestService to set
     */
    @Autowired
    public void setAmenitiesRequestService(AmenitiesRequestService amenitiesRequestService) {
        this.amenitiesRequestService = amenitiesRequestService;
    }

    /**
     * @param requestService the requestService to set
     */
    @Autowired
    public void setRequestService(ServiceRequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * @param adjustmentService the adjustmentService to set
     */
    @Autowired
    public void setAdjustmentService(AdjustmentService adjustmentService) {
        this.adjustmentService = adjustmentService;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.OwnerHouseBean;
import com.homeportal.bean.RequestorBean;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.Amenity;
import com.homeportal.model.ServiceRequest;
import com.homeportal.service.AmenitiesRequestService;
import com.homeportal.service.HouseService;
import com.homeportal.service.OwnerHouseService;
import com.homeportal.service.OwnerService;
import com.homeportal.service.ServiceRequestService;
import com.homeportal.util.DateTimeUtil;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.ValidationUtil;


/**
 *
 * @author Peter
 */
@Controller
public class AdminCreateRequestController 
{
	private static Logger logger = Logger.getLogger(AdminCreateRequestController.class);
	
    private AmenitiesRequestService  amenitiesRequestService;
    private ServiceRequestService serviceRequestService;
    private OwnerService ownerService;
    private HouseService houseService;
    private OwnerHouseService ownerHouseService;
    
    
    private static final String FERNDALE_USERID = MessageBundle.getProperty("ferndale_userId");
    
    
    /**
     * Method used to display the RESERVATION REQUEST form when admin wants to create a reservation request 
     * */
    /*@RequestMapping("/admin-create-request.action")
    String viewReservationRequestForm() throws Exception
    {
    	System.out.println("\n***** Admin creating request and getting the Reservation Request form...");
    	logger.info("\n***** Admin creating request and getting the Reservation Request form...");
    	
    	return "admin-create-request";
    }*/
    
    
    
    
   /* @RequestMapping(value="/admin/getRequestor")
    public @ResponseBody
    Map<String, ? extends Object> loadRequestor() throws Exception {
            List<RequestorBean> list = userService.getRequestors();
            return getMap(list);
    }*/
    
    
    /*
     * NOTE: THIS METHOD IS USED BY THE HOME OWNER TO CREATE AMENITIES REQUEST (DIFFERENT FROM WHAT ADMIN IS USING)
     * */
    @RequestMapping(value="/admin-createRequest/amenities.action", method=RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> addAmenities(AmenitiesRequest ar, RequestorBean rb)  
    		throws Exception {
        
    	logger.info("Creating amenity request...");
    	
    	try {
            ar.setCreatedAt(new Date());
            ar.setUpdatedAt(new Date());
            
            Map<String, Object> data = new HashMap<String, Object>();
            amenitiesRequestService.create(ar);
            
            data.put("success",Boolean.TRUE);
            
            return data;
        } catch (Exception e) {
            return getModelMapError("Error trying to save the Amenity request.");
        }
    }
    
    @RequestMapping(value="/admin-createRequest/services.action", method=RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> addServices(ServiceRequest sr, RequestorBean rb)  // RequestorBean needs to be declared here, else userId and houseId will be NULL 
    		throws Exception {
    	
    	logger.info("====================== IN THE CREATE SERVICE...");
    	
        try {
        	// this code is needed to check if the requestor of the service is the Admin User
            /*if(ub.getId() == 0)
            {
                ub.setId(Integer.parseInt(FERNDALE_USERID));
            }
            sr.setUserId(ub.getId());*/
        	
            sr.setCreatedAt(new Date());
            sr.setUpdatedAt(new Date());
            
            // this code is needed to check the requestor's name
           // System.out.println("payee is : " + sr.getPayeeName());
            
            Map<String, Object> data = new HashMap<String, Object>();
            serviceRequestService.create(sr);
            
            data.put("success",Boolean.TRUE);
            
            return data;
        } catch (Exception e) {
            return getModelMapError("Error trying to save the Service request.");
        }
    }
    
    
    
    //####################################  NEW METHOD FOR THE CREATE REQUEST PAGE #################################################################
    
    @RequestMapping(value="/admin/get-owner-house.action")
    public @ResponseBody
    Map<String, ? extends Object> loadRequestor(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("Loading all the possible REQUESTOR...");
    	logger.info("Loading all the possible REQUESTOR...");
    	
    	List<OwnerHouseBean> list = ownerHouseService.getOwnerHouseList();
    	return getMap(list);
    }
    
    
    
     /**
     * Generates modelMap to be returned to the modelAndView
     *
     * @param list - List of OwnerHouseBean objects
     * @return modelMap
     */
    private Map<String, Object> getMap(List<OwnerHouseBean> list)
    {
    	if (list != null)
    	{
    		System.out.println("Got " + list.size() + " REQUESTOR.");
            logger.info("Got " + list.size() + " REQUESTOR.");
    	}
    	
    	Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", list.size());
        modelMap.put("data", list);
        modelMap.put("success", true);
        // System.out.println(modelMap);
        
        return modelMap;
    }
    
    
    
    /**
     * Generates modelMap to return in the modelAndView in case of exception
     *
     * @param msg message
     * @return modelMap
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
     * @param serviceRequestService the serviceRequestService to set
     */
    @Autowired
    public void setServiceRequestService(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }
    
    
    /**
     * @param ownerHouseService the ownerHouseService to set
     */
    @Autowired
    public void setOwnerHouseService(OwnerHouseService ownerHouseService) {
        this.ownerHouseService = ownerHouseService;
    }
    
    
    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }
    
    

    @Autowired
    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
}

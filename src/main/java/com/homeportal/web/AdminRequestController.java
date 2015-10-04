/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.web;

import java.util.ArrayList;
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

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.ServiceRequest;
import com.homeportal.service.AmenitiesRequestService;
import com.homeportal.service.SearchRequestsService;
import com.homeportal.service.ServiceRequestService;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.DateTimeUtil;
import com.homeportal.util.ValidationUtil;


/**
 * This controller manages the creation, search and retrieval and update of an Amenity and a Service request by the Admin
 * 
 * @author Racs
 */
@Controller
public class AdminRequestController 
{

	private static Logger logger = Logger.getLogger(AdminRequestController.class);
    
    private AmenitiesRequestService  amenitiesRequestService;
    private ServiceRequestService  serviceRequestService;
    private SearchRequestsService searchRequestsService;
    
    
    
    // ADMIN CREATES REQUESTS
    /**
     * Used to display the RESERVATION REQUEST Form when admin wants to create an Amenity or a Service request
     * */
    @RequestMapping("/admin-create-request.action")
    String viewReservationRequestForm() throws Exception
    {
    	System.out.println("\n***** Admin getting the Reservation Request form and creating request...");
    	logger.info("***** Admin getting the Reservation Request form and creating request...");
    	
    	return "admin-create-request";
    }
    
    
    
    /*
     * NOTE: THIS METHOD IS USED BY ADMIN IN CREATING AMENITIES REQUEST
     * */
    @RequestMapping(value = "/admin/create-request.action", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> createRequest(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("===========  ADMIN CREATING REQUEST =================");
    	logger.info("===========  ADMIN CREATING REQUEST =================");
    	
    	AmenitiesRequest ar = null;
    	ServiceRequest sr = null;
    	
    	String userIdParam = request.getParameter("requestorUserId");
    	
    	System.out.println("userIdParam: " + userIdParam);
    	logger.info("userIdParam: " + userIdParam);
    	
    	String houseIdParam = request.getParameter("houseId");
    	String amenityIdParam = request.getParameter("amenityId");
    	String serviceIdParam = request.getParameter("serviceId");
    	
    	System.out.println("****** serviceIdParam: " + serviceIdParam);
    	logger.info("****** serviceIdParam: " + serviceIdParam);
    	
    	String requestedDateParam = request.getParameter("requestedDate");	// also the same as preferred_date for Service requests
    	String startTimeParam = request.getParameter("startTime");
    	String endTimeParam = request.getParameter("endTime");
    	String quantityParam = request.getParameter("quantity");
    	String jobOrderTypeIdParam = request.getParameter("jobOrderTypeId");
    	
    	int userId = 0;
    	int houseId = 0;
    	int amenityId = 0;
    	int serviceId = 0;
    	int quantity = 0;
    	int jobOrderTypeId = 0;
    	String startTime = null;
    	String endTime = null;
    	
    	if (ValidationUtil.hasValue(userIdParam))
    	{
    		userId = Integer.parseInt(userIdParam);
    	}
    	else
    	{
    		userId = (Integer) session.getAttribute("userId");
    	}
    	
    	if (ValidationUtil.hasValue(houseIdParam))
    	{
    		houseId = Integer.parseInt(houseIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(amenityIdParam))
    	{
    		amenityId = Integer.parseInt(amenityIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(serviceIdParam))
    	{
    		serviceId = Integer.parseInt(serviceIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(quantityParam))
    	{
    		quantity = Integer.parseInt(quantityParam);
    	}
    	
    	if (ValidationUtil.hasValue(startTimeParam))
    	{
    		startTime = DateTimeUtil.formatToMilitaryTime(startTimeParam);	// RACS
    	}
    	
    	if (ValidationUtil.hasValue(endTimeParam))
    	{
    		endTime = DateTimeUtil.formatToMilitaryTime(endTimeParam);
    	}
    	
    	
    	
    	if (ValidationUtil.hasValue(jobOrderTypeIdParam))
    	{
    		jobOrderTypeId = Integer.parseInt(jobOrderTypeIdParam);
    	}
    	

    	// model map
    	System.out.println("Creating modelMap...");
    	logger.info("Creating modelMap...");
    	Map<String, Object> modelMap = new HashMap<String, Object>(3);
    	
    	if (jobOrderTypeId == ConstantsUtil.JOB_ORDER_TYPE_AMENITY_REQUEST)
    	{
    		ar = amenitiesRequestService.createAmenityRequest(userId, houseId, amenityId, requestedDateParam, startTime, endTime, quantity);
    		modelMap.put("data", ar);
    	}
    	else if(jobOrderTypeId == ConstantsUtil.JOB_ORDER_TYPE_SERVICE_REQUEST)
    	{
    		sr = serviceRequestService.createServiceRequest(userId, houseId, serviceId, requestedDateParam, quantity);
    		modelMap.put("data", sr);
    	}
    	
    	// add SUCCESS
    	modelMap.put("success", true);
	     
	    System.out.println(modelMap);
	    logger.info(modelMap);
    	 
	    return modelMap;
    }
    
    
    /**
     * Get amenity requests.
     * 
     * If the calling page is the Admin Home page, get amenity requests by STATUS (just add the desired status to the statusList 
     * example: NEW, BOOKED etc.)
     * If the calling page is the Admin Search page, call the Search service.
     * 
     * @param request - HttpServletRequest
	 * @param session - HttpSession
	 * 
	 * @return map of Object
     */
    @RequestMapping(value = "/admin/get-amenity-requests.action")
    public @ResponseBody
    Map<String, ? extends Object> getAdminAmenityRequests(HttpServletRequest request, HttpSession session) 
    		throws Exception
    {
    	String callingPage = request.getParameter("callingPage");
    	List<AmenityBean> arList = new ArrayList<AmenityBean>();
    	
    	try
    	{	//RACS
    		if (ValidationUtil.hasValue(callingPage))
            {
        		if (callingPage.equals(ConstantsUtil.SEARCH_CALLING_PAGE_HOME))
            	{
        			System.out.println("\n***** Admin HOME getting Amenity requests...");
            		System.out.println("callingPage: " + callingPage);
            		
            		logger.info("***** Admin HOME getting Amenity requests...");
            		logger.info("callingPage: " + callingPage);
            		
            		List<String> statusList = new ArrayList<String>();
            		statusList.add(ConstantsUtil.NEW);					// just add the status we wish to display in the service tab
            		// statusList.add(ConstantsUtil.CHANGE_REQUEST);	// currently disabled, this status CHANGE_REQUEST is not being used anywhere in the code
            		// statusList.add(ConstantsUtil.BOOKED);
            		
            		arList = amenitiesRequestService.getAmenityRequestsByStatus(statusList);
            		
            	}
            	else if (callingPage.equals(ConstantsUtil.SEARCH_CALLING_PAGE_SEARCH))
                {
            		System.out.println("\n***** Admin SEARCH getting Amenity requests...");
            		System.out.println("callingPage: " + callingPage);
            		
            		logger.info("***** Admin SEARCH getting Amenity requests...");
            		logger.info("callingPage: " + callingPage);
            		
            		String requestorUserId = (String) session.getAttribute("requestorUserId");
                	String requestorHouseId = (String) session.getAttribute("requestorHouseId");
                	String amenityNum = (String) session.getAttribute("amenityId");
                	String status = (String) session.getAttribute("status");
                	String fromDate = (String) session.getAttribute("fromDate");
                	String toDate = (String) session.getAttribute("toDate");
                		   
                	int userId = 0;
                	int houseId = 0;
                	int amenityId = 0;
                		   
                	if (ValidationUtil.hasValue(requestorUserId))
                	{
                		userId = Integer.parseInt(requestorUserId);
                	}
                		   
                	if (ValidationUtil.hasValue(requestorHouseId))
                	{
                		houseId = Integer.parseInt(requestorHouseId);
                	}
                		   
                	if (ValidationUtil.hasValue(amenityNum))
                	{
                		amenityId = Integer.parseInt(amenityNum);
                	}
                	
                	// SEARCH for the Amenity request
                	arList = searchRequestsService.adminSearchUserAmenityRequests(userId, houseId, amenityId, status, fromDate, toDate);
                }
        	}
        	
        	return getMapAR(arList);
        	
    	}
    	catch (Exception ex)
		{
    		System.out.println("Error retrieving amenity requests from database. " + ex.getMessage());
    		logger.error("Error retrieving amenity requests from database. " + ex.getMessage());
    		
	        return getModelMapError("Error retrieving amenity requests from database.");
	    }
    }
    
   
    
    
    /**
     * Get service requests.
     * 
     * If the calling page is the Admin Home page, get service requests by STATUS (just add the desired status to the statusList 
     * example: NEW, BOOKED etc.)
     * If the calling page is the Admin Search page, call the Search service.
     * 
     * @param request - HttpServletRequest
	 * @param session - HttpSession
	 * 
	 * @return map of Object
     */
    @RequestMapping(value = "/admin/get-service-requests.action")
    public @ResponseBody
    Map<String, ? extends Object> getAdminServiceRequests(HttpServletRequest request, HttpSession session) throws Exception
    {
    	String callingPage = request.getParameter("callingPage");
    	List<ServiceBean> srList = new ArrayList<ServiceBean>();
    	
    	try
    	{
    		if (ValidationUtil.hasValue(callingPage))
        	{
    			if (callingPage.equals(ConstantsUtil.SEARCH_CALLING_PAGE_HOME))
        		{
    				System.out.println("\n***** Admin HOME getting Service requests...");
            		System.out.println("callingPage: " + callingPage);
            		
            		logger.info("***** Admin HOME getting Service requests...");
            		logger.info("callingPage: " + callingPage);
            		
        			List<String> statusList = new ArrayList<String>(); 
                    statusList.add(ConstantsUtil.NEW);                	// just add the status we wish to display in the amenities tab
                    // statusList.add(ConstantsUtil.BOOKED);
                    
                    srList = serviceRequestService.getServiceRequestsByStatus(statusList);
                }
        		else if (callingPage.equals(ConstantsUtil.SEARCH_CALLING_PAGE_SEARCH))
            	{
        			System.out.println("\n***** Admin SEARCH getting Service requests...");	// RACS
            		System.out.println("callingPage: " + callingPage);
            		
            		logger.info("***** Admin SEARCH getting Service requests...");
            		logger.info("callingPage: " + callingPage);
            		
        			String requestorUserId = (String) session.getAttribute("requestorUserId");
        			String requestorHouseId = (String) session.getAttribute("requestorHouseId");
        			String serviceNum = (String) session.getAttribute("serviceId");
        			String status = (String) session.getAttribute("status");
        			String fromDate = (String) session.getAttribute("fromDate");
        			String toDate = (String) session.getAttribute("toDate");
        			
        			int userId = 0;
        			int houseId = 0;
        			int serviceId = 0;
        			
        			if (ValidationUtil.hasValue(requestorUserId))
        			{
        				userId = Integer.parseInt(requestorUserId);
        			}
        			   
        			if (ValidationUtil.hasValue(requestorHouseId))
        			{
        				houseId = Integer.parseInt(requestorHouseId);
        			}
        			
        			if (ValidationUtil.hasValue(serviceNum))
        			{
        				serviceId = Integer.parseInt(serviceNum);
        			}
        			
        			// SEARCH for the Service request
        			srList = searchRequestsService.adminSearchUserServiceRequests(userId, houseId, serviceId, status, fromDate, toDate);	// RACS
        		}
    		}
    		
    		return getMapSR(srList);
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Error retrieving service requests from database. " + ex.getMessage());
    		logger.error("Error retrieving service requests from database. " + ex.getMessage());
    		
    		return getModelMapError("Error retrieving amenity requests from database.");
    	}
    }
  

    
    
    
    @RequestMapping(value = "/admin/amenities.action")
    public @ResponseBody
    Map<String, ? extends Object> viewAmenitiesRequest(HttpSession session) throws Exception {
        try {
            System.out.println("went to viewAmenitiesRequest ....");
            int userId = Integer.parseInt(session.getAttribute("reqId").toString());
            int amenityId = Integer.parseInt(session.getAttribute("desc").toString());
            String status = session.getAttribute("reqStatus").toString();
            String reqDate = session.getAttribute("reqDate").toString();
            List<AmenityBean> ar = amenitiesRequestService.getAmenityListbyAdmin(userId,status,reqDate,amenityId);
            return getMapAR(ar);
        } catch (Exception e) {
            return getModelMapError("Error retrieving amenities from database.");
        }
    }
    
    
    
    /**
     * This method is called when trying to update the status of an AMENITY REQUEST in the admin portal.
     * Example: If the STATUS in the Reservation details is changed from NEW to DONE, then admin clicks "Submit",
     * this method is used
     * 
     * */
    @RequestMapping(value = "/admin/update/amenities.action",method=RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> updateAmenityRequest(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("\n***** Updating amenity with ID: " + request.getParameter("id"));
    	logger.info("***** Updating amenity with ID: " + request.getParameter("id"));
    	
    	String amenityIdParam = request.getParameter("id");
    	String basicCostParam = request.getParameter("basic_cost");
    	String additionalCostParam = request.getParameter("additional_cost");
    	String otherChargesParam = request.getParameter("otherCharges");
    	String totalCostParam = request.getParameter("total_cost");
    	String quantityParam = request.getParameter("quantity");
    	String startTimeParam = request.getParameter("startTime");
    	String endTimeParam = request.getParameter("endTime");
    	
    	System.out.println("startTimeParam: " + startTimeParam);
    	System.out.println("endTimeParam: " + endTimeParam);
    	
    	logger.info("startTimeParam: " + startTimeParam);
    	logger.info("endTimeParam: " + endTimeParam);
    	
    	String status = request.getParameter("status");
    	String remarks = request.getParameter("remarks");
    	
    	
    	int amenityId = 0;
    	Double basicCost = new Double("0.00");
    	Double additionalCost = new Double("0.00");
    	Double otherCharges = new Double("0.00");
    	Double totalCost = new Double("0.00");
    	int quantity = 0;
    	String startTime = null;
    	String endTime = null;
    	
    	
    	if (ValidationUtil.hasValue(amenityIdParam))
    	{
    		amenityId = Integer.parseInt(amenityIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(basicCostParam))
    	{
    		basicCost = Double.parseDouble(basicCostParam);
    	}
    	
    	if (ValidationUtil.hasValue(additionalCostParam))
    	{
    		additionalCost = Double.parseDouble(additionalCostParam);
    	}
    	
    	if (ValidationUtil.hasValue(otherChargesParam))
    	{
    		otherCharges = Double.parseDouble(otherChargesParam);
    	}
    	
    	if (ValidationUtil.hasValue(totalCostParam))
    	{
    		totalCost = Double.parseDouble(totalCostParam);
    	}
    	
    	if (ValidationUtil.hasValue(quantityParam))
    	{
    		quantity = Integer.parseInt(quantityParam);
    	}
    	
    	if (ValidationUtil.hasValue(startTimeParam))
    	{
    		startTime = DateTimeUtil.formatToMilitaryTimeDuringUpdate(startTimeParam);
    		
    		System.out.println("startTime: " + startTime);
        	logger.info("startTime: " + startTime);
        }
    	
    	if (ValidationUtil.hasValue(endTimeParam))
    	{
    		endTime = DateTimeUtil.formatToMilitaryTimeDuringUpdate(endTimeParam);
    		
    		System.out.println("endTime: " + endTime);
        	logger.info("endTime: " + endTime);
    	}
    	
    	
        
    	AmenitiesRequest ar = null;
    	try
        {
    		// get the Amenity request using its ID
    		ar = amenitiesRequestService.getAmenityRequestById(amenityId);
    		
    		if (ar != null)
    		{
    			// update its field values using the parameter values then save
    			System.out.println("Got Amenity request for user id " + ar.getUserId());
    			logger.info("Got Amenity request for user id " + ar.getUserId());
    			
    			ar.setBasicCost(basicCost);
    			ar.setAdditionalCost(additionalCost);
    			ar.setOtherCharges(otherCharges);
    			ar.setTotalCost(totalCost);
    			ar.setQuantity(quantity);
    			ar.setStartTime(startTime);
    			ar.setEndTime(endTime);
    			ar.setRemarks(remarks);
    			ar.setStatus(status);
    			ar.setUpdatedAt(new Date());
    			
    			amenitiesRequestService.updateAmenityRequest(ar);
    		}
    		
    		System.out.println("Creating the model map...");
    		logger.info("Creating the model map...");
    		
    		Map<String, Object> modelMap = new HashMap<String, Object>(2);
            // modelMap.put("total", amenitys.size());
            modelMap.put("data", ar);
            modelMap.put("success", true);
            
           return modelMap;
    		
    		
        	//amenitiesRequestService.updateViaAdmin(ar);
        	
            // List<AmenitiesRequest> v = amenitiesRequestService.updateViaAdmin(ar);	// this updates a request's status and other fields
            
            //int userId = Integer.parseInt(session.getAttribute("reqId").toString());
           // int amenityId = Integer.parseInt(session.getAttribute("desc").toString());
            // String status = session.getAttribute("reqStatus").toString();
            // String reqDate = session.getAttribute("reqDate").toString();
            // List<AmenityBean> ars = amenitiesRequestService.getAmenityListbyAdmin(userId,status,reqDate,amenityId);
            // return getMapAR(ars);
        }
        catch (Exception e)
        {
            return getModelMapError("Error trying to update amenity request." + e.toString());
        }
    }
    
    

    /**
     * This method is called when trying to update the status of an AMENITY REQUEST in the admin portal.
     * Example: If the STATUS in the Reservation details is changed from NEW to DONE, then admin clicks "Submit",
     * this method is used
     * 
     * */
    @RequestMapping(value = "/admin/update/services.action",method=RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> updateServiceRequest(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("\n***** Updating service with ID: " + request.getParameter("id"));
    	logger.info("***** Updating service with ID: " + request.getParameter("id"));
    	
    	String serviceIdParam = request.getParameter("id");
    	
    	String basicCostParam = request.getParameter("basic_cost");
    	System.out.println("basicCostParam: " + basicCostParam);
    	logger.info("basicCostParam: " + basicCostParam);
    	
    	String additionalCostParam = request.getParameter("additional_cost");
    	String otherChargesParam = request.getParameter("otherCharges");
    	
    	String totalCostParam = request.getParameter("total_cost");
    	System.out.println("totalCostParam: " + totalCostParam);
    	logger.info("totalCostParam: " + totalCostParam);
    	
    	String quantityParam = request.getParameter("quantity");
    
    	
    	String status = request.getParameter("status");
    	String remarks = request.getParameter("remarks");
    	String preferredDate = request.getParameter("preferredDate");
    	System.out.println("preferredDate: " + preferredDate);
    	logger.info("preferredDate: " + preferredDate);
    	
    	
    	int serviceId = 0;
    	Double basicCost = new Double("0.00");
    	Double additionalCost = new Double("0.00");
    	Double otherCharges = new Double("0.00");
    	Double totalCost = new Double("0.00");
    	int quantity = 0;
    	
    	
    	if (ValidationUtil.hasValue(serviceIdParam))
    	{
    		serviceId = Integer.parseInt(serviceIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(basicCostParam))
    	{
    		basicCost = Double.parseDouble(basicCostParam);
    	}
    	
    	if (ValidationUtil.hasValue(additionalCostParam))
    	{
    		additionalCost = Double.parseDouble(additionalCostParam);
    	}
    	
    	if (ValidationUtil.hasValue(otherChargesParam))
    	{
    		otherCharges = Double.parseDouble(otherChargesParam);
    	}
    	
    	if (ValidationUtil.hasValue(totalCostParam))
    	{
    		totalCost = Double.parseDouble(totalCostParam);
    	}
    	
    	if (ValidationUtil.hasValue(quantityParam))
    	{
    		quantity = Integer.parseInt(quantityParam);
    	}
    	
    	
        
    	ServiceRequest sr = null;
    	try
        {
    		// get the Service request using its ID
    		sr = serviceRequestService.getServiceRequestById(serviceId);
    		
    		if (sr != null)
    		{
    			// update its field values using the parameter values then save
    			System.out.println("Got Service request for user id " + sr.getUserId());
    			logger.info("Got Service request for user id " + sr.getUserId());
    			
    			sr.setBasicCost(basicCost);
    			sr.setAdditionalCost(additionalCost);
    			sr.setOtherCharges(otherCharges);
    			sr.setTotalCost(totalCost);
    			sr.setQuantity(quantity);
    			sr.setPreferredDate(preferredDate);
    			sr.setRemarks(remarks);
    			sr.setStatus(status);
    			sr.setUpdatedAt(new Date());
    			
    			serviceRequestService.updateServiceRequest(sr);
    		}
    		
    		
    		System.out.println("Creating the model map...");
    		logger.info("Creating the model map...");
    		
    		Map<String, Object> modelMap = new HashMap<String, Object>(2);
            // modelMap.put("total", amenitys.size());
            modelMap.put("data", sr);
            modelMap.put("success", true);
            
           return modelMap;
    	}
        catch (Exception e)
        {
            return getModelMapError("Error trying to update service request." + e.toString());
        }
    }
    
    
    
    private Map<String, Object> getMapAR(List<AmenityBean> amenitys) 
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", amenitys.size());
        modelMap.put("data", amenitys);
        modelMap.put("success", true);
        
        System.out.println("Number of results search Amenity: " + amenitys.size());
        logger.info("Number of results search Amenity: " + amenitys.size());
        
        // System.out.println(modelMap);
        return modelMap;
    }
    
    
    private Map<String, Object> getMapSR(List<ServiceBean> services) 
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", services.size());
        modelMap.put("data", services);
        modelMap.put("success", true);
        
        System.out.println("Number of results search Service: " + services.size());
        logger.info("Number of results search Service: " + services.size());
        
        // System.out.println(modelMap);
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
     * @param searchRequestsService the searchRequestsService to set
     */
    @Autowired
    public void setSearchRequestsService(SearchRequestsService searchRequestsService) {
        this.searchRequestsService = searchRequestsService;
    }
    
    
    /**
     * @param serviceRequestService the serviceRequestService to set
     */
    @Autowired
    public void setServiceRequestService(ServiceRequestService serviceRequestService)
    {
        this.serviceRequestService = serviceRequestService;
    }
  
}

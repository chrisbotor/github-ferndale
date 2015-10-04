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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.Amenity;
import com.homeportal.model.Employee;
import com.homeportal.model.ServiceRequest;
import com.homeportal.model.Services;
import com.homeportal.model.Vehicle;
import com.homeportal.service.AmenitiesRequestService;
import com.homeportal.service.AmenityService;
import com.homeportal.service.EmployeeService;
import com.homeportal.service.RequestService;
import com.homeportal.service.ServiceRequestService;
import com.homeportal.service.ServiceService;
import com.homeportal.service.VehicleService;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.DateTimeUtil;
import com.homeportal.util.ValidationUtil;


/**
 * This controller manages the creation, search and retrieval and update of an Amenity and a Service request by the User
 * 
 * @author Racs
 */
@Controller
public class UserRequestController 
{
	private static Logger logger = Logger.getLogger(UserRequestController.class);
	
	private String DATE_DEFAULT_FORMAT_FROM_REQUEST = "E MMM dd yyyy HH:mm:ss Z";
	private String DATE_DEFAULT_FORMAT_DB = "MM/dd/yyyy";
	
    private AmenityService amenityService;
    private AmenitiesRequestService amenitiesRequestService;
    private ServiceService serviceService;
    private ServiceRequestService serviceRequestService;
    private VehicleService vehicleService;
    private EmployeeService employeeService;
    
    private RequestService requestService;
    
    
    
    // USER CREATES REQUESTS
    /**
     * Used to display the RESERVATION REQUEST Form when user wants to create an Amenity or a Service request
     * */
    @RequestMapping("/user-create-request.action")
    String viewReservationRequestForm() throws Exception
    {
    	System.out.println("\n***** User getting the Reservation Request form and creating request...");
    	logger.info("***** User getting the Reservation Request form and creating request...");
    	
    	return "user-create-request";
    }
    
    
    // GETS AMENITY OR SERVICE FEE
    /**
     * Used by both admin and user to compute for Amenity or Service fee during request creation
     * 
     * */
    @RequestMapping(value="/user/compute-amenity-or-service-fee.action")
    public @ResponseBody
    Map<String, ? extends Object> computeAmenityOrServiceFee(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("===== Computing for the Amenity or Service Fee...");
    	logger.info("===== Computing for the Amenity or Service Fee...");
    	
    	String jobOrderTypeIdParam = request.getParameter("jobOrderTypeId");
    	String amenityOrServiceIdParam = request.getParameter("amenityOrServiceId");
    	String param2Param = request.getParameter("param2");
    	
    	int jobOrderTypeId = 0;
    	int amenityOrServiceId = 0;
    	int param2 = 0;
    	
    	if (ValidationUtil.hasValue(jobOrderTypeIdParam))
    	{
    		jobOrderTypeId = Integer.parseInt(jobOrderTypeIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(amenityOrServiceIdParam))
    	{
    		amenityOrServiceId = Integer.parseInt(amenityOrServiceIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(param2Param))
    	{
    		param2 = Integer.parseInt(param2Param);
    	}
    	
    	// call the common request service
    	Double fee = requestService.getAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2);
    	
    	 Map<String, Object> modelMap = new HashMap<String, Object>(3);

    	 modelMap.put("data", fee);
         modelMap.put("success", true);
         System.out.println(modelMap);
         return modelMap;
    }
    
    
    
    // VALIDATE DATE AND TIME OVERLAP
    /**
     * Used by both admin and user to validate service request and date overlap during request creation
     * 
     * */
    @RequestMapping(value="/user/validate-service-request-and-date-overlap.action")
    public @ResponseBody
    Map<String, ? extends Object> validateServiceRequestAndDateOverlap(HttpServletRequest request, HttpSession session) throws Exception
    {
    	logger.info("Validating service request and date overlap...");
    	System.out.println("Validating service request and date overlap...");
    	
    	String serviceReqIdParam = request.getParameter("serviceReqId");
    	String requestedDateParam = request.getParameter("requestedDate");
    	
    	String dateParam = request.getParameter("date");
    	
    	logger.info("serviceReqIdParam: " + serviceReqIdParam);
    	logger.info("requestedDateParam: " + requestedDateParam);
    	
    	System.out.println("serviceReqIdParam: " + serviceReqIdParam);
    	System.out.println("requestedDateParam: " + requestedDateParam);
    	
    	// FORMAT date to 'MM/dd/yyyy' format. Example: '11/30/2013'
        String formattedDate = DateTimeUtil.convertDateStringToAnotherFormat(requestedDateParam, DATE_DEFAULT_FORMAT_FROM_REQUEST, DATE_DEFAULT_FORMAT_DB);
    	
        boolean withOverlap = false;
    	
    	if (ValidationUtil.hasValue(serviceReqIdParam))
    	{
    		withOverlap = serviceService.ValidateServiceRequestAndDateOverlap(Integer.parseInt(serviceReqIdParam), formattedDate);
    	}
    	
    	
    	Map<String, Object> modelMap = new HashMap<String, Object>(3);
    	modelMap.put("data", withOverlap);
        modelMap.put("success", true);
        
        System.out.println(modelMap);
        logger.info("*********  WITH OVERLAP? ***********");
        logger.info(modelMap);
        return modelMap;
    }
    
    
    
    // VALIDATE DATE AND TIME OVERLAP
    /**
     * Used by both admin and user to validate date and time overlap during request creation
     * 
     * */
    @RequestMapping(value="/user/validate-date-and-time-overlap.action")
    public @ResponseBody
    Map<String, ? extends Object> validateDateAndTimeOverlap(HttpServletRequest request, HttpSession session) throws Exception
    {
    	logger.info("Validating date and time overlap...");
    	System.out.println("Validating date and time overlap...");
    	
    	String requestTypeParam = request.getParameter("requestType");
    	String dateParam = request.getParameter("date");
    	String startTimeParam = request.getParameter("startTime");
    	String endTimeParam = request.getParameter("endTime");
    	String requestIdParam = request.getParameter("requestId");
    	
    	logger.info("ECY requestTypeParam: " + requestTypeParam);
    	logger.info("dateParam: " + dateParam);
    	logger.info("startTimeParam: " + startTimeParam);
    	logger.info("endTimeParam: " + endTimeParam);
    	logger.info("requestIdParam: " + requestIdParam);
    	
    	System.out.println("requestTypeParam: " + requestTypeParam);
    	System.out.println("dateParam: " + dateParam);
    	System.out.println("startTimeParam: " + startTimeParam);
    	System.out.println("endTimeParam: " + endTimeParam);
    	System.out.println("requestIdParam: " + requestIdParam);
    	
    	
    	
    	
    	// FORMAT date to 'MM/dd/yyyy' format. Example: '11/30/2013'
        //final String format = "MM/dd/yyyy";
        //String date = DateTimeUtil.convertDateToString(dateParam, format));
    	
    	String formattedDate = DateTimeUtil.convertDateStringToAnotherFormat(dateParam, DATE_DEFAULT_FORMAT_FROM_REQUEST, DATE_DEFAULT_FORMAT_DB);
    	// to be continued String startTime = DateTimeUtil.formatToMilitaryTime(timeString);
    	
    	// to be continued amenitiesRequestService.validateDateAndTimeOverlap(formattedDate, );
    	
    	
    	String formattedStartTime = DateTimeUtil.formatToMilitaryTime(startTimeParam);
    	String formattedEndTime = DateTimeUtil.formatToMilitaryTime(endTimeParam);
    	boolean withOverlap = false;
    	
    	if (ValidationUtil.hasValue(requestTypeParam))
    	{
    		if (Integer.parseInt(requestTypeParam) == 1)
    		{
    			withOverlap = amenitiesRequestService.validateDateAndTimeOverlap(Integer.parseInt(requestIdParam), formattedDate, formattedStartTime, formattedEndTime);
    		}
    		else if (Integer.parseInt(requestTypeParam) == 2)
    		{
    			// withOverlap = serviceRequestService.validateDateAndTimeOverlap(Integer.parseInt(requestIdParam), formattedDate, formattedStartTime, formattedEndTime);
    		}
    	}
    	
    	
    	
    	/*String jobOrderTypeIdParam = request.getParameter("jobOrderTypeId");
    	String amenityOrServiceIdParam = request.getParameter("amenityOrServiceId");
    	String param2Param = request.getParameter("param2");
    	
    	int jobOrderTypeId = 0;
    	int amenityOrServiceId = 0;
    	int param2 = 0;
    	
    	if (ValidationUtil.hasValue(jobOrderTypeIdParam))
    	{
    		jobOrderTypeId = Integer.parseInt(jobOrderTypeIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(amenityOrServiceIdParam))
    	{
    		amenityOrServiceId = Integer.parseInt(amenityOrServiceIdParam);
    	}
    	
    	if (ValidationUtil.hasValue(param2Param))
    	{
    		param2 = Integer.parseInt(param2Param);
    	}
    	
    	// call the common request service
    	Double fee = requestService.getAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2);*/
    	
    	 Map<String, Object> modelMap = new HashMap<String, Object>(3);

    	 //modelMap.put("data", fee);
    	 modelMap.put("data", withOverlap);
         modelMap.put("success", true);
         System.out.println(modelMap);
         logger.info("********* CHRIS WITH OVERLAP? ***********");
         logger.info(modelMap);
         return modelMap;
    }
    
    
    
    /**
     * NOTE: THIS METHOD IS USED BY THE USER IN CREATING AMENITIES REQUEST
     * */
    @RequestMapping(value = "/user/create-request.action", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> createRequest(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("===========  USER CREATING REQUEST...  =================");
    	logger.info("===========  USER CREATING REQUEST...  =================");
    	
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
    		startTime = DateTimeUtil.formatToMilitaryTime(startTimeParam);
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
    	
    	modelMap.put("success", true);
	     
	    System.out.println(modelMap);
	    logger.info(modelMap);
    	 
	    return modelMap;
    }
    
    
    
    
    
    /**
     * Displays the box where the user can create an Amenity or a Service request
     * 
     * */
    @RequestMapping("/user-request")
    String view() throws Exception
    {
    	System.out.println("went to  Request Controller...view");
        return "user-request";
    }

    
    
    
    
    @RequestMapping(value="/amenities/view.action")
    public @ResponseBody
    Map<String, ? extends Object> loadAmenities() throws Exception {
            List<Amenity> list = amenityService.getAmenityList();
            return getMap(list);
    }

    @RequestMapping(value="/services/view.action")
    public @ResponseBody
    Map<String, ? extends Object> loadServices() throws Exception {
            List<Services> list = serviceService.getServicesList();
            return getMapServices(list);
    }

    
// ###########################################        SUBMIT REQUESTS         ###############################################################
    // TODO
    @RequestMapping(value="/request/amenities.action", method=RequestMethod.POST)
    /*public @ResponseBody Map<String, ? extends Object> addAmenities(Amenities_Request ar, @RequestParam("houseId") String houseId, // wrong parameters, should be HttpServletRequest
    		HttpSession session) throws Exception*/
    public @ResponseBody Map<String, ? extends Object> addAmenities(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("\n**** Submitting request...");
    	logger.info("**** Submitting request...");
    	
        try
        {
            String userId = session.getAttribute("userId").toString();
            
            System.out.println("userId: " + userId);
            
           /* ar.setUserId(Integer.parseInt(userId));
            ar.setHouseId(Integer.parseInt(houseId));
            ar.setCreatedAt(new Date());
            ar.setUpdatedAt(new Date());*/
            
            Map<String, Object> data = new HashMap<String, Object>();
          //  amenitiesRequestService.create(ar);
	    
            data.put("success",Boolean.TRUE);
            return data;
            
        } catch (Exception e) {
            return getModelMapError("Error trying to submit Amenity request.");
        }
    }

    
    
    
    @RequestMapping(value="/request/services.action", method=RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> addServices(ServiceRequest sr, @RequestParam("houseId") String houseId, 
    		HttpSession session)  throws Exception {
        try {
    		logger.info("Submitting service request....");
    		
            String userId = session.getAttribute("userId").toString();
            
            sr.setUserId(Integer.parseInt(userId));
            sr.setHouseId(Integer.parseInt(houseId));
            sr.setCreatedAt(new Date());
            sr.setUpdatedAt(new Date());
            
            Map<String, Object> data = new HashMap<String, Object>();
            serviceRequestService.create(sr);
            
            data.put("success",Boolean.TRUE);
            return data;
            
        } catch (Exception e) {
            return getModelMapError("Error trying to submit Service request.");
        }
    }
    
    
    
    
    @RequestMapping(value="/request/vehicle.action", method=RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> addVehicle(Vehicle vehicle, @RequestParam("houseId") String houseId,
    		HttpSession session)  throws Exception {
        try {
            String userId = session.getAttribute("userId").toString();
            
            vehicle.setUserId(Integer.parseInt(userId));
            vehicle.setHouseId(Integer.parseInt(houseId));
            vehicle.setCreatedAt(new Date());
            vehicle.setUpdatedAt(new Date());
            
            Map<String, Object> data = new HashMap<String, Object>();
            vehicleService.create(vehicle);
	    
            data.put("success",Boolean.TRUE);
            return data;
            
        } catch (Exception e) {
            return getModelMapError("Error trying to submit request for vehicle sticker.");
        }
    }
    
    @RequestMapping(value = "/request/employee.action")
    public @ResponseBody
    Map<String, ? extends Object> addEmployee(Employee employee, @RequestParam("houseId") String houseId,
    		HttpSession session) throws Exception {
        try {
            String userId = session.getAttribute("userId").toString();
            
            employee.setUserId(Integer.parseInt(userId));
            employee.setHouseId(Integer.parseInt(houseId));
            employee.setCreatedAt(new Date());
            employee.setUpdatedAt(new Date());
            
            Map<String, Object> data = new HashMap<String, Object>();
            employeeService.create(employee);
            
            data.put("success",Boolean.TRUE);
            return data;
            
        } catch (Exception e) {
            return getModelMapError("Error trying to submit request for Authorized Employee.");
        }
    }

    

     /**
     * Generates modelMap to return in the modelAndView
     *
     * @param House
     * @return
     */
    private Map<String, Object> getMap(List<Amenity> amenitys) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", amenitys.size());
        modelMap.put("data", amenitys);
        modelMap.put("success", true);
        // System.out.println(modelMap);
        return modelMap;
    }

     /**
     * Generates modelMap to return in the modelAndView
     *
     * @param House
     * @return
     */
    private Map<String, Object> getMapServices(List<Services> ser) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", ser.size());
        modelMap.put("data", ser);
        modelMap.put("success", true);
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
     * @param amenityService the amenityService to set
     */
    @Autowired
    public void setAmenityService(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    /**
     * @param amenitiesRequestService the amenitiesRequestService to set
     */
    @Autowired
    public void setAmenitiesRequestService(AmenitiesRequestService amenitiesRequestService) {
        this.amenitiesRequestService = amenitiesRequestService;
    }

    /**
     * @param serviceService the serviceService to set
     */
    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * @param serviceRequestService the serviceRequestService to set
     */
    @Autowired
    public void setServiceRequestService(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
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
    
    
    /**
     * @param requestService the requestService to set
     */
    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}

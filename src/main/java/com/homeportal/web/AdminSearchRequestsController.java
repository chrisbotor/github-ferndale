/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Displays the view for the Admin Search box
 * 
 * @author Racs
 */
@Controller
public class AdminSearchRequestsController 
{
	private static Logger logger = Logger.getLogger(AdminSearchRequestsController.class);
    
	
	/**
	 * Gets the box where admin can specify search criteria
	 * */
    @RequestMapping("/admin-search-requests")
    String view(HttpSession session) throws Exception 
    {
    	System.out.println("went to view of AdminSearchRequestsController ");
    	logger.info("went to view of AdminSearchRequestsController...");
    	
         session.removeAttribute("reqId");
         session.removeAttribute("desc");
         session.removeAttribute("reqStatus");
         session.removeAttribute("reqDate");
         
         return "admin-search-requests";
    }
    
    
    
    /**
	 * Display search amenity results in grid view.
	 * 
	 * @param request - HttpServletRequest
	 * @param session - HttpSession
	 * 
	 * @return name of the next page
	 * */
    @RequestMapping("/admin-search-amenity-requests.action")
    String searchAmenityRequests(HttpServletRequest request, HttpSession session) throws Exception 
    {
    	System.out.println("\n***** Searching for Amenity requests...");
 	    System.out.println("USER ID: " + request.getParameter("requestorUserId"));
 	    System.out.println("HOUSE ID: " + request.getParameter("requestorHouseId"));
 	    System.out.println("AMENITY ID: " + request.getParameter("amenityId"));
 	    System.out.println("STATUS: " + request.getParameter("status"));
 	    System.out.println("FROM DATE: " + request.getParameter("fromDate"));
 	    System.out.println("TO DATE: " + request.getParameter("toDate"));
    	
    	logger.info("***** Searching for Amenity requests...");
    	logger.info("USER ID: " + request.getParameter("requestorUserId"));
    	logger.info("HOUSE ID: " + request.getParameter("requestorHouseId"));
    	logger.info("AMENITY ID: " + request.getParameter("amenityId"));
    	logger.info("STATUS: " + request.getParameter("status"));
    	logger.info("FROM DATE: " + request.getParameter("fromDate"));
	    logger.info("TO DATE: " + request.getParameter("toDate"));
	   
	    session.setAttribute("requestorUserId", request.getParameter("requestorUserId"));
	    session.setAttribute("requestorHouseId", request.getParameter("requestorHouseId"));
	    session.setAttribute("amenityId", request.getParameter("amenityId"));
	    session.setAttribute("serviceId", request.getParameter("serviceId"));
	    session.setAttribute("status", request.getParameter("status"));
	    session.setAttribute("fromDate", request.getParameter("fromDate"));
	    session.setAttribute("toDate", request.getParameter("toDate"));
	    
	    return "admin-search-amenity-requests";
    }
    
    
    
    /**
   	 * Display search service results in grid view.
   	 * 
   	 * @param request - HttpServletRequest
   	 * @param session - HttpSession
   	 * 
   	 * @return name of the next page
   	 * */
    @RequestMapping("/admin-search-service-requests.action")
    String getServiceRequests(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("\n***** Searching for Service requests...");
    	System.out.println("USER ID: " + request.getParameter("requestorUserId"));
    	System.out.println("HOUSE ID: " + request.getParameter("requestorHouseId"));
		System.out.println("SERVICE ID: " + request.getParameter("serviceId"));
		System.out.println("STATUS: " + request.getParameter("status"));
		System.out.println("FROM DATE: " + request.getParameter("fromDate"));
		System.out.println("TO DATE: " + request.getParameter("toDate"));
		   
    	logger.info("***** Searching for Service requests...");
    	logger.info("USER ID: " + request.getParameter("requestorUserId"));
		logger.info("HOUSE ID: " + request.getParameter("requestorHouseId"));
		logger.info("SERVICE ID: " + request.getParameter("serviceId"));
		logger.info("STATUS: " + request.getParameter("status"));
		logger.info("FROM DATE: " + request.getParameter("fromDate"));
		logger.info("TO DATE: " + request.getParameter("toDate"));
		
		session.setAttribute("requestorUserId", request.getParameter("requestorUserId"));
		session.setAttribute("requestorHouseId", request.getParameter("requestorHouseId"));
		session.setAttribute("serviceId", request.getParameter("serviceId"));
		session.setAttribute("status", request.getParameter("status"));
		session.setAttribute("fromDate", request.getParameter("fromDate"));
		session.setAttribute("toDate", request.getParameter("toDate"));
		
		return "admin-search-service-requests";
	}

}

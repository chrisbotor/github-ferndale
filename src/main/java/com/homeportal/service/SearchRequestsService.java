/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.dao.impl.SearchRequestsDaoImpl;
import com.homeportal.util.ValidationUtil;

/**
 *
 * @author ranievas
 */
@Service
public class SearchRequestsService 
{
	private static Logger logger = Logger.getLogger(SearchRequestsService.class);
	private SearchRequestsDaoImpl searchRequestsDaoImpl;
    
    
	
    /**
     * USED BY ADMIN SEARCH AMENITY REQUESTS - Gets all the amenities requested by a particular home owner, results are displayed in the ADMIN page
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AmenityBean> adminSearchUserAmenityRequests(int userId, int houseId, int amenityId, String status, String fromDate, String toDate)
    {
    	List<AmenityBean> amenityBeanList = new ArrayList<AmenityBean>();
    	
    	System.out.println("SERVICE searching for Amenity requests...");
    	System.out.println("userId: " + userId);
    	System.out.println("houseId: " + houseId);
    	System.out.println("amenityId: " + amenityId);
    	System.out.println("status: " + status);
    	System.out.println("fromDate: " + fromDate);
    	System.out.println("toDate: " + toDate);
    	
    	logger.info("SERVICE searching for Amenity requests...");
    	logger.info("userId: " + userId);
    	logger.info("houseId: " + houseId);
    	logger.info("amenityId: " + amenityId);
    	logger.info("status: " + status);
    	logger.info("fromDate: " + fromDate);
    	logger.info("toDate: " + toDate);
    	
    	
    	// NO Requestor
    	if (userId == 100 && houseId == 100)
    	{
    		// #################################################  by AMENITY  ###########################################################################
    		// by AMENITY only
    		if (amenityId > 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by AMENITY only...");	// OK!
    			logger.info("Searching by AMENITY only...");
    			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByAmenityOnly(amenityId);
    		}
    		// by FROM DATE only
    		else if (amenityId == 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by FROM DATE only...");	// OK!
    			logger.info("Searching by FROM DATE only...");
    			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByFromDateOnly(fromDate);
    		}
    		// by fromDate and toDate
    		else if (amenityId == 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasValue(toDate))
    		{
    			System.out.println("\nSearching by fromDate and toDate...");	// OK!
    			logger.info("Searching by fromDate and toDate...");
    			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByFromDateAndToDate(fromDate, toDate);
    		}
    		// by Amenity and Status
    		else if (amenityId > 0 && ValidationUtil.hasValue(status) && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by AMENITY and STATUS...");	// OK!
    			logger.info("Searching by AMENITY and STATUS...");
    			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByAmenityAndStatus(amenityId, status);
    		}
    		// by Amenity, Status and FromDate
    		else if (amenityId > 0 && ValidationUtil.hasValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by AMENITY, STATUS and fromDate...");	// NEED SQL PA!
    			logger.info("Searching by AMENITY, STATUS and fromDate...");
    			// amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByAmenityStatusAndFromDate(amenityId, status, fromDate);
    		}
    		// by Amenity, Status, FromDate and ToDate
    		else if (amenityId > 0 && ValidationUtil.hasValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasValue(toDate))
    		{
    			System.out.println("\nSearching by AMENITY, STATUS, fromDate and toDate...");	// NEED SQL PA!
    			logger.info("Searching by AMENITY, STATUS, fromDate and toDate...");
    		}
    		// #################################################  by STATUS  ###############################################################################################
    		// by STATUS only
    		else if (ValidationUtil.hasValue(status) && amenityId == 0 && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by STATUS only...");	// OK!
    			logger.info("Searching by STATUS only...");
    			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByStatusOnly(status);
    		}
    		
    		
    		// SEARCH FOR ALL
    		else if (amenityId == 0 && ValidationUtil.hasNoValue(status)
        			&& ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching ALL amenity requests...");	// OK! (check the status muna)
    			logger.info("Searching ALL amenity requests...");
        		amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsAll();
        	}
    	}
    	else
    	{
    		// WITH REQUESTOR already
    		if (userId > 0 && houseId > 0)
        	{
    			if (amenityId > 0 && ValidationUtil.hasNoValue(status))
    			{
    				// by REQUESTOR and AMENITY
    				System.out.println("Searching using REQUESTOR and AMENITY...");	// OK!
    				logger.info("Searching using REQUESTOR and AMENITY...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAndAmenity(userId, houseId, amenityId);
    			}
    			else if (amenityId == 0 && ValidationUtil.hasValue(status))
    			{
    				// by REQUESTOR and STATUS
    				System.out.println("Searching using REQUESTOR and STATUS..."); // OK!
    				logger.info("Searching using REQUESTOR and STATUS...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAndStatus(userId, houseId, status);
    			}
    			else if (amenityId > 0 && ValidationUtil.hasValue(status))		// ECY
    			{
    				// by REQUESTOR, AMENITY and STATUS
    				System.out.println("Searching using REQUESTOR, AMENITY and STATUS..."); // OK!
    				logger.info("Searching using REQUESTOR, AMENITY and STATUS...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAmenityAndStatus(userId, houseId, amenityId, status);
    			}
    			else if (amenityId == 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(fromDate))
    			{
    				// REQUESTOR ONLY
    				System.out.println("Searching using REQUESTOR only...");	// OK! (Lumalabas lahat ng status dito even Paid)
    				logger.info("Searching using REQUESTOR only...");
        			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorOnly(userId, houseId);
    			}
    				
    			
    			
    			
    			
    			
    			/*if (amenityId > 0)
    			{
    				logger.info("Searching using REQUESTOR and AMENITY...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAndAmenity(userId, houseId, amenityId);
    			}
    			// by REQUESTOR and STATUS
    			else if (ValidationUtil.hasValue(status))
    			{
    				logger.info("Searching using REQUESTOR and STATUS...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAndStatus(userId, houseId, status);
    			}
    			// by REQUESTOR, AMENITY and STATUS
    			else if (amenityId > 0 && ValidationUtil.hasValue(status))
    			{
    				logger.info("Searching using REQUESTOR, AMENITY and STATUS...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAmenityAndStatus(userId, houseId, amenityId, status);
    			}
    			
    			
        		// other conditions
    			else if (amenityId > 0 && ValidationUtil.hasValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasValue(fromDate))
        		{
        			
        		}
        		else
        		{
        			// REQUESTOR ONLY
        			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorOnly(userId, houseId); 
        		}*/
        	}
    	}
    	
    	
    	
    	
    	
    	
    	return amenityBeanList;
    }

    
    
    /**
     * USED BY ADMIN SEARCH SERVICE REQUESTS - Gets all the services requested by a particular home owner, results are displayed in the ADMIN page
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ServiceBean> adminSearchUserServiceRequests(int userId, int houseId, int serviceId, String status, String fromDate, String toDate)
    {
    	logger.info("Retrieving service requests search results...");
    	System.out.println("Retrieving service requests search results...");
    	
    	List<ServiceBean> serviceBeanList = new ArrayList<ServiceBean>();
    	
    	logger.info("userId: " + userId);
    	logger.info("houseId: " + houseId);
    	logger.info("serviceId: " + serviceId);
    	
    	
    	// NO Requestor
    	if (userId == 100 && houseId == 100)
    	{
    		// #################################################  by SERVICE  ###########################################################################
    		// by SERVICE only
    		if (serviceId > 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by SERVICE only...");
    			logger.info("Searching by SERVICE only...");
    			serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByServiceOnly(serviceId);
    		}
    		// by FROM DATE only
    		else if (serviceId == 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by FROM DATE only...");
    			logger.info("Searching by FROM DATE only...");
    			serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByFromDateOnly(fromDate);
    		}
    		// by fromDate and toDate
    		else if (serviceId == 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasValue(toDate))
    		{
    			System.out.println("\nSearching by fromDate and toDate...");
    			logger.info("Searching by fromDate and toDate...");
    			serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByFromDateAndToDate(fromDate, toDate);
    		}
    		// by Service and Status
    		else if (serviceId > 0 && ValidationUtil.hasValue(status) && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by SERVICE and STATUS...");
    			logger.info("Searching by SERVICE and STATUS...");
    			serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByServiceAndStatus(serviceId, status);
    		}
    		// #################################################  by STATUS  ###########################################################################################
    		// by STATUS only
    		else if (ValidationUtil.hasValue(status) && serviceId == 0 && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching by STATUS only...");
    			logger.info("Searching by STATUS only...");
    			serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByStatusOnly(status);
    		}
    	
    	
    		// SEARCH FOR ALL
    		else if (serviceId == 0 && ValidationUtil.hasNoValue(status)
        			&& ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(toDate))
    		{
    			System.out.println("\nSearching ALL service requests...");	// OK! (check the status muna)
    			logger.info("Searching ALL service requests...");
    			serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsAll();  // ADD FILTER FOR SERVICE REQUEST
        	}
    	}
    	else
    	{
    		// WITH REQUESTOR already
    		if (userId > 0 && houseId > 0)
        	{
    			if (serviceId > 0 && ValidationUtil.hasNoValue(status))
    			{
    				// by REQUESTOR and SERVICE
    				System.out.println("Searching using REQUESTOR and SERVICE...");
    				logger.info("Searching using REQUESTOR and SERVICE...");
    				serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByRequestorAndService(userId, houseId, serviceId);
    			}
    			else if (serviceId == 0 && ValidationUtil.hasValue(status))
    			{
    				// by REQUESTOR and STATUS
    				System.out.println("Searching using REQUESTOR and STATUS...");
    				logger.info("Searching using REQUESTOR and STATUS...");
    				serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByRequestorAndStatus(userId, houseId, status);
    			}
    			else if (serviceId > 0 && ValidationUtil.hasValue(status))		// RACS
    			{
    				// by REQUESTOR, SERVICE and STATUS
    				System.out.println("Searching using REQUESTOR, SERVICE and STATUS...");
    				logger.info("Searching using REQUESTOR, SERVICE and STATUS...");
    				serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByRequestorServiceAndStatus(userId, houseId, serviceId, status);
    			}
    			else if (serviceId == 0 && ValidationUtil.hasNoValue(status) && ValidationUtil.hasNoValue(fromDate) && ValidationUtil.hasNoValue(fromDate))
    			{
    				// REQUESTOR ONLY
    				System.out.println("Searching using REQUESTOR only...");	// TODO (Lumalabas lahat ng status dito even Paid)
    				logger.info("Searching using REQUESTOR only...");
    				serviceBeanList = searchRequestsDaoImpl.searchServiceRequestsByRequestorOnly(userId, houseId);
    			}
    			
    			
    			
    			
    			/*if (amenityId > 0)
    			{
    				logger.info("Searching using REQUESTOR and AMENITY...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAndAmenity(userId, houseId, amenityId);
    			}
    			// by REQUESTOR and STATUS
    			else if (ValidationUtil.hasValue(status))
    			{
    				logger.info("Searching using REQUESTOR and STATUS...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAndStatus(userId, houseId, status);
    			}
    			// by REQUESTOR, AMENITY and STATUS
    			else if (amenityId > 0 && ValidationUtil.hasValue(status))
    			{
    				logger.info("Searching using REQUESTOR, AMENITY and STATUS...");
    				amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorAmenityAndStatus(userId, houseId, amenityId, status);
    			}
    			
    			
        		// other conditions
    			else if (amenityId > 0 && ValidationUtil.hasValue(status) && ValidationUtil.hasValue(fromDate) && ValidationUtil.hasValue(fromDate))
        		{
        			
        		}
        		else
        		{
        			// REQUESTOR ONLY
        			amenityBeanList = searchRequestsDaoImpl.searchAmenityRequestsByRequestorOnly(userId, houseId); 
        		}*/
        	}
    	}
    	
    	
    	return serviceBeanList;
    }
    
    
    
    /**
     * @param searchRequestsDaoImpl the searchRequestsDaoImpl to set
     */
    @Autowired
    public void setSearchRequestsDaoImpl(SearchRequestsDaoImpl searchRequestsDaoImpl) {
        this.searchRequestsDaoImpl = searchRequestsDaoImpl;
    }
}

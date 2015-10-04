/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.AmenityBean;
import com.homeportal.dao.impl.AmenitiesRequestDaoImpl;
import com.homeportal.model.Adjustment;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.util.AmenitiesRequestUtil;
import com.homeportal.util.ConstantsUtil;

/**
 *
 * @author PSP36488
 */
@Service
public class AmenitiesRequestService
{
	private static Logger logger = Logger.getLogger(AmenitiesRequestService.class);

    private AmenitiesRequestDaoImpl amenitiesRequestDaoImpl;
    private AmenitiesRequestUtil amenitiesRequestUtil;
    
    
    /**
     * Create new Amenity request
     *
     * @param 
     * @return 
     */
    @Transactional
    public AmenitiesRequest createAmenityRequest(int userId, int houseId, int amenityId, String requestedDate, String startTime, 
    		String endTime, int quantity)
    {
    	AmenitiesRequest ar = new AmenitiesRequest();
    	ar.setUserId(userId);
    	ar.setHouseId(houseId);
    	ar.setAmenityId(amenityId);
    	ar.setRequestedDate(requestedDate);
    	ar.setStartTime(startTime);
    	ar.setEndTime(endTime);
    	ar.setQuantity(1);
    	
    	if (quantity > 0)
    	{
    		ar.setQuantity(quantity);
    	}
    	
    	// manually set values
    	ar.setStatus(ConstantsUtil.NEW_REQUEST);
    	ar.setRemarks(ConstantsUtil.EMPTY_STRING);
    	ar.setConfirmedDate(ConstantsUtil.EMPTY_STRING);
    	ar.setBasicCost(new Double("0.00"));
    	ar.setAdditionalCost(new Double("0.00"));
    	ar.setOtherCharges(new Double("0.00"));
    	ar.setBilled(0);
    	ar.setCreatedAt(new Date());
    	ar.setUpdatedAt(new Date());
    	
    	try
    	{
    		amenitiesRequestDaoImpl.saveAmenitiesRequest(ar);
    	}
    	catch(SQLException ex)
    	{
    		logger.error("Error encountered in saving amenity request. " + ex.getMessage());
    		throw new HibernateException("Error encountered in amenity service request. ", ex);
    	}
    	
    	return ar;
    }
    
    
    /**
     * Get amenity requests by STATUS (used by admin home in loading NEW amenity requests)
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AmenityBean> getAmenityRequestsByStatus(List<String> statusList) 
    {
        return amenitiesRequestDaoImpl.getAmenityRequestsByStatus(statusList);
    }
    
    
    
  
    
    
    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AmenityBean> getAmenityList(int userId, int houseId) {
        return amenitiesRequestDaoImpl.getAmenitiesRequests(userId, houseId);
    }
    
    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AmenityBean> getAmenityList() {
        return amenitiesRequestDaoImpl.getAmenities_Requests();
    }
    
    

    /**
     * Get all amenities
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AmenityBean> getAmenityListbyAdmin(int userId, String status, String requestedDate, int amenityId) {
        return amenitiesRequestDaoImpl.getAmenities_RequestsByAdmin(userId,status, requestedDate, amenityId);
    }
    
    
    /**
     * Updates the paid amount of a specific Amenity request
     * */
    @Transactional
    public Double updatePaidAmount(Double paidAmount, int requestId, double amount) {
            return amenitiesRequestDaoImpl.updatePaidAmount(paidAmount, requestId, amount);
    }
    
    
    /**
	 * 	Updates the STATUS, total cost etc. of a specific Amenity request
	 * */
    /*@Transactional
    public boolean updateAmenityRequest(AmenitiesRequest ar) 
    {
    	try
    	{
    		// get the Adjustment object using the request id
        	// Adjustment adj = adjustmentDaoImpl.getAdjustmentById(requestId);
        	
        	if (adj != null)
        	{
        		adj.setPaidAmount(paidAmount);
        		adj.setUpdatedAt(new Date());
        	}
        	
    		amenitiesRequestDaoImpl.updateAmenityRequest(ar);
    		return true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("Error encountered in updating adjustment. " + ex.getMessage());
    		throw new HibernateException("Error encountered in updating adjustment. ", ex);
    	}
    }*/
    
    
    
    /**
     * Updates the STATUS, total cost etc. of a specific Amenity request
     * */
    @Transactional
    public AmenitiesRequest updateAmenityRequest(AmenitiesRequest ar)
    {
            return amenitiesRequestDaoImpl.updateAmenityRequest(ar);
    }

    
    /**
	 * Create new Amenity Request
	 * @param data - json data from request
	 * @return list of created amenity requests
	 */
	@Transactional
	public List<AmenitiesRequest> create(Object data) throws Exception
	{
		System.out.println("Creating request...");
		
        List<AmenitiesRequest> newAr = new ArrayList<AmenitiesRequest>();
		List<AmenitiesRequest> list = amenitiesRequestUtil.getAmenitiesRequestFromRequest(data);
		
		boolean existing = false;
		boolean overlappedSchedule = false;
		
		for (AmenitiesRequest ar : list)
		{
			// validate request
			existing = amenitiesRequestDaoImpl.checkIfExisting(ar);
			
			if (existing)
			{
				throw new Exception("The same time and date has already been reserved.");
			}
			else
			{
				overlappedSchedule = amenitiesRequestDaoImpl.checkIfOverlappedSchedule(ar);
				
				if (overlappedSchedule)
				{
					throw new Exception("The same time and date has already been reserved.");
				}
				
				newAr.add(amenitiesRequestDaoImpl.saveAmenitiesRequest(ar));
				
			}
		}

		return newAr;
	}

        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<AmenitiesRequest> update(Object data){

		List<AmenitiesRequest> returnAr = new ArrayList<AmenitiesRequest>();
		List<AmenitiesRequest> updatedAr = amenitiesRequestUtil.getAmenitiesRequestFromRequest(data);
		for (AmenitiesRequest ar : updatedAr){
			returnAr.add(amenitiesRequestDaoImpl.updateAmenities_Request(ar));
		}
		return returnAr;
	}

        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<AmenitiesRequest> updateViaPortal(Object data, String uom){

		List<AmenitiesRequest> returnAr = new ArrayList<AmenitiesRequest>();
		List<AmenitiesRequest> updatedAr = amenitiesRequestUtil.getAmenitiesRequestFromRequest(data);
		for (AmenitiesRequest ar :updatedAr){
			returnAr.add(amenitiesRequestDaoImpl.updateAmenities_RequestViaPortal(ar,uom));
		}
		return returnAr;
	}
        
    
	/**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<AmenitiesRequest> updateViaAdmin(Object data){

		List<AmenitiesRequest> returnAr = new ArrayList<AmenitiesRequest>();
		List<AmenitiesRequest> updatedAr = amenitiesRequestUtil.getAmenitiesRequestFromRequest(data);
		for (AmenitiesRequest ar :updatedAr){
			returnAr.add(amenitiesRequestDaoImpl.updateAmenities_RequestViaAdmin(ar));
		}
		return returnAr;
	}
	
	
	 /**
     * Get an amenity request by its ID
     * */
    @Transactional
    public AmenitiesRequest getAmenityRequestById(int amenityId) throws SQLException
    {
    	return amenitiesRequestDaoImpl.getAmenityRequestById(amenityId);
    }
	
	
        
         /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	//@Transactional
	public String getUom(Object data){

		String  uom = "";
		List<AmenityBean> updatedAr = amenitiesRequestUtil.getAmenitiesBeanFromRequest(data);
		          for (AmenityBean ab: updatedAr) {
                          uom = ab.getUom();
                           }
		return uom;
	}
        
        
        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	//@Transactional
	public int getHouseId(Object data){

		int houseId = 0;
		List<AmenityBean> updatedAr = amenitiesRequestUtil.getAmenitiesBeanFromRequest(data);
		          for (AmenityBean ab: updatedAr) {
                          houseId = ab.getHouseId();
                           }
		return houseId;
	}
        
        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	//@Transactional
	public int getAmenityId(Object data){

		int amenityId = 0;
		List<AmenityBean> updatedAr = amenitiesRequestUtil.getAmenitiesBeanFromRequest(data);
		          for (AmenityBean ab: updatedAr) {
                          amenityId = ab.getAmenityId();
                           }
		return amenityId;
	}
        
        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	//@Transactional
	public double getValuePerAmenity(Object data){

		double value = 0.0;
		List<AmenityBean> updatedAr = amenitiesRequestUtil.getAmenitiesBeanFromRequest(data);
		          for (AmenityBean ab: updatedAr) {
                          value = ab.getBasic_cost();
                           }
		return value;
	}
	
	
	
	/**
	 * Used to validate date and time overlap during creation of Amenity Requests
	 * 
	 * @return boolean true means there is an overlap, false means no overlap
	 * */
	@Transactional(readOnly = true)
	public boolean validateDateAndTimeOverlap(int amenityId, String date, String startTime, String endTime)
	{
		return amenitiesRequestDaoImpl.validateDateAndTimeOverlap(amenityId, date, startTime, endTime);
	}
	
	
	/**
     * computeAmenityFee
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Double computeAmenityFee(int amenityId, int hoursOrPcs)
    {
        return amenitiesRequestDaoImpl.computeAmenityFee(amenityId, hoursOrPcs);
    }
	
	

    /**
     * @param amenitiesRequestDaoImpl the amenitiesRequestDaoImpl to set
     */
    @Autowired
    public void setAmenitiesRequestDaoImpl(AmenitiesRequestDaoImpl amenitiesRequestDaoImpl) {
        this.amenitiesRequestDaoImpl = amenitiesRequestDaoImpl;
    }

    /**
     * @param amenitiesRequestUtil the amenitiesRequestUtil to set
     */
    @Autowired
    public void setAmenitiesRequestUtil(AmenitiesRequestUtil amenitiesRequestUtil) {
        this.amenitiesRequestUtil = amenitiesRequestUtil;
    }
}

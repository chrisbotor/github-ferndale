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

import com.homeportal.bean.ServiceBean;
import com.homeportal.dao.impl.ServiceRequestDaoImpl;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.ServiceRequest;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.ServiceRequestUtil;

/**
 *
 * @author Racs
 */
@Service
public class ServiceRequestService 
{
	private static Logger logger = Logger.getLogger(ServiceRequestService.class);

    private ServiceRequestDaoImpl serviceRequestDaoImpl;
    private ServiceRequestUtil serviceRequestUtil;
    
    
    /**
     * Create new Service request
     *
     * @param 
     * @return 
     */
    @Transactional
    public ServiceRequest createServiceRequest(int userId, int houseId, int serviceId, String requestedDate, int quantity)
    {
    	ServiceRequest sr = new ServiceRequest();
    	sr.setUserId(userId);
    	sr.setHouseId(houseId);
    	sr.setServiceId(serviceId);
    	sr.setPreferredDate(requestedDate);
    	sr.setQuantity(1);
    	
    	if (quantity > 0)
    	{
    		sr.setQuantity(quantity);
    	}
    	
    	// manually set values
    	sr.setStatus(ConstantsUtil.NEW_REQUEST);
    	sr.setRemarks(ConstantsUtil.EMPTY_STRING);
    	sr.setConfirmedDate(ConstantsUtil.EMPTY_STRING);
    	sr.setPreferredTime(ConstantsUtil.EMPTY_STRING);
    	sr.setMiscNames(ConstantsUtil.EMPTY_STRING);
    	sr.setPayeeName(ConstantsUtil.EMPTY_STRING);
    	sr.setBasicCost(new Double("0.00"));
    	sr.setAdditionalCost(new Double("0.00"));
    	sr.setOtherCharges(new Double("0.00"));
    	sr.setBilled(0);
    	sr.setCreatedAt(new Date());
    	sr.setUpdatedAt(new Date());
    	
    	try
    	{
    		serviceRequestDaoImpl.saveServiceRequest(sr);
    	}
    	catch(SQLException ex)
    	{
    		logger.error("Error encountered in saving service request. " + ex.getMessage());
    		throw new HibernateException("Error encountered in saving service request. ", ex);
    	}
    	
    	return sr;
    }
    
    
    /**
     * Get amenity requests by STATUS (used by admin home in loading NEW amenity requests)
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ServiceBean> getServiceRequestsByStatus(List<String> statusList) 
    {
        return serviceRequestDaoImpl.getServiceRequestsByStatus(statusList);
    }
    
    

    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ServiceBean> getServiceRequestList(int id, int houseId) {
        return serviceRequestDaoImpl.getService_Requests(id, houseId);
    }
    
     /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ServiceBean> getServiceRequestList()
    {
        return serviceRequestDaoImpl.getService_Requests();
    }

    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ServiceBean> getServiceRequestList(int userId, String status, String requestedDate, int serviceId) {
        return serviceRequestDaoImpl.getService_RequestsByAdmin(userId, status, requestedDate, serviceId);
    }
    
    @Transactional
    public Double updatePaidAmount(Double paidAmount, int requestId, double amount) {
            return serviceRequestDaoImpl.updatePaidAmount(paidAmount, requestId, amount);
     }
    

    @Transactional
    public Double updatePaidAmountAdjustments(Double paidAmount, int requestId, double amount) {
            return serviceRequestDaoImpl.updatePaidAmountAdjustments(paidAmount, requestId, amount);
     }

    
    /**
	 * Create new Service Request
	 * @param data - json data from request
	 * @return list of created service requests
	 */
	@Transactional
	public List<ServiceRequest> create(Object data) throws Exception
	{
        List<ServiceRequest> newSr = new ArrayList<ServiceRequest>();
        
		List<ServiceRequest> list = serviceRequestUtil.getServiceRequestFromRequest(data);
		
		boolean existing = false;
		
		for (ServiceRequest sr : list)
		{
			// validate request
			existing = serviceRequestDaoImpl.checkIfExisting(sr);
			
			logger.info("Service request already existing? " + existing);
			
			if (existing)
			{
				logger.info("The time and date have already been reserved by another home owner.");
				throw new Exception("The time and date have already been reserved by another home owner.");
			}
			else
			{
				newSr.add(serviceRequestDaoImpl.saveServiceRequest(sr));
			}
		}

		return newSr;
	}

        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<ServiceRequest> update(Object data){

		List<ServiceRequest> returnAr = new ArrayList<ServiceRequest>();
		List<ServiceRequest> updatedAr = serviceRequestUtil.getServiceRequestFromRequest(data);
		for (ServiceRequest ar : updatedAr){
			returnAr.add(serviceRequestDaoImpl.updateService_Request(ar));
		}
		return returnAr;
	}

        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<ServiceRequest> updateViaPortal(Object data,String uom){

		List<ServiceRequest> returnAr = new ArrayList<ServiceRequest>();
		List<ServiceRequest> updatedAr = serviceRequestUtil.getServiceRequestFromRequest(data);
		for (ServiceRequest ar : updatedAr){
			returnAr.add(serviceRequestDaoImpl.updateService_RequestViaPortal(ar,uom));
		}
		return returnAr;
	}

        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<ServiceRequest> updateViaAdmin(Object data){

		List<ServiceRequest> returnAr = new ArrayList<ServiceRequest>();
		List<ServiceRequest> updatedAr = serviceRequestUtil.getServiceRequestFromRequest(data);
		for (ServiceRequest ar : updatedAr){
			returnAr.add(serviceRequestDaoImpl.updateService_RequestViaAdmin(ar));
		}
		return returnAr;
	}
        
         /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	//@Transactional
	public String getUom(Object data){

		String  uom = "";
		List<ServiceBean> updatedAr = serviceRequestUtil.getServiceBeanFromRequest(data);
		          for (ServiceBean ab: updatedAr) {
                          uom = ab.getUom();
                           }
		return uom;
	}
	
	
	/**
     * Get an service request by its ID
     * */
    @Transactional
    public ServiceRequest getServiceRequestById(int serviceId) throws SQLException
    {
    	return serviceRequestDaoImpl.getServiceRequestById(serviceId);
    }
	
    /**
     * Updates the STATUS, total cost etc. of a specific Amenity request
     * */
    @Transactional
    public ServiceRequest updateServiceRequest(ServiceRequest sr)
    {
            return serviceRequestDaoImpl.updateServiceRequest(sr);
    }
    

    /**
     * @param serviceRequestDaoImpl the serviceRequestDaoImpl to set
     */
    @Autowired
    public void setServiceRequestDaoImpl(ServiceRequestDaoImpl serviceRequestDaoImpl) {
        this.serviceRequestDaoImpl = serviceRequestDaoImpl;
    }

    /**
     * @param serviceRequestUtil the serviceRequestUtil to set
     */
    @Autowired
    public void setServiceRequestUtil(ServiceRequestUtil serviceRequestUtil) {
        this.serviceRequestUtil = serviceRequestUtil;
    }
}


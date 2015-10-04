/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import com.homeportal.dao.impl.ServiceDaoImpl;
import com.homeportal.model.Services;
import com.homeportal.util.ServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PSP36488
 */
@Service
public class ServiceService {

    private ServiceDaoImpl servicesDaoImpl;
    private ServiceUtil servicesUtil;

    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Services> getServicesList() {
        return servicesDaoImpl.getServices();
    }
    
    /**
     * Create new Owner/Owners
     *
     * @param data - json data from request
     * @return created contacts
     */
    @Transactional
    public List<Services> create(Object data) {

        List<Services> newServices = new ArrayList<Services>();
        List<Services> list = servicesUtil.getServicesFromRequest(data);
        for (Services v : list) {
            v.setCreatedAt(new Date());
            v.setUpdatedAt(new Date());
            newServices.add(servicesDaoImpl.saveServices(v));
        }

        return newServices;
    }

    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<Services> update(Object data) {

        List<Services> returnServicess = new ArrayList<Services>();
        List<Services> updatedServicess = servicesUtil.getServicesFromRequest(data);
        for (Services v : updatedServicess) {
            v.setCreatedAt(new Date());
            v.setUpdatedAt(new Date());
            returnServicess.add(servicesDaoImpl.updateServices(v));
        }
        return returnServicess;
    }

    /**
     * Delete contact/contacts
     *
     * @param data - json data from request
     */
    @Transactional
    public void delete(Object data)
    {

        //it is an array - have to cast to array object
        if (data.toString().indexOf('[') > -1) {
            List<Integer> deleteOwners = servicesUtil.getListIdFromJSON(data);
            for (Integer id : deleteOwners) {
                servicesDaoImpl.deleteServices(id);
            }

        } else { //it is only one object - cast to object/bean
            Integer id = Integer.parseInt(data.toString());
            servicesDaoImpl.deleteServices(id);
        }
    }
    
    
    

	/**
	 * Used to validate service request and date during creation of Service Requests
	 * 
	 * @return boolean true means there is an overlap, false means no overlap
	 * */
	@Transactional(readOnly = true)
	public boolean ValidateServiceRequestAndDateOverlap(int serviceReqIdParam, String formattedDate)
	{
		return  servicesDaoImpl.validateServiceRequestAndDateOverlap(serviceReqIdParam, formattedDate);
	}
    
    

    /**
     * @param servicesDaoImpl the servicesDaoImpl to set
     */
    @Autowired
    public void setServicesDaoImpl(ServiceDaoImpl servicesDaoImpl) {
        this.servicesDaoImpl = servicesDaoImpl;
    }

    /**
     * @param servicesUtil the servicesUtil to set
     */
    @Autowired
    public void setServicesUtil(ServiceUtil servicesUtil) {
        this.servicesUtil = servicesUtil;
    }

}

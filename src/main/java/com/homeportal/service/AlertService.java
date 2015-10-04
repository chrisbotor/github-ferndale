package com.homeportal.service;


import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.EmployeeBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.bean.VehicleBean;
import com.homeportal.dao.impl.AlertDaoImpl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.model.Occupant;


@Service
public class AlertService {
	
	private AlertDaoImpl alertDaoImpl;
	
	/**
	 * @return the list of new amenity requests
	 * 
	 */
	@Transactional(readOnly=true)
	public List<AmenityBean> getNewAmenityRequests(){
		System.out.println("@AlertService: Getting new amenity requests...");
		
		List<AmenityBean> newAmenityRequests = new ArrayList<AmenityBean>();
		newAmenityRequests = alertDaoImpl.getNewAmenityRequests();
		
		return newAmenityRequests;
	}
	
	
	/**
	 * @return the list of new service requests
	 * 
	 */
	@Transactional(readOnly=true)
	public List<ServiceBean> getNewServiceRequests(){
		System.out.println("@AlertService: Getting new service requests...");
		
		List<ServiceBean> newServiceRequests = new ArrayList<ServiceBean>();
		newServiceRequests = alertDaoImpl.getNewServiceRequests();
		
		return newServiceRequests;
	}
	
	
	/**
	 * @return the list of newly added employees
	 * 
	 */
	@Transactional(readOnly=true)
	public List<EmployeeBean> getNewEmployees(){
		System.out.println("@AlertService: Getting new employees...");
		
		List<EmployeeBean> newEmployees = new ArrayList<EmployeeBean>();
		newEmployees = alertDaoImpl.getNewEmployees();
		
		return newEmployees;
	}
	
	
	/**
	 * @return the list of newly added occupants
	 * 
	 */
	@Transactional(readOnly=true)
	public List<Occupant> getNewOccupants(){
		System.out.println("@AlertService: Getting new occupants...");
		
		List<Occupant> newOccupants = new ArrayList<Occupant>();
		newOccupants = alertDaoImpl.getNewOccupants();
		
		return newOccupants;
	}
	
	
	/**
	 * @return the list of newly added vehicles
	 * 
	 */
	@Transactional(readOnly=true)
	public List<VehicleBean> getNewVehicles(){
		System.out.println("@AlertService: Getting new vehicles...");
		
		List<VehicleBean> newVehicles = new ArrayList<VehicleBean>();
		newVehicles = alertDaoImpl.getNewVehicles();		
		return newVehicles;
	}
	
	
	
	/**
     * @param alertDaoImpl the alertDaoImpl to set
     */
    @Autowired
    public void setAlertDaoImpl(AlertDaoImpl alertDaoImpl) {
        this.alertDaoImpl = alertDaoImpl;
    }

}

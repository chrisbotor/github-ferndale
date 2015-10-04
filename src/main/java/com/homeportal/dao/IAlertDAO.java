/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;


import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.EmployeeBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.bean.VehicleBean;
import java.util.List;
import com.homeportal.model.Occupant;


public interface IAlertDAO {
	
		public List<AmenityBean> getNewAmenityRequests();
		public List<ServiceBean> getNewServiceRequests();
		public List<EmployeeBean> getNewEmployees();
		public List<Occupant> getNewOccupants();
		List<VehicleBean> getNewVehicles();
}

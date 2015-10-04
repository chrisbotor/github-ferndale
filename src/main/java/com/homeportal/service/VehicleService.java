/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.bean.VehicleBean;
import com.homeportal.dao.impl.VehicleDaoImpl;
import com.homeportal.model.Vehicle;
import com.homeportal.util.VehicleUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class VehicleService {
    
    private VehicleDaoImpl vehicleDaoImpl;
    private VehicleUtil vehicleUtil;
    
    /**
	 * Get all contacts
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<VehicleBean>  getVehiclesList(){
            return vehicleDaoImpl.getVehicles();
	}
    
    /**
	 * Get all contacts
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<VehicleBean>  getVehiclesList(int userId,String status){
            return vehicleDaoImpl.getVehicles(userId, status);
	}
        
        /**
	 * Get all contacts
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Vehicle>  getVehiclesList(int userId, int houseId){
            return vehicleDaoImpl.getVehicles(userId, houseId);
	}
        
        /**
	 * Create new Owner/Owners
	 * @param data - json data from request
	 * @return created contacts
	 */
	@Transactional
	public List<Vehicle> create(Object data){
		
        List<Vehicle> newVehicle = new ArrayList<Vehicle>();
		List<Vehicle> list = vehicleUtil.getVehiclesFromRequest(data);
		for (Vehicle v : list){
                        System.out.println("went to save Vehicle");
			newVehicle.add(vehicleDaoImpl.saveVehicles(v));
		}
		
		return newVehicle;
	}
        
        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<Vehicle> update(Object data){
		
		List<Vehicle> returnVehicles = new ArrayList<Vehicle>();
		List<Vehicle> updatedVehicles = vehicleUtil.getVehiclesFromRequest(data);
		for (Vehicle v : updatedVehicles){
			returnVehicles.add(vehicleDaoImpl.updateVehicle(v));
		}
		return returnVehicles;
	}
        
        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<Vehicle> updateViaPortal(Object data){
		
		List<Vehicle> returnVehicles = new ArrayList<Vehicle>();
		List<Vehicle> updatedVehicles = vehicleUtil.getVehiclesFromRequest(data);
		for (Vehicle v : updatedVehicles){
			returnVehicles.add(vehicleDaoImpl.updateVehicleViaPortal(v));
		}
		return returnVehicles;
	}
        
        /**
	 * Delete contact/contacts
	 * @param data - json data from request
	 */
	@Transactional
	public void delete(Object data){
		
		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			List<Integer> deleteOwners = vehicleUtil.getListIdFromJSON(data);
			for (Integer id : deleteOwners){
				vehicleDaoImpl.deleteVehicles(id);
			}
			
		} else { //it is only one object - cast to object/bean
			Integer id = Integer.parseInt(data.toString());
			vehicleDaoImpl.deleteVehicles(id);
		}
	}
    

    /**
     * @param vehicleDaoImpl the vehicleDaoImpl to set
     */
    @Autowired
    public void setVehicleDaoImpl(VehicleDaoImpl vehicleDaoImpl) {
        this.vehicleDaoImpl = vehicleDaoImpl;
    }

    /**
     * @param vehicleUtil the vehicleUtil to set
     */
    @Autowired
    public void setVehicleUtil(VehicleUtil vehicleUtil) {
        this.vehicleUtil = vehicleUtil;
    }
}

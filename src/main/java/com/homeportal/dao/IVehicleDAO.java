/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.bean.VehicleBean;
import com.homeportal.model.Vehicle;
import java.util.List;

/**
 *
 * @author Peter
 */
public interface IVehicleDAO {
    
    List<VehicleBean> getVehicles();
    List<VehicleBean> getVehicles(int userId,String status);
    List<Vehicle> getVehicles(int userId, int houseId);
    void deleteVehicles(int id);
    Vehicle saveVehicles(Vehicle vehicle);
    Vehicle updateVehicle(Vehicle vehicle);
    Vehicle updateVehicleViaPortal(Vehicle vehicle);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Peter
 */
@Component
public class VehicleUtil {
    
/**
	 * Get list of Vehicles from request.
	 * @param data - json data from request
	 * @return list of Vehicles
	 */
	public List<Vehicle> getVehiclesFromRequest(Object data){

		List<Vehicle> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListVehiclesFromJSON(data);

		} else { //it is only one object - cast to object/bean

			Vehicle vehicle = getVehicleFromJSON(data);

			list = new ArrayList<Vehicle>();
			list.add(vehicle);
		}

		return list;
	}

	/**
	 * Transform json data format into Vehicle object
	 * @param data - json data from request
	 * @return
	 */
	private Vehicle getVehicleFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		Vehicle newVehicle = (Vehicle) JSONObject.toBean(jsonObject, Vehicle.class);
		return newVehicle;
	}

	/**
	 * Transform json data format into list of Vehicle objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Vehicle> getListVehiclesFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Vehicle> newVehicles = (List<Vehicle>) JSONArray.toCollection(jsonArray,Vehicle.class);
		return newVehicles;
	}

	/**
	 * Tranform array of numbers in json data format into
	 * list of Integer
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getListIdFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Integer> idVehicles = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idVehicles;
	}

    
}


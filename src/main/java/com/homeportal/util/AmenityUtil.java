/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.util;

import com.homeportal.model.Amenity;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author PSP36488
 */
@Component
public class AmenityUtil {

    /**
	 * Get list of Amenitys from request.
	 * @param data - json data from request
	 * @return list of Amenitys
	 */
	public List<Amenity> getAmenitysFromRequest(Object data){

		List<Amenity> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListAmenitysFromJSON(data);

		} else { //it is only one object - cast to object/bean

			Amenity owner = getAmenityFromJSON(data);

			list = new ArrayList<Amenity>();
			list.add(owner);
		}

		return list;
	}

	/**
	 * Transform json data format into Amenity object
	 * @param data - json data from request
	 * @return
	 */
	private Amenity getAmenityFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		Amenity newAmenity = (Amenity) JSONObject.toBean(jsonObject, Amenity.class);
		return newAmenity;
	}

	/**
	 * Transform json data format into list of Amenity objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Amenity> getListAmenitysFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Amenity> newAmenitys = (List<Amenity>) JSONArray.toCollection(jsonArray,Amenity.class);
		return newAmenitys;
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
		List<Integer> idAmenitys = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idAmenitys;
	}

}

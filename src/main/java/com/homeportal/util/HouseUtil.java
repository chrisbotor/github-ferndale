/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.model.House;
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
public class HouseUtil {

     /**
	 * Get list of Houses from request.
	 * @param data - json data from request
	 * @return list of Houses
	 */
	public List<House> getHousesFromRequest(Object data){

		List<House> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListHousesFromJSON(data);

		} else { //it is only one object - cast to object/bean

			House House = getHouseFromJSON(data);

			list = new ArrayList<House>();
			list.add(House);
		}

		return list;
	}

	/**
	 * Transform json data format into House object
	 * @param data - json data from request
	 * @return
	 */
	private House getHouseFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		House newHouse = (House) JSONObject.toBean(jsonObject, House.class);
		return newHouse;
	}

	/**
	 * Transform json data format into list of House objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<House> getListHousesFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<House> newHouses = (List<House>) JSONArray.toCollection(jsonArray,House.class);
		return newHouses;
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
		List<Integer> idHouses = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idHouses;
	}

    
}

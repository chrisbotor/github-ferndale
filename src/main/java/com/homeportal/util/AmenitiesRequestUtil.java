/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.homeportal.bean.AmenityBean;
import com.homeportal.model.AmenitiesRequest;

/**
 *
 * @author PSP36488
 */
@Component
public class AmenitiesRequestUtil 
{
 /**
	 * Get list of Amenitys from request.
	 * @param data - json data from request
	 * @return list of Amenitys
	 */
	public List<AmenitiesRequest> getAmenitiesRequestFromRequest(Object data){

		List<AmenitiesRequest> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListAmenitiesRequestFromJSON(data);

		} else { //it is only one object - cast to object/bean

			AmenitiesRequest ar = getAmenitiesRequestFromJSON(data);

			list = new ArrayList<AmenitiesRequest>();
			list.add(ar);
		}

		return list;
	}
       /**
        * Get list of Amenitys from request.
	 * @param data - json data from request
	 * @return list of Amenitys
	 */
	public List<AmenityBean> getAmenitiesBeanFromRequest(Object data){
		List<AmenityBean> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			list = getListAmenityBeanFromJSON(data);

		} else { //it is only one object - cast to object/bean
			AmenityBean ar = (AmenityBean) getAmenityBeanFromJSON(data);
			list = new ArrayList<AmenityBean>();
			list.add(ar);
		}

		return list;
	}

	/**
	 * Transform json data format into Amenity object
	 * @param data - json data from request
	 * @return
	 */
	private AmenitiesRequest getAmenitiesRequestFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		AmenitiesRequest newAmenity = (AmenitiesRequest) JSONObject.toBean(jsonObject, AmenitiesRequest.class);
		return newAmenity;
	}
        
        /**
	 * Transform json data format into Amenity object
	 * @param data - json data from request
	 * @return
	 */
	private AmenityBean getAmenityBeanFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		AmenityBean newAmenity = (AmenityBean) JSONObject.toBean(jsonObject, AmenityBean.class);
		return newAmenity;
	}

	/**
	 * Transform json data format into list of Amenity objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<AmenitiesRequest> getListAmenitiesRequestFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<AmenitiesRequest> newAmenitys = (List<AmenitiesRequest>) JSONArray.toCollection(jsonArray,AmenitiesRequest.class);
		return newAmenitys;
	}

        /**
	 * Transform json data format into list of Amenity objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<AmenityBean> getListAmenityBeanFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<AmenityBean> newAmenitys = (List<AmenityBean>) JSONArray.toCollection(jsonArray,AmenityBean.class);
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
	
	

	/**
	 * Formats time to military time format
	 * @param timeString 
	 * @return formattedTime
	 */
	public static String formatTime(String timeString)
	{
		System.out.println("Formatting time " + timeString + "...");
		
		String formattedTime = null;
		
		Map<String, String> timeMap = new HashMap<String, String>();
		
		timeMap.put("12:00 AM", "0000");
		timeMap.put("1:00 AM", "0100");
		timeMap.put("2:00 AM", "0200");
		timeMap.put("3:00 AM", "0300");
		timeMap.put("4:00 AM", "0400");
		timeMap.put("5:00 AM", "0500");
		timeMap.put("6:00 AM", "0600");
		timeMap.put("7:00 AM", "0700");
		timeMap.put("8:00 AM", "0800");
		timeMap.put("9:00 AM", "0900");
		timeMap.put("10:00 AM", "1000");
		timeMap.put("11:00 AM", "1100");
		timeMap.put("12:00 PM", "1200");
		timeMap.put("1:00 PM", "1300");
		timeMap.put("2:00 PM", "1400");
		timeMap.put("3:00 PM", "1500");
		timeMap.put("4:00 PM", "1600");
		timeMap.put("5:00 PM", "1700");
		timeMap.put("6:00 PM", "1800");
		timeMap.put("7:00 PM", "1900");
		timeMap.put("8:00 PM", "2000");
		timeMap.put("9:00 PM", "2100");
		timeMap.put("10:00 PM", "2200");
		timeMap.put("11:00 PM", "2300");
		
		
		if (timeString != null)
		{
			if (timeMap.containsKey(timeString))
			{
				formattedTime = timeMap.get(timeString);
				
				return formattedTime;
			}
		}
		
		return timeString;
		
	}
	
	

}

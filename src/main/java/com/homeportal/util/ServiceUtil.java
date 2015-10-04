/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.util;

import com.homeportal.model.Services;
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
public class ServiceUtil {

    /**
	 * Get list of Services from request.
	 * @param data - json data from request
	 * @return list of Services
	 */
	public List<Services> getServicesFromRequest(Object data){

		List<Services> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListServicesFromJSON(data);

		} else { //it is only one object - cast to object/bean

			Services service = getServiceFromJSON(data);

			list = new ArrayList<Services>();
			list.add(service);
		}

		return list;
	}

	/**
	 * Transform json data format into Services object
	 * @param data - json data from request
	 * @return
	 */
	private Services getServiceFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		Services newService = (Services) JSONObject.toBean(jsonObject, Services.class);
		return newService;
	}

	/**
	 * Transform json data format into list of Services objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Services> getListServicesFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Services> newServices = (List<Services>) JSONArray.toCollection(jsonArray,Services.class);
		return newServices;
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
		List<Integer> idServices = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idServices;
	}

}

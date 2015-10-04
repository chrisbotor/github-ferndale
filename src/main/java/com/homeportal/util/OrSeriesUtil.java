/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.model.OrSeries;
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
public class OrSeriesUtil {
    
    /**
	 * Get list of OrSeries from request.
	 * @param data - json data from request
	 * @return list ofOrSeries
	 */
	public List<OrSeries> getOrSeriessFromRequest(Object data){

		List<OrSeries> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListOrSeriessFromJSON(data);

		} else { //it is only one object - cast to object/bean

			OrSeries owner = getOrSeriesFromJSON(data);

			list = new ArrayList<OrSeries>();
			list.add(owner);
		}

		return list;
	}

	/**
	 * Transform json data format intoOrSeries object
	 * @param data - json data from request
	 * @return
	 */
	private OrSeries getOrSeriesFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		OrSeries newOrSeries = (OrSeries) JSONObject.toBean(jsonObject,OrSeries.class);
		return newOrSeries;
	}

	/**
	 * Transform json data format into list ofOrSeries objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<OrSeries> getListOrSeriessFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<OrSeries> newOrSeriess = (List<OrSeries>) JSONArray.toCollection(jsonArray,OrSeries.class);
		return newOrSeriess;
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
		List<Integer> idOrSeriess = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idOrSeriess;
	}

}

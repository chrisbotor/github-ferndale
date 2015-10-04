/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.model.Rates;
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
public class RatesUtil {
    
    /**
	 * Get list of Rates from request.
	 * @param data - json data from request
	 * @return list ofRates
	 */
	public List<Rates> getRatesFromRequest(Object data){

		List<Rates> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListRatesFromJSON(data);

		} else { //it is only one object - cast to object/bean

			Rates r = getRatesFromJSON(data);

			list = new ArrayList<Rates>();
			list.add(r);
		}

		return list;
	}

	/**
	 * Transform json data format intoRates object
	 * @param data - json data from request
	 * @return
	 */
	private Rates getRatesFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		Rates newRates = (Rates) JSONObject.toBean(jsonObject,Rates.class);
		return newRates;
	}

	/**
	 * Transform json data format into list ofRates objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Rates> getListRatesFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Rates> newRates = (List<Rates>) JSONArray.toCollection(jsonArray,Rates.class);
		return newRates;
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
		List<Integer> idRates = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idRates;
	}
}

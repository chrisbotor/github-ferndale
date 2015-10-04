/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.model.Occupant;
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
public class OccupantUtil {
 
    
	public List<Occupant> getOccupantsFromRequest(Object data){

		List<Occupant> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListOccupantsFromJSON(data);

		} else { //it is only one object - cast to object/bean

			Occupant occupant = getOccupantFromJSON(data);

			list = new ArrayList<Occupant>();
			list.add(occupant);
		}

		return list;
	}

	/**
	 * Transform json data format into Occupant object
	 * @param data - json data from request
	 * @return
	 */
	private Occupant getOccupantFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		Occupant newOccupant = (Occupant) JSONObject.toBean(jsonObject, Occupant.class);
		return newOccupant;
	}

	/**
	 * Transform json data format into list of Occupant objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Occupant> getListOccupantsFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Occupant> newOccupants = (List<Occupant>) JSONArray.toCollection(jsonArray,Occupant.class);
		return newOccupants;
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
		List<Integer> idOccupants = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idOccupants;
	}

    
}



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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeportal.model.Owner;
import com.homeportal.service.OwnerService;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
@Component
public class OwnerUtil 
{
	private static Logger logger = Logger.getLogger(OwnerUtil.class);
	private static OwnerService ownerService;
	
	
	
    /**
	 * Get list of Owners from request.
	 * @param data - json data from request 
	 * @return list of Owners
	 */
	public List<Owner> getOwnersFromRequest(Object data){

		List<Owner> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListOwnersFromJSON(data);

		} else { //it is only one object - cast to object/bean

			Owner owner = getOwnerFromJSON(data);

			list = new ArrayList<Owner>();
			list.add(owner);
		}

		return list;
	}

	/**
	 * Transform json data format into Owner object
	 * @param data - json data from request
	 * @return 
	 */
	private Owner getOwnerFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		Owner newOwner = (Owner) JSONObject.toBean(jsonObject, Owner.class);
		return newOwner;
	}

	/**
	 * Transform json data format into list of Owner objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Owner> getListOwnersFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Owner> newOwners = (List<Owner>) JSONArray.toCollection(jsonArray,Owner.class);
		return newOwners;
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
		List<Integer> idOwners = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idOwners;
	}
    


	/*
	 * Get the co-owner based on the UserID
	 * */
	public static Map<String, String> getCoOwner(int userId)
	{
		logger.info("****** Getting Co-Owner details...");
		
		Map<String, String> coOwnerDetails = null;
		Owner owner = ownerService.getOwnerByUserId(userId);
		
		// get the co-owner details
		if (owner != null)
		{
			if (CommonUtil.hasValue(owner.getCoLastName()))
			{
				logger.info("Got co-owner last name: " + owner.getCoLastName());
				System.out.println("Got co-owner last name: " + owner.getCoLastName());
				
				coOwnerDetails = new HashMap<String, String>();
				
				if (ValidationUtil.hasValue(owner.getCoLastName()) && !(owner.getCoLastName().equalsIgnoreCase("null")))
				{
					coOwnerDetails.put("coLastName", owner.getCoLastName());
				}
				
				if (ValidationUtil.hasValue(owner.getCoFirstName()) && !(owner.getCoFirstName().equalsIgnoreCase("null")))
				{
					coOwnerDetails.put("coFirstName", owner.getCoFirstName());
				}
				
				if (ValidationUtil.hasValue(owner.getCoMiddleName()) && !(owner.getCoMiddleName().equalsIgnoreCase("null")))
				{
					coOwnerDetails.put("coMiddleName", owner.getCoMiddleName());
				}
			}
		}
		
		return coOwnerDetails;
	}
	
	
	
	// Auto-wired services
	/**
     * @param ownerService - the billing service used to get co-owner details
     */
    @Autowired
    public void setOwnerService(OwnerService ownerService) {
    	OwnerUtil.ownerService = ownerService;
    }

}

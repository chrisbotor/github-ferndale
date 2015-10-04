/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.util;

import com.homeportal.bean.ServiceBean;
import com.homeportal.model.ServiceRequest;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author PSP36488
 */
@Component
public class ServiceRequestUtil 
{
	private static Logger logger = Logger.getLogger(ServiceRequestUtil.class);
	
	private static final String FERNDALE_USERID = MessageBundle.getProperty("ferndale_userId");
	
	
	/**
	 * Get list of Amenitys from request.
	 * @param data - json data from request
	 * @return list of Amenitys
	 */
	public List<ServiceRequest> getServiceRequestFromRequest(Object data)
	{
		logger.info("Getting service request from the Request object...");

		List<ServiceRequest> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListServiceRequestFromJSON(data);

		} else { //it is only one object - cast to object/bean

			ServiceRequest ar = getServiceRequestFromJSON(data);
			
			logger.info("============== ECY ID: " + ar.getId());
			logger.info("============== ECY HOUSE: " + ar.getHouseId());

			list = new ArrayList<ServiceRequest>();
			list.add(ar);
		}

		return list;
	}
        
        /**
        * Get list of Amenitys from request.
	 * @param data - json data from request
	 * @return list of Amenitys
	 */
	public List<ServiceBean> getServiceBeanFromRequest(Object data){
		List<ServiceBean> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			list = getListServiceBeanFromJSON(data);

		} else { //it is only one object - cast to object/bean
			ServiceBean ar = (ServiceBean) getServiceBeanFromJSON(data);
			list = new ArrayList<ServiceBean>();
			list.add(ar);
		}

		return list;
	}
        
        /**
	 * Transform json data format into list of Amenity objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<ServiceBean> getListServiceBeanFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<ServiceBean> newAmenitys = (List<ServiceBean>) JSONArray.toCollection(jsonArray,ServiceBean.class);
                return newAmenitys;
	}

	/**
	 * Transform json data format into Amenity object
	 * @param data - json data from request
	 * @return
	 */
	private ServiceRequest getServiceRequestFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		ServiceRequest newAmenity = (ServiceRequest) JSONObject.toBean(jsonObject, ServiceRequest.class);
		return newAmenity;
	}
        
        /**
	 * Transform json data format into Amenity object
	 * @param data - json data from request
	 * @return
	 */
	private ServiceBean getServiceBeanFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		ServiceBean newAmenity = (ServiceBean) JSONObject.toBean(jsonObject, ServiceBean.class);
		return newAmenity;
	}

	/**
	 * Transform json data format into list of Amenity objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<ServiceRequest> getListServiceRequestFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<ServiceRequest> newAmenitys = (List<ServiceRequest>) JSONArray.toCollection(jsonArray,ServiceRequest.class);
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


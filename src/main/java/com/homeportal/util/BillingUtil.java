/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.bean.BillingBean;
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
public class BillingUtil {

     /**
        * Get list of BillingBean from request.
	 * @param data - json data from request
	 * @return list of BillingBean
	 */
	public List<BillingBean> getBillingBeanFromRequest(Object data){
		List<BillingBean> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			list = getListBillingBeanFromJSON(data);

		} else { //it is only one object - cast to object/bean
			BillingBean ar = (BillingBean) getBillingBeanFromJSON(data);
			list = new ArrayList<BillingBean>();
			list.add(ar);
		}

		return list;
	}

    /**
     * Transform json data format into list of Amenity objects
     *
     * @param data - json data from request
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<BillingBean> getListBillingBeanFromJSON(Object data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        List<BillingBean> newAmenitys = (List<BillingBean>) JSONArray.toCollection(jsonArray, BillingBean.class);
        return newAmenitys;
    }

    /**
     * Transform json data format into Amenity object
     *
     * @param data - json data from request
     * @return
     */
    private BillingBean getBillingBeanFromJSON(Object data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        BillingBean newAmenity = (BillingBean) JSONObject.toBean(jsonObject, BillingBean.class);
        return newAmenity;
    }
}

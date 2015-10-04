/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import com.homeportal.dao.impl.AmenityDaoImpl;
import com.homeportal.model.Amenity;
import com.homeportal.util.AmenityUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AmenityService {

    private AmenityDaoImpl amenityDaoImpl;
    private AmenityUtil amenityUtil;

    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Amenity> getAmenityList() {
        return amenityDaoImpl.getAmenities();
    }
    
    /**
     * Create new Owner/Owners
     *
     * @param data - json data from request
     * @return created contacts
     */
    @Transactional
    public List<Amenity> create(Object data) {

        List<Amenity> newAmenity = new ArrayList<Amenity>();
        List<Amenity> list = amenityUtil.getAmenitysFromRequest(data);
        for (Amenity v : list) {
            v.setCreatedAt(new Date());
            v.setUpdatedAt(new Date());
            newAmenity.add(amenityDaoImpl.saveAmenity(v));
        }

        return newAmenity;
    }

    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<Amenity> update(Object data) {

        List<Amenity> returnAmenitys = new ArrayList<Amenity>();
        List<Amenity> updatedAmenitys = amenityUtil.getAmenitysFromRequest(data);
        for (Amenity v : updatedAmenitys) {
             v.setCreatedAt(new Date());
            v.setUpdatedAt(new Date());
            returnAmenitys.add(amenityDaoImpl.updateAmenity(v));
        }
        return returnAmenitys;
    }

    /**
     * Delete contact/contacts
     *
     * @param data - json data from request
     */
    @Transactional
    public void delete(Object data) {

        //it is an array - have to cast to array object
        if (data.toString().indexOf('[') > -1) {
            List<Integer> deleteOwners = amenityUtil.getListIdFromJSON(data);
            for (Integer id : deleteOwners) {
                amenityDaoImpl.deleteAmenity(id);
            }

        } else { //it is only one object - cast to object/bean
            Integer id = Integer.parseInt(data.toString());
            amenityDaoImpl.deleteAmenity(id);
        }
    }

    /**
     * @param amenityDaoImpl the amenityDaoImpl to set
     */
    @Autowired
    public void setAmenityDaoImpl(AmenityDaoImpl amenityDaoImpl) {
        this.amenityDaoImpl = amenityDaoImpl;
    }

    /**
     * @param amenityUtil the amenityUtil to set
     */
    @Autowired
    public void setAmenityUtil(AmenityUtil amenityUtil) {
        this.amenityUtil = amenityUtil;
    }

}

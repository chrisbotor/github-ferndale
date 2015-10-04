/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.dao.impl.OccupantDaoImpl;
import com.homeportal.model.Occupant;
import com.homeportal.util.OccupantUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class OccupantService {
    
    private OccupantDaoImpl occupantDaoImpl;
    private OccupantUtil occupantUtil;
    
    /**
	 * Get all contacts
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Occupant>  getOccupantsList(){
            return occupantDaoImpl.getOccupants();
	}
        
        /**
	 * Create new Owner/Owners
	 * @param data - json data from request
	 * @return created contacts
	 */
	@Transactional
	public List<Occupant> create(Object data){
		
        List<Occupant> newOccupant = new ArrayList<Occupant>();
		List<Occupant> list = occupantUtil.getOccupantsFromRequest(data);
		for (Occupant v : list){
			newOccupant.add(occupantDaoImpl.saveOccupants(v));
		}
		
		return newOccupant;
	}
        
        /**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<Occupant> update(Object data){
		
		List<Occupant> returnOccupants = new ArrayList<Occupant>();
		List<Occupant> updatedOccupants = occupantUtil.getOccupantsFromRequest(data);
		for (Occupant v : updatedOccupants){
			returnOccupants.add(occupantDaoImpl.updateOccupant(v));
		}
		return returnOccupants;
	}
        
        /**
	 * Delete contact/contacts
	 * @param data - json data from request
	 */
	@Transactional
	public void delete(Object data){
		
		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			List<Integer> deleteOwners = occupantUtil.getListIdFromJSON(data);
			for (Integer id : deleteOwners){
				occupantDaoImpl.deleteOccupants(id);
			}
			
		} else { //it is only one object - cast to object/bean
			Integer id = Integer.parseInt(data.toString());
			occupantDaoImpl.deleteOccupants(id);
		}
	}

    /**
     * @param occupantDaoImpl the occupantDaoImpl to set
     */
    @Autowired
    public void setOccupantDaoImpl(OccupantDaoImpl occupantDaoImpl) {
        this.occupantDaoImpl = occupantDaoImpl;
    }

    /**
     * @param occupantUtil the occupantUtil to set
     */
    @Autowired
    public void setOccupantUtil(OccupantUtil occupantUtil) {
        this.occupantUtil = occupantUtil;
    }
    
}

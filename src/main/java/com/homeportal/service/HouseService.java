/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.HouseBean;
import com.homeportal.dao.impl.HouseDaoImpl;
import com.homeportal.model.House;
import com.homeportal.model.Owner;
import com.homeportal.util.HouseUtil;

/**
 *
 * @author PSP36488
 */
@Service
public class HouseService 
{
	private static Logger logger = Logger.getLogger(HouseService.class);
	
    private HouseDaoImpl houseDaoImpl;
    private HouseUtil houseUtil;
    
    
    /**
	 * Get all house for the Move in/move out
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<House> getAllHouse() throws SQLException
	{
		return houseDaoImpl.getAllHouse();
	}
	
	
	/**
     * Gets the list of house of a home owner
     * */
	@Transactional(readOnly=true)
	public List<House> getHouseListByOwnerId(int ownerId) throws SQLException
	{
		return houseDaoImpl.getHouseListByOwnerId(ownerId);
	}
    

    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<HouseBean> getHousesList() {
        return houseDaoImpl.getHouses();
    }

    /**
     * Create new House/Houses
     *
     * @param data - json data from request
     * @return created contacts
     */
    @Transactional
    public List<House> create(Object data) 
    {

        List<House> newHouses = new ArrayList<House>();
        List<House> list = houseUtil.getHousesFromRequest(data);
        for (House House : list) {
            newHouses.add(houseDaoImpl.saveHouses(House));
        }

        return newHouses;
    }

    @Transactional
    public House addHouse(House h) {
        return houseDaoImpl.saveHouses(h);
    }

    @Transactional
    public House updateHouseRented(House h) {
        return houseDaoImpl.updateRented(h);
    }

    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<House> update(Object data) {

        List<House> returnHouses = new ArrayList<House>();
        List<House> updatedHouses = houseUtil.getHousesFromRequest(data);
        for (House House : updatedHouses) {
            returnHouses.add(houseDaoImpl.updateHouse(House));
        }

        return returnHouses;
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
            List<Integer> deleteHouses = houseUtil.getListIdFromJSON(data);
            for (Integer id : deleteHouses) {
                houseDaoImpl.deleteHouses(id);
            }

        } else { //it is only one object - cast to object/bean
            Integer id = Integer.parseInt(data.toString());
            houseDaoImpl.deleteHouses(id);
        }
    }
    
 
 // ############################################################### NEW METHODS FOR ADMIN CREATE REQUEST ##################################################
    /**
	 * Get ahouse based on its house id
	 * @return house - the House
	 */
	@Transactional(readOnly=true)
	public House getHouseByHouseId(int houseId){
		logger.info("SERVICE getting the house using its houseId: " + houseId);
		System.out.println("SERVICE getting the house using its houseId: " + houseId);
		
		House house = null;
		
		if(houseId > 0){
			house = houseDaoImpl.getHouseByHouseId(houseId);
		}
		 
		return house;
	}

    /**
     * @param HouseDaoImpl the HouseDaoImpl to set
     */
    @Autowired
    public void setHouseDaoImpl(HouseDaoImpl houseDaoImpl) {
        this.houseDaoImpl = houseDaoImpl;
    }

    /**
     * @param HouseUtil the HouseUtil to set
     */
    @Autowired
    public void setHouseUtil(HouseUtil houseUtil) {
        this.houseUtil = houseUtil;
    }
}

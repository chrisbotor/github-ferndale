/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.bean.HouseBean;
import com.homeportal.model.House;
import java.util.List;

/**
 *
 * @author Peter
 */
public interface IHouseDAO {
    
    List<HouseBean> getHouses();
    void deleteHouses(int id);
    House saveHouses(House house);
    House updateHouse(House house);
    House updateRented(House house);
    
    public List<House> getHouseListByOwnerId(int ownerId);
	
	public House getHouseByHouseId(int houseId);
}

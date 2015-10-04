/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import java.sql.SQLException;
import java.util.List;
import com.homeportal.bean.OwnerBean;
import com.homeportal.model.Owner;

/**
 *
 * @author Peter
 */
public interface IOwnerDAO {
    
    List<Owner> getOwners() throws SQLException;
    
    List<OwnerBean> getFullname();
   
    void deleteOwners(int id);
    
    Owner saveOwners(Owner owner);
    
    Owner updateOwner(Owner owner);
    
    List<Owner> getOwner(int id);
    
    public Owner getOwnerByUserId(int userId);
	
	public Owner getOwnerByOwnerId(int ownerId);
	
	public Owner updateSingleOwner(Owner owner);
	
}

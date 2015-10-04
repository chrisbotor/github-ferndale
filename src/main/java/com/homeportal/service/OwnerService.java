/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.OwnerBean;
import com.homeportal.dao.impl.OwnerDaoImpl;
import com.homeportal.model.Adjustment;
import com.homeportal.model.Owner;
import com.homeportal.util.OwnerUtil;



/**
 *
 * @author Peter
 */
@Service
public class OwnerService
{
	private static Logger logger = Logger.getLogger(OwnerService.class);
	
    private OwnerDaoImpl ownerDaoImpl;
    private OwnerUtil ownerUtil;

    /**
	 * Get all contacts
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Owner>  getOwnersList() throws SQLException
	{
		return ownerDaoImpl.getOwners();
	}

	
	/**
	 * Gets all the owner for the Owner Search
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Owner> getAllOwner() throws SQLException
	{
		return ownerDaoImpl.getAllOwner();
	}
	
	
        /**
	 * Get all contacts
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<OwnerBean>  getFullName(){
            return ownerDaoImpl.getFullname();
	}
        
         /**
	 * Get all contacts
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Owner>  getOwner(int id){
            return ownerDaoImpl.getOwner(id);
	}
	
	/**
	 * Create new Owner/Owners
	 * @param data - json data from request
	 * @return created contacts
	 */
	@Transactional
	public List<Owner> create(Object data){
		
        List<Owner> newOwners = new ArrayList<Owner>();
		List<Owner> list = ownerUtil.getOwnersFromRequest(data);
		for (Owner owner : list){
			newOwners.add(ownerDaoImpl.saveOwners(owner));
		}
		
		return newOwners;
	}

         @Transactional
    public Owner addOwner(Owner owner) {
        return ownerDaoImpl.saveOwners(owner);
    }
        
        
         
         /*@Transactional
        public void updateOwner(Owner owner){
                ownerDaoImpl.updateOwner(owner);
        }*/
	
	
	/**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<Owner> update(Object data){
		
		List<Owner> returnOwners = new ArrayList<Owner>();
		List<Owner> updatedOwners = ownerUtil.getOwnersFromRequest(data);
		for (Owner owner : updatedOwners){
			returnOwners.add(ownerDaoImpl.updateOwner(owner));
		}
		
		return returnOwners;
	}
	
	
	/**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 * 
	 * SHOULD BE COMBINED WITH update method above!
	 */
	@Transactional
	public boolean updateOwner(Owner owner) 
    {
		System.out.println("SERVICE updating Owner...");
		
		try
    	{
    		ownerDaoImpl.update(owner);
    		return true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("Error encountered in updating adjustment. " + ex.getMessage());
    		throw new HibernateException("Error encountered in updating adjustment. ", ex);
    	}
	}
	
	
	
	/**
	 * Delete contact/contacts
	 * @param data - json data from request
	 */
	@Transactional
	public void delete(Object data){
		
		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			List<Integer> deleteOwners = ownerUtil.getListIdFromJSON(data);
			for (Integer id : deleteOwners){
				ownerDaoImpl.deleteOwners(id);
			}
			
		} else { //it is only one object - cast to object/bean
			Integer id = Integer.parseInt(data.toString());
			ownerDaoImpl.deleteOwners(id);
		}
	}

	
	
	
	/**
	 * Get an owner based on its owner id
	 * @return owner - the Owner
	 */
	@Transactional(readOnly=true)
	public Owner getOwnerByOwnerId(int ownerId){
		logger.info("Getting owner in the OwnerService...");
		
		Owner owner = null;
		
		if(ownerId > 0){
			owner = ownerDaoImpl.getOwnerByOwnerId(ownerId);
		}
		 
		return owner;
	}
	
	
	/**
	 * Get an owner based on its owner id
	 * @return owner - the Owner
	 */
	@Transactional
	public Owner updateSingleOwner(Owner owner)
	{
		logger.info("Updating owner with ID: " + owner.getId());
		
		ownerDaoImpl.updateSingleOwner(owner);
		
		return owner;
	}
	
	
	/**
	 * Get the co-owner of an owner based on its userId
	 * @return owner - the Owner
	 */
	@Transactional(readOnly=true)
	public Owner getOwnerByUserId(int userId){
		logger.info("Getting owner by its userId...");
		
		Owner owner = null;
		
		if(userId > 0)
		{
			owner = ownerDaoImpl.getOwnerByUserId(userId);
		}
		 
		return owner;
	}
	
	
	
	
    /**
     * @param ownerDaoImpl the ownerDaoImpl to set
     */
    @Autowired    
    public void setOwnerDaoImpl(OwnerDaoImpl ownerDaoImpl) {
        this.ownerDaoImpl = ownerDaoImpl;
    }

    /**
     * @param ownerUtil the ownerUtil to set
     */
    @Autowired
    public void setOwnerUtil(OwnerUtil ownerUtil) {
        this.ownerUtil = ownerUtil;
    }
	

	
}

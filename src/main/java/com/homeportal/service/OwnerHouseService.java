package com.homeportal.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.OwnerHouseBean;
import com.homeportal.dao.impl.OwnerDaoImpl;
import com.homeportal.dao.impl.OwnerHouseDaoImpl;


/**
*
* @author ranievas
*/
@Service
public class OwnerHouseService 
{
	private static Logger logger = Logger.getLogger(OwnerHouseService.class);
	
	 private OwnerHouseDaoImpl ownerHouseDaoImpl;
	   
	  
	  /**
	     * Get all owner - house combination
	     *
	     * @return
	     */
	    @Transactional(readOnly = true)
	    public List<OwnerHouseBean> getOwnerHouseList()
	    {
	    	return ownerHouseDaoImpl.getOwnerHouseList();
	    }
	    
	    
	    /**
	     * @param ownerHouseDaoImpl the ownerHouseDaoImpl to set
	     */
	    @Autowired
	    public void setOwnerHouseDaoImpl(OwnerHouseDaoImpl ownerHouseDaoImpl) {
	        this.ownerHouseDaoImpl = ownerHouseDaoImpl;
	    }

	
	
	

}

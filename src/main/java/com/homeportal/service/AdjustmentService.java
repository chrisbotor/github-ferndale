/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.dao.impl.AdjustmentDaoImpl;
import com.homeportal.model.Adjustment;
import com.homeportal.util.ValidationUtil;


/**
 *
 * @author racs
 */
@Service
public class AdjustmentService 
{
	private static Logger logger = Logger.getLogger(AdjustmentService.class);
	private AdjustmentDaoImpl adjustmentDaoImpl;
	

    /**
     * 	Creates an Adjustment object
     * */
    @Transactional
    public Adjustment createAdjustment(int userId, int houseId, String type, String remarks, double amount, String postedDate) 
    		throws SQLException
    {
    	System.out.println("went to AdjustmentService...");
    	logger.info("went to AdjustmentService...");
    	
    	Adjustment adj = new Adjustment();
    	adj.setUserId(userId);
    	adj.setHouseId(houseId);
    	adj.setRemarks(remarks);
    	
    	adj.setPostedDate(postedDate);
    	adj.setPaidAmount(new Double("0.00"));
    	adj.setCreatedAt(new java.util.Date());
    	adj.setUpdatedAt(new java.util.Date());
    	
    	if (ValidationUtil.hasValue(type))
    	{
    		if (type.equalsIgnoreCase("adjustment"))
    		{
    			amount = -1 * amount;
    			adj.setType("A");
    		}
    		else if (type.equalsIgnoreCase("penalty"))
        	{
    			adj.setType("P");
        	}
    	}
    	
    	adj.setAmount(amount);
    	
    	return adjustmentDaoImpl.createAdjustment(adj);
    }

    
    
    /**
     * Gets all adjustments for a specific user and his/her house
     * */
    @Transactional
    public List<Adjustment> getAdjustmentByUserIdAndHouseId(int userId, int houseId) throws SQLException
    {
    	logger.info("SERVICE getting adjustments for user with ID: " + userId + " and for house " + houseId);
    	System.out.println("SERVICE getting adjustments for user with ID: " + userId + " and for house " + houseId);
    	
    	List<Adjustment> adjustments = adjustmentDaoImpl.getAdjustmentsByUserIdAndHouseId(userId, houseId);
    	return adjustments;
    }
    
    
    /**
	 * 	Updates the paid amount in adjustment table
	 * */
    @Transactional
    public boolean updateAdjustmentPaidAmount(double paidAmount, int requestId) 
    {
    	try
    	{
    		// get the Adjustment object using the request id
        	Adjustment adj = adjustmentDaoImpl.getAdjustmentById(requestId);
        	
        	if (adj != null)
        	{
        		adj.setPaidAmount(paidAmount);
        		adj.setUpdatedAt(new Date());
        	}
        	
    		adjustmentDaoImpl.updateAdjustment(adj);
    		return true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("Error encountered in updating adjustment. " + ex.getMessage());
    		throw new HibernateException("Error encountered in updating adjustment. ", ex);
    	}
    }
    
    
    
    /**
    * @param adjustmentDaoImpl the adjustmentDaoImpl to set
    */
    @Autowired
    public void setAdjustmentDaoImpl(AdjustmentDaoImpl adjustmentDaoImpl)
    {
        this.adjustmentDaoImpl = adjustmentDaoImpl;
    }

}

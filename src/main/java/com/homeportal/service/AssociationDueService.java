/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.dao.impl.AssociationDueDaoImpl;
import com.homeportal.model.Adjustment;
import com.homeportal.model.AssociationDue;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.ValidationUtil;


/**
 *
 * @author racs
 */
@Service
public class AssociationDueService 
{
	private static Logger logger = Logger.getLogger(AssociationDueService.class);
	private AssociationDueDaoImpl assocDueDaoImpl;
	
	
	
    /**
     * Creates an Association Due object
     * */
	@Transactional
    public AssociationDue createAssociationDue(int userId, int houseId, String billingMonth, String postedDate, double rate, 
    		String remarks, double lotArea, double amount) throws SQLException
    {
		logger.info("SERVICE creating an association due object...");
    	System.out.println("SERVICE creating an association due object...");
    	
    	AssociationDue assocDue = new AssociationDue();
    	
    	assocDue.setUserId(userId);
		assocDue.setHouseId(houseId);
		assocDue.setbillingMonth(""); // TODO: NEEDS CODE FOR THIS!!
		assocDue.setPostedDate(postedDate);
		assocDue.setStatus(ConstantsUtil.NEW);
		assocDue.setRate(rate);
		assocDue.setRemarks(remarks);
		assocDue.setLotArea(lotArea);
		assocDue.setAmount(amount);
		assocDue.setPaidAmount(0.00);
		assocDue.setLotArea(lotArea);
		assocDue.setCreatedAt(new Date());
		assocDue.setUpdatedAt(new Date());
		
		return assocDueDaoImpl.saveAssociationDue(assocDue);
    }
	
	
	/**
	 * 	Updates the paid amount in association_due table
	 * */
    @Transactional
    public boolean updateAssocDuePaidAmount(double paidAmount, int requestId, double amount) 
    {
    	return assocDueDaoImpl.updateAssocDuePaidAmount(paidAmount, requestId, amount);
    }

    
    /**
     * Updates the amount in association_due table
     * */
    @Transactional
    public boolean updateAssocDueNewAmountToPay(int userId, int houseId, double amount, String remarks) 
    {
    	AssociationDue assocDue = getAssociationDue(userId, houseId);    	
    	
    	try
    	{
    		assocDue.setAmount(amount);
    		assocDue.setRemarks(remarks);
    		assocDue.setBilled(1);
    		assocDue.setUpdatedAt(new Date());
    		
    		assocDueDaoImpl.updateAssocDue(assocDue);
    		return true;
    	}
    	catch(HibernateException ex)
    	{
    		logger.error("Error encountered in updating association dues. " + ex.getMessage());
    		throw new HibernateException("Error encountered in updating association dues. ", ex);
    	}
   }
    
    
    /**
     * Updates the association due's status to 'DONE' and billed from zero to 1 so that the SQL trigger for job order will fire 
     * */
    @Transactional
    public boolean updateAssociationDue(AssociationDue assocDue)
    {
    	try
    	{
    		assocDueDaoImpl.updateAssocDue(assocDue);
    		return true;
    	}
    	catch(HibernateException ex)
    	{
    		logger.error("Error encountered in updating association dues. " + ex.getMessage());
    		throw new HibernateException("Error encountered in updating association dues. ", ex);
    	}
   }
    
    
    
   /**
     * Gets the Association Due record for a specific home owner
     * */
    @Transactional
    public AssociationDue getAssociationDue(int userId, int houseId) 
    {
    	logger.info("Getting the assoc dues in the service layer...");
    	System.out.println("Getting the assoc dues in the service layer...");
    	
    	AssociationDue assocDue = assocDueDaoImpl.getAssociationDue(userId, houseId);   	
    	return assocDue;
    }
  
    
    
    /**
    * @param assocDueDaoImpl the assocDueDaoImpl to set
    */
    @Autowired
    public void setAssociationDueDaoImpl(AssociationDueDaoImpl associationDueDaoImpl)
    {
        this.assocDueDaoImpl = associationDueDaoImpl;
    }

}

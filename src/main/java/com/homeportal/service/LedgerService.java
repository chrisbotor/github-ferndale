/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.dao.impl.LedgerDaoImpl;
import com.homeportal.model.Adjustment;
import com.homeportal.model.AssociationDue;
import com.homeportal.model.LedgerSummary;
import com.homeportal.util.ConstantsUtil;


/**
 *
 * @author Racs
 */
@Service
public class LedgerService 
{
	private static Logger logger = Logger.getLogger(LedgerService.class);
	private LedgerDaoImpl ledgerDaoImpl;
	
	
	/**
	 * 	Gets the ledger summary based on the house id
	 * */
	@Transactional
	public LedgerSummary getLedgerSummaryByHouseId(int houseId) throws SQLException
	{
		return ledgerDaoImpl.getLedgerSummaryByHouseId(houseId);
	}
	
	
	
	/**
     * Creates an Ledger Summary object which will be saved to database
     * 
     * */
	@Transactional
    public LedgerSummary createLedgerSummary(int userId, int houseId, Double amount, Double penalty) throws SQLException
    {
		logger.info("SERVICE creating a ledger summary object...");
    	System.out.println("SERVICE creating a ledger summary object...");
    	
    	LedgerSummary ledgerSummary = new LedgerSummary();
    	ledgerSummary.setUserId(userId);
    	ledgerSummary.setHouseId(houseId);
    	ledgerSummary.setAmount(amount);
    	ledgerSummary.setPenalty(penalty);
    	ledgerSummary.setCreatedAt(new Date());
    	ledgerSummary.setUpdatedAt(new Date());
    	
		return ledgerDaoImpl.saveLedgerSummary(ledgerSummary);
    }
	
    
	/**
	 * 	Updates the penalty column in the ledger_summary table
	 * */
    @Transactional
    public boolean updateLedgerSummary(LedgerSummary ledgerSummary) 
    {
    	try
    	{
    		ledgerDaoImpl.updateLedgerSummary(ledgerSummary);
    		return true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("Error encountered in updating ledgerSummary. " + ex.getMessage());
    		throw new HibernateException("Error encountered in updating ledgerSummary. ", ex);
    	}
    }
    
    /**
    * @param ledgerDaoImpl the ledgerDaoImpl to set
    */
    @Autowired
    public void setLedgerDaoImpl(LedgerDaoImpl ledgerDaoImpl)
    {
        this.ledgerDaoImpl = ledgerDaoImpl;
    }

}

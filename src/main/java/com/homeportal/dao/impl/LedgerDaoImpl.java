/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.homeportal.dao.ILedgerDAO;
import com.homeportal.model.Adjustment;
import com.homeportal.model.LedgerSummary;
import com.homeportal.model.Rates;
import com.homeportal.util.MessageBundle;

/**
 *
 * @author RACS
 */
@Repository
public class LedgerDaoImpl implements ILedgerDAO
{
    
	private static Logger logger = Logger.getLogger(RatesDaoImpl.class);

	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
	
	private static final String GET_RATES_HQL = MessageBundle.getSqlProperty("get.rates.hql");
	private static final String GET_RATES_BY_CODE_HQL = MessageBundle.getSqlProperty("get.ledger.summary.by.houseid.hql");
	
	
	
    /**
     * Gets a ledger summary object based on the given house id
     * 
     * */
    @SuppressWarnings("unchecked")
    public LedgerSummary getLedgerSummaryByHouseId(int houseId)
    {
    	logger.info("DAO getting the rates for: " + houseId);
    	System.out.println("DAO getting the rates for: " + houseId);
    	
    	Session session = sessionFactory.getCurrentSession();
    	List<LedgerSummary> ratesList = null;
    	LedgerSummary rates = null;
		
		Query query = session.createQuery(GET_RATES_BY_CODE_HQL);
		query.setParameter("houseId", houseId);
		
		ratesList = query.list();
		
		if (ratesList != null && ratesList.size() > 0)
		{
			rates = ratesList.get(0);
		}
		
    	return rates;
    }
    
    
    
    /**
     *	Creates a new ledger summary record in ledger_summary table
     *
     * */
	@Override
	public LedgerSummary saveLedgerSummary(LedgerSummary ledgerSummary) throws SQLException
	{ 
		logger.info("DAO Saving ledger summary to the database...");
		System.out.println("DAO Saving ledger summary to the database...");
		
		Session session = sessionFactory.getCurrentSession();
		session.save(ledgerSummary);
        return ledgerSummary;
	}
    
	
	
	/**
     * Gets all the rates from the database
     * */
    @SuppressWarnings("unchecked")
    public List<Rates> getRates()
    {
    	logger.info("DAO Getting all RATES from the database...");
    	System.out.println("DAO Getting all RATES from the database...");
    	
    	Session session = sessionFactory.getCurrentSession();
    	Query query = session.createQuery(GET_RATES_HQL);
			
		return query.list();
    }
    
    
    
    /**
     * Update rates object
     * 
     * */
    public Rates updateRates(Rates rates) {
        Session session = sessionFactory.getCurrentSession();
        session.update(rates);
        return rates;
    
    }
    

    /**
	 * Updates the LedgerSummary (use to update the penalty of LedgerSummary)
	 * */
	public void updateLedgerSummary(LedgerSummary ledgerSummary) throws HibernateException
	{
	     Session session = sessionFactory.getCurrentSession();
	     session.update(ledgerSummary);
	}


	/*@Override
	public LedgerSummary getLedgerSummaryByHouseId(int houseId)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}*/


	@Override
	public List<LedgerSummary> getAllLedgerSummary() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
    
}

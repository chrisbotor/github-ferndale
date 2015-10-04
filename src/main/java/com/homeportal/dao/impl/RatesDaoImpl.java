/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.homeportal.dao.IRatesDAO;
import com.homeportal.model.AssociationDue;
import com.homeportal.model.Rates;
import com.homeportal.util.MessageBundle;

/**
 *
 * @author racs
 */
@Repository
public class RatesDaoImpl implements IRatesDAO
{
    
	private static Logger logger = Logger.getLogger(RatesDaoImpl.class);

	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
	
	private static final String GET_RATES_HQL = MessageBundle.getSqlProperty("get.rates.hql");
	private static final String GET_RATES_BY_CODE_HQL = MessageBundle.getSqlProperty("get.rates.by.code.hql");
	
	
    /**
     * Gets all the rates from the database
     * */
    @SuppressWarnings("unchecked")
    public List<Rates> getRates()
    {
    	logger.info("Getting all RATES from the database...");
    	System.out.println("Getting all RATES from the database...");
    	
    	Session session = sessionFactory.getCurrentSession();
    	Query query = session.createQuery(GET_RATES_HQL);
			
		return query.list();
    }
    
    
    /**
     * Gets a specific c object based on the given code (ex: assocdue)
     * */
    @SuppressWarnings("unchecked")
    public Rates getRatesByCode(String code) 
    {
    	logger.info("DAO getting the rates for: " + code);
    	System.out.println("DAO getting the rates for: " + code);
    	
    	Session session = sessionFactory.getCurrentSession();
    	List<Rates> ratesList = null;
    	Rates rates = null;
		
		Query query = session.createQuery(GET_RATES_BY_CODE_HQL);
		query.setParameter("code", code);
		
		ratesList = query.list();
		
		if (ratesList != null && ratesList.size() > 0)
		{
			rates = ratesList.get(0);
		}
		
    	return rates;
    }
    
    
    
    /**
     * Update rates object
     * */
    public Rates updateRates(Rates rates) {
        Session session = sessionFactory.getCurrentSession();
        session.update(rates);
        return rates;
    
    }
    
}

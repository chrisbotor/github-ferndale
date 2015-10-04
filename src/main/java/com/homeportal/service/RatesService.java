/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.dao.impl.RatesDaoImpl;
import com.homeportal.model.AssociationDue;
import com.homeportal.model.Rates;
import com.homeportal.util.RatesUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class RatesService 
{
	private static Logger logger = Logger.getLogger(RatesService.class);
	
    private RatesDaoImpl ratesDaoImpl;
    private RatesUtil ratesUtil;

    
    /**
     * Get all rates
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Rates> getRatesList() 
    {
        return ratesDaoImpl.getRates();
    }
    
    
    /**
     * Gets a specific Rates object based on the given code (ex: assocdue)
     * */
    @Transactional
    public Rates getRatesByCode(String code) 
    {
    	logger.info("SERVICE getting the rates for: " + code);
    	System.out.println("SERVICE getting the rates for: " + code);
    	
    	Rates rates = ratesDaoImpl.getRatesByCode(code);
    	return rates;
    }
    
    
    /**
     * Update rates object
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<Rates> update(Object data) {

        List<Rates> returnRates = new ArrayList<Rates>();
        List<Rates> updatedRates = ratesUtil.getRatesFromRequest(data);
        for (Rates v : updatedRates) {
            v.setCreatedAt(new Date());
            v.setUpdatedAt(new Date());
            returnRates.add(ratesDaoImpl.updateRates(v));
        }
        return returnRates;
    }
    
    public Double getAmount(Object data){
    
        Double amt = 0.0;
        List<Rates> list = ratesUtil.getRatesFromRequest(data);
        for (Rates rates : list) {
            amt = rates.getAmount();
        }
        return amt;
    }
    
    public String getStartDate(Object data){
        
        String startDate = "";
        List<Rates> list = ratesUtil.getRatesFromRequest(data);
        for (Rates rates : list) {
            startDate = rates.getStartDate();
        }
        return startDate;
    }
    
    public String getEndDate(Object data){
        
        String endDate = "";
        List<Rates> list = ratesUtil.getRatesFromRequest(data);
        for (Rates rates : list) {
            endDate = rates.getEndDate();
        }
        return endDate;
    }
    
    /**
     * @param ratesDaoImpl the ratesDaoImpl to set
     */
    @Autowired
    public void setRatesDaoImpl(RatesDaoImpl ratesDaoImpl) {
        this.ratesDaoImpl = ratesDaoImpl;
    }

    /**
     * @param ratesUtil the ratesUtil to set
     */
    @Autowired
    public void setRatesUtil(RatesUtil ratesUtil) {
        this.ratesUtil = ratesUtil;
    }
}

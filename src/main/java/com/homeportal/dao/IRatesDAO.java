/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.model.Rates;

import java.util.List;

/**
 *
 * @author racs
 */
public interface IRatesDAO {
    
    public List<Rates> getRates();
    public Rates getRatesByCode(String code);
    public Rates updateRates(Rates rates);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.model.OrSeries;
import java.util.List;

/**
 *
 * @author Peter
 */
public interface IOrSeriesDAO {
    
    public List<OrSeries> getSeriesNumber();
    public OrSeries updateSeriesNumber(OrSeries os);
    public int updateCurrentNumber(int current);
}

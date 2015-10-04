/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.dao.impl.OrSeriesDaoImpl;
import com.homeportal.model.OrSeries;
import com.homeportal.util.JasperReportUtil;
import com.homeportal.util.OrSeriesUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class OrSeriesService {
    
    private OrSeriesDaoImpl orSeriesDaoImpl;
    private OrSeriesUtil orSeriesUtil;
    
     /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<OrSeries> getSeriesNumber() {
        return orSeriesDaoImpl.getSeriesNumber();
    }
    
    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<OrSeries> updateSeriesNumber(Object data) {
        List<OrSeries> retOr = new ArrayList<OrSeries>();
        List<OrSeries> updOr = orSeriesUtil.getOrSeriessFromRequest(data);
        for (OrSeries v: updOr) {
            v.setUpdatedAt(new Date());
            v.setCreatedAt(new Date());
            retOr.add(orSeriesDaoImpl.updateSeriesNumber(v));
        }
        return retOr;
    }
    
    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<OrSeries> updateFirstNumber(Object data) {
        List<OrSeries> retOr = new ArrayList<OrSeries>();
        List<OrSeries> updOr = orSeriesUtil.getOrSeriessFromRequest(data);
        for (OrSeries v: updOr) {
            System.out.println("first num : " + v.getStartSeries());
            orSeriesDaoImpl.updateCurrentNumber(v.getStartSeries());
        }
        return retOr;
    }
    
    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public int updateCurrentNumber(int current) {
        //List<OrSeries> updOr = orSeriesUtil.getOrSeriessFromRequest(data);
        //for (OrSeries v : updOr) {
            //retOr.add(orSeriesDaoImpl.updateSeriesNumber(v));
        //}
        return orSeriesDaoImpl.updateCurrentNumber(current);
    }
    
    public int startSeries(Object data)
    {
        int startSeries = 0;
        List<OrSeries> list = orSeriesUtil.getOrSeriessFromRequest(data);
        for (OrSeries or : list) {
            startSeries = or.getStartSeries();
        }
        return startSeries;
    }
    
    public int endSeries(Object data)
    {
        int endSeries = 0;
        List<OrSeries> list = orSeriesUtil.getOrSeriessFromRequest(data);
        for (OrSeries or : list) {
            endSeries = or.getEndSeries();
        }
        return endSeries;
    }
    
    
    /**
     * @param orSeriesDaoImpl the orSeriesDaoImpl to set
     */
    @Autowired
    public void setOrSeriesDaoImpl(OrSeriesDaoImpl orSeriesDaoImpl) {
        this.orSeriesDaoImpl = orSeriesDaoImpl;
    }

    /**
     * @param orSeriesUtil the orSeriesUtil to set
     */
    @Autowired
    public void setOrSeriesUtil(OrSeriesUtil orSeriesUtil) {
        this.orSeriesUtil = orSeriesUtil;
    }
}

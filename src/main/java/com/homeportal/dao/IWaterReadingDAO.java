/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import java.util.List;

import com.homeportal.model.WaterReading;

/**
 *
 * @author racs
 */
public interface IWaterReadingDAO 
{
	public Double getWaterRate();
    public boolean insertWaterReading(List<WaterReading> waterReadingList);
    public boolean updateWaterReadingPaidAmount(Double paidAmount, int requestId, Double amount);
}

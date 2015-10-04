package com.homeportal.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.dao.impl.WaterReadingDaoImpl;
import com.homeportal.model.WaterReading;

@Service
public class WaterReadingService 
{
	private static Logger logger = Logger.getLogger(WaterReadingService.class);
	
	private WaterReadingDaoImpl waterReadingDaoImpl = new WaterReadingDaoImpl();
	
	
	@Transactional
    public Double getWaterRate() 
	{
		if (waterReadingDaoImpl != null)
		{
			 return waterReadingDaoImpl.getWaterRate();
		}
		else
		{
			System.out.println("waterReadingDaoImpl is NULL!");
			return null;
		}
       
    }
	
	
	
	@Transactional
    public boolean insertWaterReading(List<WaterReading> waterReadingList) 
	{
		if (waterReadingDaoImpl != null)
		{
			 return waterReadingDaoImpl.insertWaterReading(waterReadingList);
		}
		else
		{
			System.out.println("waterReadingDaoImpl is NULL!");
			return false;
		}
       
    }
	
	
	 @Transactional
	 public boolean updateWaterReadingPaidAmount(double paidAmount, int requestId, double amount) 
	 {
		 return waterReadingDaoImpl.updateWaterReadingPaidAmount(paidAmount, requestId, amount);
	 }
	 
	 
	 
	 
	/**
     * @param HouseDaoImpl the HouseDaoImpl to set
     */
    @Autowired
    public void setWaterReadingDaoImpl(WaterReadingDaoImpl waterReadingDaoImpl) 
    {
        this.waterReadingDaoImpl = waterReadingDaoImpl;
    }
}

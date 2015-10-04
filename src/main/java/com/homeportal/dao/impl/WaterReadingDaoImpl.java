package com.homeportal.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.homeportal.dao.IWaterReadingDAO;
import com.homeportal.model.WaterReading;
import com.homeportal.util.SQLUtil;


@Repository
public class WaterReadingDaoImpl implements IWaterReadingDAO
{
	private static Logger logger = Logger.getLogger(WaterReadingDaoImpl.class);
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
    
	
	@Override
	public Double getWaterRate()
	{
		logger.info("Getting water rate from rates table...");
		
		Double rate = null;
		String sqlStatement = null;
		ResultSet rs = null;
    	Connection connection = null;
    	Statement statement = null;
        IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
         
        try 
        {
        	sqlStatement = SQLUtil.getWaterRateSQL();
          	connection = connectionDao.getConnection();
          	
          	if (connection != null)
          	{
          		statement = connection.createStatement();
          		
          		if (statement != null)
          		{
          			rs = statement.executeQuery(sqlStatement);
          			
          			if (rs != null)
          			{
          				while (rs.next())
          				{
          					rate = rs.getDouble("amount");
          				}
          				
          				System.out.println("rate: " + rate);
          			}
          		}
          	}
         
         } 
         catch (Exception ex) 
         {
         	logger.error("Could not get water rate from the rate table. \nGot error: " + ex.getMessage());
         	
         	System.out.println("Could not get water rate from the rate table. \nGot error: " + ex.getMessage());
         }
         finally
         {
        	 if(rs != null)
             {
        		 try
        		 {
        			 rs.close();; 
        		 }
        		 catch (SQLException ex) 
        	     {
        			 logger.error("Could not close ResultSet. Got error: " + ex.getMessage());
        	         	 
        	         System.out.println("Could not close ResultSet. Got error: " + ex.getMessage());
        	     }
             }
        	 
        	 
        	 if(statement != null)
             {
        		 try
        		 {
        			 statement.close(); 
        		 }
        		 catch (SQLException ex) 
        	     {
        			 logger.error("Could not close Statement. Got error: " + ex.getMessage());
        	         	 
        	         System.out.println("Could not close Statement. Got error: " + ex.getMessage());
        	     }
             }
        	 
        	 if(connection != null)
          	 {
        		 connectionDao.closeConnection();
          	 }
          }
    	
        return rate;
	}
	
	
    @Override
	public boolean insertWaterReading(List<WaterReading> waterReadingList) 
	{
    	System.out.println("============== inserting water reading ====================");
    	System.out.println("Number of records to insert: " + waterReadingList.size());
    	
    	 boolean insertedAll = false;
    	 String sqlString = null; 
    	 Connection connection = null;
    	 Statement statement = null;
         IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
         
         try 
         {
        	connection = connectionDao.getConnection();
        	
        	if (waterReadingList != null)
        	{
        		for (WaterReading wr : waterReadingList)
        		{
        			if (wr != null)
        			{
        				sqlString = SQLUtil.getInsertWaterReadingSQL(wr);
        	          	
        	          	if (connection != null)
        	          	{
        	          		try 
        	          		{
        	          			statement = connection.createStatement();
            	          		
            	          		if (statement != null)
            	          		{
            	          			statement.executeUpdate(sqlString);
            	          		}
        	          			
        	          		}
        	          		catch (Exception ex)
        	          		{
        	          			logger.error("Could not insert water reading for userId = " + wr.getUserId() + " and houseId = " + wr.getHouseId()
        	                 			+ "\nGot error: " + ex.getMessage());
        	                 	 
        	                 	System.out.println("Could not insert water reading for userId = " + wr.getUserId() + " and houseId = " + wr.getHouseId()
        	                 			+ "\nGot error: " + ex.getMessage());
        	          		}
        	          	}
        			}
        		}
        	}
         } 
         catch (Exception ex) 
         {
         	logger.error("Could not get a connection.\nGot error: " + ex.getMessage());
         	 
         	System.out.println("Could not get a connection.\nGot error: " + ex.getMessage());
         }
         finally
         {
        	 if(statement != null)
             {
        		 try
        		 {
        			 statement.close(); 
        		 }
        		 catch (SQLException ex) 
        	     {
        			 logger.error("Could not close Statement. Got error: " + ex.getMessage());
        	         	 
        	         System.out.println("Could not close Statement. Got error: " + ex.getMessage());
        	     }
             }
        	 
          	 if(connection != null)
          	 {
          		connectionDao.closeConnection();
          	 }
          }
    	
    	return insertedAll;
	}

    
    

    /**
     *  Updates water_reading table during billing payment thru admin
     * */
	@Override
	public boolean updateWaterReadingPaidAmount(Double paidAmount, int requestId, Double amount)
	{
		boolean updated = false;
    	
    	final String UPDATE_WATER_READING_AMOUNT_PAID_SQL = SQLUtil.getUpdateWaterReadingPaidAmountSQL(paidAmount, requestId, amount);
    	
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery(UPDATE_WATER_READING_AMOUNT_PAID_SQL);
        int rowCount = query.executeUpdate();
        
        if (rowCount > 0)
        {
        	updated = true;
        }
        
        return updated;
	}

}
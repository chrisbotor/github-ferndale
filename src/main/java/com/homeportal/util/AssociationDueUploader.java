package com.homeportal.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import com.homeportal.dao.impl.IConnectionDaoImpl;
import com.homeportal.model.AssociationDue;

/**
 *
 * @author racs
 */
public class AssociationDueUploader 
{
	private static Logger logger = Logger.getLogger(AssociationDueUploader.class);
	
	public static void main(String[] args) throws SQLException
    {
		String remarks = "";
		
		if (args != null && args.length > 0)
		{
			remarks = args[0];
		}
		else
		{
			logger.error("NO remarks given.");
		}
		
		String sqlString = null; 
	   	Connection connection = null;
	   	Statement statement = null;
        ResultSet rs = null;
	    IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
	    
	   connection = connectionDao.getConnection();
	    	
	    	sqlString = SQLUtil.getAssociationDueRateSQL();
	    	
	    	try 
      		{
      			statement = connection.createStatement();
          		
          		if (statement != null)
          		{
          			rs = statement.executeQuery(sqlString);
          			
          			if (rs != null)
          			{
          			    while (rs.next())
          		        {
          		            AssociationDue assocDue = new AssociationDue();
          		            assocDue.setUserId(Integer.parseInt(rs.getString(1)));
          		            assocDue.setHouseId(Integer.parseInt(rs.getString(2)));
          		            assocDue.setLotArea(Double.parseDouble(rs.getString(3)));
          		            assocDue.setRate(Double.parseDouble(rs.getString(4)));
          		            assocDue.setAmount(Double.parseDouble(rs.getString(5)));
          		            assocDue.setRemarks(remarks);
          		            
          		            Statement stInsert = null;
          		            stInsert = connection.createStatement();
          		         
          		            sqlString = SQLUtil.getInsertAssociationDueSQL(assocDue); // should be modified to insert the remarks
          		            
          		            try
          		            {
          		            	stInsert.executeUpdate(sqlString);
               		            stInsert.close();
          		            }
          		            catch (SQLException ex)
          		            {
          		            	logger.error("Could not insert association due for userId = " + assocDue.getUserId() + " and houseId = " + assocDue.getHouseId()
        	                 			+ "\nGot error: " + ex.getMessage());
        	                 	 
        	                 	System.out.println("Could not insert association due for userId = " + assocDue.getUserId() + " and houseId = " + assocDue.getHouseId()
        	                 			+ "\nGot error: " + ex.getMessage());
          		            }
          		           
          		        }
          			}
          		}
      			
      		}
      		catch (Exception ex)
      		{
      			logger.error("Could not get the current association dues. \nGot error: " + ex.getMessage());
             	 
             	System.out.println("Could not get the current association dues. \nGot error: " + ex.getMessage());
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
	}
       
}

package com.homeportal.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import com.homeportal.dao.IConnectionDao;
import com.homeportal.dao.exception.DaoException;
import com.homeportal.util.MessageBundle;

/**
 * Class that gets the connection to the database, this is used in generating PDF reports
 * */
public class IConnectionDaoImpl implements IConnectionDao
{
	private static Logger logger = Logger.getLogger(IConnectionDaoImpl.class);
	private Connection connection;
	
	private static final String MYSQL_JDBC_DRIVER = MessageBundle.getProperty("mysqlJDBCDriver");
    private static final String CONNECTION_URL = MessageBundle.getProperty("connectionURL");
    
	
	public IConnectionDaoImpl()
	{
		try
		{
			Class.forName(MYSQL_JDBC_DRIVER);
	        connection = DriverManager.getConnection(CONNECTION_URL);
	        
	        System.out.println("\nUsing this CONNECTION_URL: " + CONNECTION_URL);
	        logger.info("\nUsing this CONNECTION_URL: " + CONNECTION_URL);
			
		}
		catch(Exception ex)
		{
			System.out.println("\nError encountred while getting a connection." + ex.getMessage());
	        logger.info("\nError encountred while getting a connection." + ex.getMessage());
	        
			new DaoException("Error encountred while getting a connection." + ex.getMessage(), ex);
		}
	}
	
	
	/** get a connection 
	 * @return a connection
	 * 
	 * */
	public Connection getConnection()
	{
		return this.connection; 
	}
	
	
	/** close the connection **/
	public void closeConnection()
	{
		if(connection != null)
		{
			try
			{
				this.connection.close();
				
			}
			catch(Exception ex)
			{
				System.out.println("\nError encountred while closing the connection." + ex.getMessage());
		        logger.info("\nError encountred while closing the connection." + ex.getMessage());
		        
				new DaoException("Error encountred while closing the connection." + ex.getMessage(), ex);
			}
		}	
	}

}

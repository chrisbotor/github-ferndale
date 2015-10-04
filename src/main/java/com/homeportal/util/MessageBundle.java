package com.homeportal.util;

import java.util.ResourceBundle;

public class MessageBundle {


	private static final ResourceBundle bundle = ResourceBundle.getBundle("setting");
	private static final ResourceBundle sqlBundle = ResourceBundle.getBundle("sql");
	
	
	/**
	 * Gets the properties from the setting.properties (which contains the constant settings for the application)
	 * */
	public static String getProperty(String key) 
	{
		StringBuilder value = new StringBuilder();
		if(key!=null)
			value.append(bundle.getString(key));
		return value.toString();
	}
	
	
	/**
	 * Gets the properties from the sql.properties (which contains the sql queries)
	 * */
	public static String getSqlProperty(String key) 
	{
		StringBuilder value = new StringBuilder();
		if(key!=null)
			value.append(sqlBundle.getString(key));
		return value.toString();
	}
}
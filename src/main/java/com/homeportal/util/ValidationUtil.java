package com.homeportal.util;

import org.apache.log4j.Logger;


/**
*
* Class that is used for validations
* */
public class ValidationUtil
{
	private static Logger logger = Logger.getLogger(ValidationUtil.class);
	
	/**
	 *  Checks if an input String is not null and has a value
	 * */
	public static boolean hasValue(String inputStr)
	{
		if (inputStr != null && !inputStr.equals("") )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * This method is used by SearchService to validate if a String has no value
	 * */
	public static boolean hasNoValue(String inputStr)
	{
		if (inputStr == null || inputStr == "" || inputStr.equals("") || inputStr.equals(null))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}

package com.homeportal.util;

import java.text.DecimalFormat;

/**
 * Contains common methods for all Java files
 * Creation Date: 2015-03-04
 * Author: Racs
 * 
 * */
public class CommonUtil 
{
	public static void main(String[] args)
	{
		Double value = new Double("25033.256");
		Double formatted = CommonUtil.formatDouble(value, ConstantsUtil.TWO_DECIMAL);
		
		System.out.println("FORMATTED: " + formatted);
	}
	
	

	/**
	 * Checks if the String parameter has value and is not null
	 * */
	public static boolean hasValue(String parameter)
	{
		boolean value = false;
		
		if (parameter != null && parameter != "")
		{
			value = true;
		}
		
		return value;
	}

	

	/**
	 * Formats a double to any decimal place
	 * 
	 * */
	public static Double formatDouble(Double value, String format)
	{
		Double formattedValue = null;
		
		if (value != null)
		{
			DecimalFormat formatter = new DecimalFormat(format);
			formattedValue = Double.parseDouble(formatter.format(value));
		}
		
		return formattedValue;
	}
	
}

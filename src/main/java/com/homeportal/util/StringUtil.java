package com.homeportal.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/*
 * Utility class for manipulating String elements
 * 
 * */
public class StringUtil 
{

	private static Logger logger = Logger.getLogger(StringUtil.class);
	
	
	public static void main(String[] args)
	{
		List<String> statusList = new ArrayList<String>();
		
		statusList.add("Reserved");
		statusList.add("Booked");
		// statusList.add("New");
		// statusList.add("Change Request");
		
		StringUtil.getStatusList(statusList);
	}
	
	
	/*
	 * Outputs a string of STATUS from the List of STATUSES
	 * */
	public static String getStatusList(List<String> statusList)
	{
		StringBuilder sb = new StringBuilder();
		
		if (statusList != null && statusList.size() > 0) 
		{
			for (String s : statusList)
			{
				if (ValidationUtil.hasValue(s))
				{
					sb.append("\'");
					sb.append(s);
					sb.append("\'");
					
					if (statusList.indexOf(s) < statusList.size() - 1)
					{
						sb.append(",");
					}
				}
			}
		}
		
		System.out.println("STATUS LIST: " + sb.toString());
		logger.info("STATUS LIST: " + sb.toString());
		
		return sb.toString();
	}
}

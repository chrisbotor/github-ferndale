package com.homeportal.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;



/**
 * Utility class for formatting Date and Time
 * @author ranievas
 *
 */
public class DateTimeUtil 
{
	private static Logger logger = Logger.getLogger(DateTimeUtil.class);
	
	public static void main(String[] args) throws Exception
	{
		DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
		fromFormat.setLenient(false);
		DateFormat toFormat = new SimpleDateFormat("ddMMMyyyy");
		toFormat.setLenient(false);
		String dateStr = "2011-07-09";
		Date date = fromFormat.parse(dateStr);
		System.out.println(toFormat.format(date));
		
		// String dateStr = "Mon Jun 18 00:00:00 IST 2012";
		
		/*String dateStr = "Wed Nov 06 2013 00:00:00 GMT 0800 (China Standard Time)";
		
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss");
		Date date = (Date)formatter.parse(dateStr);
		System.out.println(date);        

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
		System.out.println("formatedDate : " + formatedDate);*/
		// 'Wed Nov 06 2013 00:00:00 GMT 0800 (China Standard Time)'
		
		
		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String format = "MM/dd/yyyy";

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		
		String reportDate = DateTimeUtil.convertDateToString(today, format);
		System.out.println("========== reportDate: " + reportDate);
	}
	
	
	/**
	 * Formats time to military time format
	 * @param timeString 
	 * @return formattedTime
	 */
	public static String formatToMilitaryTime(String timeString)
	{
		System.out.println("Formatting time " + timeString + "...");
		
		String formattedTime = null;
		
		Map<String, String> timeMap = new HashMap<String, String>();
		
		timeMap.put("12:00 AM", "0000");
		timeMap.put("01:00 AM", "0100");
		timeMap.put("02:00 AM", "0200");
		timeMap.put("03:00 AM", "0300");
		timeMap.put("04:00 AM", "0400");
		timeMap.put("05:00 AM", "0500");
		timeMap.put("06:00 AM", "0600");
		timeMap.put("07:00 AM", "0700");
		timeMap.put("08:00 AM", "0800");
		timeMap.put("09:00 AM", "0900");
		timeMap.put("10:00 AM", "1000");
		timeMap.put("11:00 AM", "1100");
		timeMap.put("12:00 PM", "1200");
		timeMap.put("01:00 PM", "1300");
		timeMap.put("02:00 PM", "1400");
		timeMap.put("03:00 PM", "1500");
		timeMap.put("04:00 PM", "1600");
		timeMap.put("05:00 PM", "1700");
		timeMap.put("06:00 PM", "1800");
		timeMap.put("07:00 PM", "1900");
		timeMap.put("08:00 PM", "2000");
		timeMap.put("09:00 PM", "2100");
		timeMap.put("10:00 PM", "2200");
		timeMap.put("11:00 PM", "2300");
		
		
		if (timeString != null)
		{
			if (timeMap.containsKey(timeString))
			{
				formattedTime = timeMap.get(timeString);
				
				return formattedTime;
			}
		}
		
		return timeString;
	}
	
	
	
	/**
	 * Formats time to military time format during update
	 * @param timeString 
	 * @return formattedTime
	 */
	public static String formatToMilitaryTimeDuringUpdate(String timeString)
	{
		System.out.println("Formatting time " + timeString + "...");
		
		String formattedTime = null;
		
		Map<String, String> timeMap = new HashMap<String, String>();
		
		timeMap.put("12:00 AM", "0000");
		timeMap.put("1:00 AM", "0100");
		timeMap.put("2:00 AM", "0200");
		timeMap.put("3:00 AM", "0300");
		timeMap.put("4:00 AM", "0400");
		timeMap.put("5:00 AM", "0500");
		timeMap.put("6:00 AM", "0600");
		timeMap.put("7:00 AM", "0700");
		timeMap.put("8:00 AM", "0800");
		timeMap.put("9:00 AM", "0900");
		timeMap.put("10:00 AM", "1000");
		timeMap.put("11:00 AM", "1100");
		timeMap.put("12:00 PM", "1200");
		timeMap.put("1:00 PM", "1300");
		timeMap.put("2:00 PM", "1400");
		timeMap.put("3:00 PM", "1500");
		timeMap.put("4:00 PM", "1600");
		timeMap.put("5:00 PM", "1700");
		timeMap.put("6:00 PM", "1800");
		timeMap.put("7:00 PM", "1900");
		timeMap.put("8:00 PM", "2000");
		timeMap.put("9:00 PM", "2100");
		timeMap.put("10:00 PM", "2200");
		timeMap.put("11:00 PM", "2300");
		
		
		if (timeString != null)
		{
			if (timeMap.containsKey(timeString))
			{
				formattedTime = timeMap.get(timeString);
				
				return formattedTime;
			}
		}
		
		return timeString;
	}

	
	
	public static Timestamp convertStringToTimestamp(String strDate)
	{
		System.out.println("DateTimeUtil convertStringToTimestamp method =========== " + strDate);
		
		// DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat sdf = null;
		
		
		Date date = null;
		Timestamp t = null;
		
		if (ValidationUtil.hasValue(strDate))
		{
			System.out.println("BEFORE parsing DATE..");
			
			try
			{
				//String newDate = "08-Jun-2013";
				
				sdf = new SimpleDateFormat("MM/dd/yyyy");
				
				date = sdf.parse(strDate);
				
				System.out.println("DATE in DateTimeUtil: " + date);
				
				t = new Timestamp(date.getTime());
				
				System.out.println("Parsed requested date: " + t);
				
				logger.debug("Parsed requested date: " + t);
				
				return t;
			}
			catch(ParseException ex)
			{
				System.out.println("Cannot parse date string " + strDate + ":" + ex.getMessage());
				
				logger.error("Cannot parse date string " + strDate + ":" + ex.getMessage(), ex);
				
				sdf = new SimpleDateFormat("MM/dd/yyyy"); // change this to db format
			}
			
		}
		
		return t;
	}

	
	/*
	 * Converts '11/30/2013' to 30Nov2013
	 * 
	 * */
	public static String convertStringToDailyCollectionFormat(String strDate)
	{
		String newFormat = null;
		
		if (CommonUtil.hasValue(strDate))
		{
			DateFormat fromFormat = new SimpleDateFormat("MM/dd/yyyy");
			fromFormat.setLenient(false);
			DateFormat toFormat = new SimpleDateFormat("ddMMMyyyy");
			toFormat.setLenient(false);
			
			try
			{
				Date date = fromFormat.parse(strDate);
				newFormat = toFormat.format(date);
			}
			catch(ParseException ex)
			{
				logger.error("Date cannot be parsed! " + strDate);
			}
		}
		
		return newFormat;
	}
	

	
	/*
	 * Converts '11/2013' to Nov2013
	 * 
	 * */
	public static String convertStringToMontlyCollectionFormat(String strDate)
	{
		String newFormat = null;
		
		if (CommonUtil.hasValue(strDate))
		{
			DateFormat fromFormat = new SimpleDateFormat("MM/yyyy");
			fromFormat.setLenient(false);
			DateFormat toFormat = new SimpleDateFormat("MMMyyyy");
			toFormat.setLenient(false);
			
			try
			{
				Date date = fromFormat.parse(strDate);
				newFormat = toFormat.format(date);
			}
			catch(ParseException ex)
			{
				logger.error("Date cannot be parsed! " + strDate);
			}
		}
		
		logger.info("New date format: " + newFormat);
		return newFormat;
	}
	
	public static Timestamp now()
	{
		TimeZone tz = TimeZone.getTimeZone("GMT+8");
		
		Calendar c = Calendar.getInstance(tz);
		Date date = c.getTime();
		
		Timestamp t = new Timestamp(date.getTime());
		
		
		// Timestamp t = DateTimeUtil.convertStringToTimestamp(date.toString()); 
		
		return t;
	}
	
	
	/**
	 * 	Converts a Date to a formatted String
	 * */
	public static String convertDateToString(Date date, String format)
	{
		String formattedDate = null;
		DateFormat df = new SimpleDateFormat(format);
		
		if (date != null)
		{
			formattedDate = df.format(date);
		}
		else
		{
			logger.error("orDate is NULL!");
			System.out.println("======= orDate is NULL!");
		}
		
		
	
		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		// Get the date today using Calendar object.
		//Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		//String reportDate = df.format(today);

		// Print what date is today!
		System.out.println("**** Report Date: " + formattedDate);
		
		return formattedDate;
	}
	
	
	/**
	 * 	Converts a Date String to another format
	 *  Example: Sun Jun 28 2015 00:00:00 GMT+0800 (PHT) to '06/28/2015'
	 * */
	public static String convertDateStringToAnotherFormat(String dateString, String fromFormat, String toFormat)
	{
		// Sun Jun 28 2015 00:00:00 GMT+0800 (PHT)
		// String dateStr = "Mon Jun 18 00:00:00 IST 2012";
		// DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		
		/*DateFormat fromFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		fromFormat2.setLenient(false);
		DateFormat toFormat2 = new SimpleDateFormat("dd-MM-yyyy");
		toFormat2.setLenient(false);
		String dateStr = "2011-07-09";
		Date date = fromFormat2.parse(dateStr);*/
		
		DateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
		fromDateFormat.setLenient(false);
		DateFormat toDateFormat= new SimpleDateFormat(toFormat);
		toDateFormat.setLenient(false);
		
		String formattedDate = null;
		// String dateStr = "2011-07-09";
		
		try
		{
			Date date = fromDateFormat.parse(dateString);
			formattedDate = toDateFormat.format(date);
			
			logger.info("FORMATTED DATE: " + formattedDate);
			System.out.println("FORMATTED DATE: " + formattedDate);
		}
		catch(ParseException pex)
		{
			pex.printStackTrace();
		}
		
		
		
		
		
		
		/*String formattedDate = null;
		DateFormat df = new SimpleDateFormat(format);
		
		if (date != null)
		{
			formattedDate = df.format(date);
		}
		else
		{
			logger.error("orDate is NULL!");
			System.out.println("======= orDate is NULL!");
		}*/
		
		
	
		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		// Get the date today using Calendar object.
		//Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		//String reportDate = df.format(today);

		// Print what date is today!
		//System.out.println("**** Report Date: " + formattedDate);
		
		return formattedDate;
	}
}

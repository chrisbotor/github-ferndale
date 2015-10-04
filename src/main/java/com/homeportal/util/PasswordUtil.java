package com.homeportal.util;

import java.util.Random;
import org.apache.log4j.Logger;


/**
*
* Class that is used for validations
* */
public class PasswordUtil
{
	private static Logger logger = Logger.getLogger(PasswordUtil.class);
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	
	
	public static void main(String[] args)
	{
		String pwd = PasswordUtil.generatePassword();
		
		System.out.println("Password: " + pwd);
	}
	
	
	/**
	 * 	Generates 8-digit alpha-numeric password
	 * */
	public static String generatePassword() 
	{
		logger.info("Generating password...");
		System.out.println("Generating password...");
		
		int digits = 8;
		StringBuilder sb = new StringBuilder(digits);
		
		for( int i = 0; i < digits; i++ )
		{
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		}
		
		return sb.toString();
	}
}

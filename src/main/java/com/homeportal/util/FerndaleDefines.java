package com.homeportal.util;


/*
 * Class which contains the mapping and definitions of some tables in the database
 * Comments below refer to the table where the properties were derived
 * */
public class FerndaleDefines 
{
	
	// Job Order Types (job_order_type)
	public static final int AMENITIES = 1;
	public static final int SERVICE = 2;
	public static final int WATER = 3;
	public static final int ASSOCIATION = 4;
	public static final int ADJUSTMENTS = 5;
	
	
	// DELIMITERS
	public static final String SCALAR_PROP_DELIMITER = ",";
	
	
	// NUMBERS
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	
	
	// BEAN NAMES USED FOR POPULATING EXTJS GRID
	public static final String AMENITY_BEAN_NAME = "AmenityBean";
	public static final String SERVICE_BEAN_NAME = "ServiceBean";
	
	
	// STATUS
	public static final String NEW = "New";
}

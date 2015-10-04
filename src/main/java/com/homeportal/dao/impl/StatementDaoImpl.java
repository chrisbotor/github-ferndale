/*package com.homeportal.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.homeportal.bean.BillingBean;


 * Dao for Billing Statement
 * 
@Repository
public class StatementDaoImpl 
{

	 @Resource(name = "sessionFactory")
	 private SessionFactory sessionFactory;
	 
	 public static void main(String[] args)
	 {
		 StatementDaoImpl dao = new StatementDaoImpl();
		 dao.getUserIds("2013-06");
		 
	 }
	 
	 
	
	 * Gets the user id from users
	 * 
	public List<Integer> getUserIds(String billingMonth)
	{
		List<Integer> userIdList = new ArrayList<Integer>();
		
		
		String sqlStatement = null;
		Query query = null;
		
		if (billingMonth != null)
		{
			sqlStatement = "select user_id as userId from users where role_id = 3 or role_id = 4 order by role_id asc";
			
			System.out.println("\nSQL Query: " + sqlStatement);
	        	
	        query = session.createSQLQuery(sqlStatement);
	        userIdList = (List<Integer>) query.list();
	    }
		
		 return userIdList;
	}
	
	
	
}
*/
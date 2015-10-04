/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.homeportal.dao.IOfficialReceiptDAO;
import com.homeportal.model.OfficialReceipt;
import com.homeportal.model.OfficialReceiptDetails;
import com.homeportal.model.OfficialReceiptHeader;
import com.homeportal.util.MessageBundle;

/**
 *
 * @author Peter
 */
@Repository
public class OfficialReceiptDaoImpl implements IOfficialReceiptDAO
{
	private static Logger logger = Logger.getLogger(OfficialReceiptDaoImpl.class);
	
	private static final String GET_OR_HQL = MessageBundle.getSqlProperty("get.or.hql");
	private static final String GET_OR_HEADER_HQL = MessageBundle.getSqlProperty("get.or.header.hql");
	private static final String GET_OR_DETAILS_HQL = MessageBundle.getSqlProperty("get.or.details.hql");
	private static final String GET_OR_LIST_HQL = MessageBundle.getSqlProperty("get.or.list.hql");
	private static final String GET_OR_BY_OR_NUMBER_HQL = MessageBundle.getSqlProperty("get.or.by.or.number.hql");

	
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public OfficialReceipt saveOfficialReceipt(OfficialReceipt or) 
    {
        System.out.println("amount " + or.getAmount());
        System.out.println("created " + or.getCreatedAt());
        System.out.println("job order id " + or.getJobOrderId());
        
        logger.info("job order id " + or.getJobOrderId());
        
        System.out.println("or number " + or.getOrNumber());
        System.out.println("updated " + or.getUpdatedAt());
        System.out.println("user id " + or.getUserId());
        Session session = sessionFactory.getCurrentSession();
        session.save(or);
        return or;
    }
    
    
    
    /*
     * 	Gets the OfficialReceipt object
     * **/
    public OfficialReceipt getOR(int userId, int houseId, int orNumber) 
    {
    	logger.info("Getting OR for userId: " + userId);
    	
    	Session session = sessionFactory.getCurrentSession();
    	OfficialReceipt or = null;
		
		if (userId > 0 && houseId > 0 && orNumber > 0)
		{
			Query query = session.createQuery(GET_OR_HQL);
			
			query.setParameter("userId", userId);
			query.setParameter("houseId", houseId);
			query.setParameter("orNumber", orNumber);
			
			or = (OfficialReceipt) query.list().get(0);
		}
	
    	return or;
    }
    
    
    
    /**
     * Gets the OfficialReceiptHeader object
     * */
    public OfficialReceiptHeader getORHeader(int userId, int houseId, int orNumber) 
    {
    	logger.info("Getting OR Header for userId: " + userId);
    	
    	Session session = sessionFactory.getCurrentSession();
    	OfficialReceiptHeader orHeader = null;
		
		if (userId > 0 && houseId > 0 && orNumber > 0)
		{
			Query query = session.createQuery(GET_OR_HEADER_HQL);
			
			query.setParameter("userId", userId);
			query.setParameter("houseId", houseId);
			query.setParameter("orNumber", orNumber);
			
			orHeader = (OfficialReceiptHeader) query.list().get(0);
		}
    	
    	return orHeader;
    }
    
    
    /**
     * Gets the OfficialReceiptDetails object using its header_id and ref_num (OR jobOrderId) columns
     * */
    public OfficialReceiptDetails getORDetails(int headerId, int refNum)
    {
    	logger.info("Getting OR Details for OR Header ID: " + headerId + " and job order id: " + refNum);
    	
    	Session session = sessionFactory.getCurrentSession();
    	OfficialReceiptDetails orDetails = null;
		
		if (headerId > 0 && refNum > 0)
		{
			Query query = session.createQuery(GET_OR_DETAILS_HQL);
			
			query.setParameter("headerId", headerId);
			query.setParameter("refNum", refNum);
			
			orDetails = (OfficialReceiptDetails) query.list().get(0);
		}
    	
    	return orDetails;
    }
    
    
    
    
    /**
     * Gets the OfficialReceipt list for home owner view
     * */
    @SuppressWarnings("unchecked")
	public List<OfficialReceipt> getORList(int userId, int houseId) 
    {
    	logger.info("Getting OR List for userId: " + userId);
    	
    	Session session = sessionFactory.getCurrentSession();
    	List<OfficialReceipt> orList = null;
		
		if (userId > 0 && houseId > 0)
		{
			Query query = session.createQuery(GET_OR_LIST_HQL);
			
			query.setParameter("userId", userId);
			query.setParameter("houseId", houseId);
			
			orList = query.list();
		}
    	
    	return orList;
    }
    
    
    
    /**
     * Gets the OfficialReceipt using the OR number
     * */
    @SuppressWarnings("unchecked")
	public List<OfficialReceipt> getORByORNumber(int orNumber) 
    {
    	logger.info("Getting OR using OR number: " + orNumber);
    	System.out.println("Getting OR using OR number: " + orNumber);
    	
    	Session session = sessionFactory.getCurrentSession();
    	List<OfficialReceipt> orList = null;
		
		if (orNumber > 0)
		{
			Query query = session.createQuery(GET_OR_BY_OR_NUMBER_HQL);
			
			query.setParameter("orNumber", orNumber);
			
			orList = query.list();
		}
    	
    	return orList;
    }
    
    
    
    /**
     * Updates the given OR object
     * 
     * **/
    public OfficialReceipt updateOR(OfficialReceipt or) 
    {
    	logger.info("Updating OR: " + or.getOrNumber());
    	
    	Session session = sessionFactory.getCurrentSession();
    	session.update(or);
    
    	return or;
    }
    
    
}

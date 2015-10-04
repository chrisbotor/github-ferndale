/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.homeportal.dao.IAdjustmentDAO;
import com.homeportal.model.Adjustment;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/**
 *
 * @author racs
 */
@Repository
public class AdjustmentDaoImpl implements IAdjustmentDAO 
{
	private static Logger logger = Logger.getLogger(AdjustmentDaoImpl.class);

	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

	private static final String GET_ADJUSTMENTS_BY_USERID_HOUSEID_HQL = MessageBundle.getSqlProperty("get.adjustments.by.userid.houseid.hql");
	private static final String GET_ADJUSTMENT_BY_ID_HQL = MessageBundle.getSqlProperty("get.adjustment.by.id.hql");
	
	
    /**
     * Updates the amount paid for the association due
     * */
    public boolean updateAssocDuePaidAmount(double paidAmount, int requestId, double amount)
    {
    	boolean updated = false;
    	
    	final String UPDATE_ASSOCIATION_DUE_AMOUNT_PAID_SQL = SQLUtil.getUpdateAssociationDuePaidAmountSQL(paidAmount, requestId, amount);
    	
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery(UPDATE_ASSOCIATION_DUE_AMOUNT_PAID_SQL);
        int rowCount = query.executeUpdate();
        
        if (rowCount > 0)
        {
        	updated = true;
        }
        
        return updated;
    }


    /**
	 * Updates the adjustment (use to update the value of the paid_amount during billing payment)
	 * */
	public void updateAdjustment(Adjustment adjustment) throws HibernateException
	{
	     Session session = sessionFactory.getCurrentSession();
	     session.update(adjustment);
	}


    /**
     * Creates a new adjustment record in adjustment table
     * */
	@Override
	public Adjustment createAdjustment(Adjustment adjustment) throws SQLException
	{ 
		System.out.println("Saving adjustment or penalty...");
		logger.info("Saving adjustment or penalty...");
		
		Session session = sessionFactory.getCurrentSession();
		session.save(adjustment);
		
        return adjustment;
	}



	/**
	 * Gets all adjustments for a specific user and his/her house
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Adjustment> getAdjustmentsByUserIdAndHouseId(int userId, int houseId)
			throws SQLException {
		logger.info("DAO getting adjustments for user id: " + userId);
    	System.out.println("DAO getting adjustments for user id: " + userId);
    	
    	Session session = sessionFactory.getCurrentSession();
    	
		Query query = session.createQuery(GET_ADJUSTMENTS_BY_USERID_HOUSEID_HQL);
		query.setParameter("userId", userId);
		query.setParameter("houseId", houseId);
		
		return query.list();
	}
	
	
	/**
	 * Gets adjustment by ID
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public Adjustment getAdjustmentById(int id) throws SQLException
	{
		logger.info("DAO getting adjustment object with id: " + id);
    	System.out.println("DAO getting adjustment object with id: " + id);
    	
    	List<Adjustment> adjustments = null;
    	Adjustment adj = null;
    	
    	Session session = sessionFactory.getCurrentSession();
    	
		Query query = session.createQuery(GET_ADJUSTMENT_BY_ID_HQL);
		query.setParameter("id", id);
		
		adjustments = (List<Adjustment>) query.list();
		
		if (adjustments != null && adjustments.size() > 0)
		{
			adj = adjustments.get(0);
		}
		
		return adj;
	}
}

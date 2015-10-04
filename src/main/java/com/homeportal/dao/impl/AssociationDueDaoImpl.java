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

import com.homeportal.dao.IAssociationDueDAO;
import com.homeportal.model.Adjustment;
import com.homeportal.model.AssociationDue;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/**
 *
 * @author racs
 */
@Repository
public class AssociationDueDaoImpl implements IAssociationDueDAO 
{
	private static Logger logger = Logger.getLogger(AssociationDueDaoImpl.class);
	
	private static final String GET_ASSOC_DUE_BY_USERID_HOUSEID_HQL = MessageBundle.getSqlProperty("get.association.due.by.userid.houseid.hql");
	
	
	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

	
	
    /**
     * Updates the amount paid for the association due
     * */
    public boolean updateAssocDuePaidAmount(double paidAmount, int requestId, double amount)
    {
    	System.out.println("DAO Updating association due paid amount...");
    	logger.info("DAO Updating association due paid amount...");
    	
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
     * Gets the Association Due for a specific home owner
     * */
    @SuppressWarnings("unchecked")
    public AssociationDue getAssociationDue(int userId, int houseId)
    {
    	System.out.println("DAO Getting the association due for user id: " + userId);
    	logger.info("DAO Getting the association due for user id: " + userId);
    	
    	Session session = sessionFactory.getCurrentSession();
    	List<AssociationDue> assocDueList = null;
    	AssociationDue assocDue = null;
		
		Query query = session.createQuery(GET_ASSOC_DUE_BY_USERID_HOUSEID_HQL);
		query.setParameter("userId", userId);
		query.setParameter("houseId", houseId);
		
		assocDueList = query.list();
		
		if (assocDueList != null && assocDueList.size() > 0)
		{
			assocDue = assocDueList.get(0);
		}
		
    	return assocDue;
    }



    /**
     * 	Updates an assoc due record
     * */
	@Override
	public void updateAssocDue(AssociationDue assocDue) throws HibernateException
	{
		Session session = sessionFactory.getCurrentSession();
		session.update(assocDue);
	}
	
	
	 /**
     *	Creates a new association due record in association_due table
     * */
	@Override
	public AssociationDue saveAssociationDue(AssociationDue assocDue) throws SQLException
	{ 
		logger.info("Saving association due to the database...");
		System.out.println("Saving association due to the database...");
		
		Session session = sessionFactory.getCurrentSession();
		
		if (session != null)
		{
			System.out.println("session: " + session);
		}
		
        session.save(assocDue);
        return assocDue;
	}   
}

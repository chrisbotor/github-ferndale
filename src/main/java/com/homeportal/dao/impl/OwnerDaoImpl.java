/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.homeportal.bean.OwnerBean;
import com.homeportal.dao.IOwnerDAO;
import com.homeportal.model.Adjustment;
import com.homeportal.model.Owner;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/**
 *
 * @author Peter
 */
@Repository
public class OwnerDaoImpl implements IOwnerDAO
{
	private static Logger logger = Logger.getLogger(OwnerDaoImpl.class);
	
	private static final String GET_OWNER_BY_OWNER_ID_HQL = MessageBundle.getSqlProperty("get.owner.by.owner.id.hql");
	private static final String GET_ALL_OWNER_SQL = MessageBundle.getSqlProperty("get.all.owner.sql");
	private static final String GET_ALL_OWNER_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.owner.sql.scalar");
	private static final String UPDATE_SINGLE_OWNER_SQL = MessageBundle.getSqlProperty("update.single.owner.sql");
	private static final String GET_OWNER_BY_USER_ID_HQL = MessageBundle.getSqlProperty("get.owner.by.user.id.hql");
	
	// private static final String GET_ALL_OWNER_HQL = MessageBundle.getSqlProperty("get.all.owner.hql");
	// private static final String GET_OWNER_BY_USER_ID_HQL = MessageBundle.getSqlProperty("get.owner.by.user.id.hql");
	
	
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    
    
    /**
	 * Get List of ALL owners from database
	 * @return list of all owners
	 */
    @SuppressWarnings("unchecked")
	public List<Owner> getAllOwner() throws SQLException
    {
    	logger.info("DAO Getting ALL owner from the database...");
    	System.out.println("DAO Getting ALL owner from the database...");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		try
		{
			scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_OWNER_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_ALL_OWNER_SQL);
				query.setResultTransformer(Transformers.aliasToBean(OwnerBean.class));
			}
		}
		catch(Exception ex)
        {
			logger.error("Error in getting all owner " + ex.getMessage());
			System.out.println("Error in getting all owner " + ex.getMessage());
        }
		
		return query.list();
	}
    
    
    
    public List<Owner> getOwners() {
        // Retrieve session from Hibernate
        
	Session session = sessionFactory.getCurrentSession();
        Query query = null;
        try{
        String sql = "select * from owner";
        query = session.createSQLQuery(sql).addEntity(Owner.class);
	System.out.println("Owner getowner list is : " + query.list().size());
        }catch(Exception e)
        {
            System.out.println("Error in get Owners " + e.getCause());
        }
	return query.list();
    }
    

     public List<OwnerBean> getFullname(){
         Session session = sessionFactory.getCurrentSession();
	//String sql = "select id, firstname || ' ' || middlename || ' ' || lastname as fullName from OWNER";
        String sql = "select id, Concat(firstname, ' ' , lastname) as fullName from owner";
        Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("fullName")
                .setResultTransformer(Transformers.aliasToBean(OwnerBean.class));
	System.out.println("Owner getowner list is : " + query.list().size());
	return query.list();
     }

    /**
	 * Set the status to false of owner with the id passed as parameter
	 * @param id
    */    
    public void deleteOwners(int id) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        Owner o = (Owner) session.get(Owner.class, id);
	// Delete
	session.delete(o);
	// Create a Hibernate query (HQL)
        //String hql = "update Owner o set o.status = 0 WHERE o.id = :id";
        //Query query = session.createQuery(hql);
        //query.setInteger("id",id);
        //int rowCount = query.executeUpdate();
	//System.out.println("Owner deleteowner list is : " + rowCount);
    }

    /**
	 * Create a new Owner on the database or
	 * Update owner
	 * @param owner
	 * @return contact added or updated in DB
	 */
    public Owner saveOwners(Owner owner) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
	session.save(owner);
	return owner;
    }

    public Owner updateOwner(Owner o) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        
        String sql = "update owner set "
                + "birthdate=:birthdate, "
                + "civil_status=:civil_status, "
                + "email_address=:email_address, "
                + "home_number=:home_number, "
                + "mobile_number=:mobile_number, "
                + "work_address=:work_address, "
                + "work_email=:work_email, "
                + "work_landline=:work_landline, "
                + "work_mobile=:work_mobile, "
                + "work_name=:work_name, "
                + "updated_at=:updated_at "
                + "where user_id=:user_id";
        Query query = session.createSQLQuery(sql);
        query.setString("birthdate", o.getBirthdate());
        query.setString("civil_status", o.getCivilStatus());
        query.setString("email_address", o.getEmailAddress());
        query.setString("home_number", o.getHomeNumber());
        query.setString("mobile_number", o.getMobileNumber());
        query.setString("work_address", o.getWorkAddress());
        query.setString("work_email", o.getWorkEmail());
        query.setString("work_landline", o.getWorkLandline());
        query.setString("work_mobile", o.getWorkMobile());
        query.setString("work_name", o.getWorkName());
        query.setDate("updated_at", new Date());
        query.setInteger("user_id", o.getUserId());
        int rowCount = query.executeUpdate();
	return o;
    }

    public List<Owner> getOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select * from owner o where o.user_id = :id";
        List<Owner> list = session.createSQLQuery(sql)
        .addEntity(Owner.class)
        .setParameter("id", id).list();
        
        return list;
        }
    
    
    /**
     * Gets an owner using its ID
     * */
    @Override
	public Owner getOwnerByOwnerId(int ownerId)
    {
		logger.info("Getting owner using ownerId: " +  ownerId);
		
		Session session = sessionFactory.getCurrentSession();
		Owner owner = null;
		
		if (ownerId > 0)
		{
			Query query = session.createQuery(GET_OWNER_BY_OWNER_ID_HQL);
			
			query.setParameter("ownerId", ownerId);
			
			owner = (Owner) query.list().get(0);
		}
	
		return owner;
	}

    
    
    @Override
	public Owner updateSingleOwner(Owner owner)
    {
    	System.out.println("Updating owner with ID: " +  owner.getId() + " and owner User ID: " + owner.getUserId());
		logger.info("Updating owner with ID: " +  owner.getId());
		
		Session session = sessionFactory.getCurrentSession();
		// Transaction tx = session.beginTransaction();
				
		// session.update(owner);
		
		Query query = session.createSQLQuery(UPDATE_SINGLE_OWNER_SQL);
		
        query.setString("birthDate", owner.getBirthdate());
        query.setString("civilStatus", owner.getCivilStatus());
        query.setString("emailAddress", owner.getEmailAddress());
        query.setString("homeNumber", owner.getHomeNumber());
        query.setString("mobileNumber", owner.getMobileNumber());
        query.setString("workAddress", owner.getWorkAddress());
        query.setString("workEmail", owner.getWorkEmail());
        query.setString("workLandline", owner.getWorkLandline());
        query.setString("workMobile", owner.getWorkMobile());
        query.setString("workName", owner.getWorkName());
        query.setDate("updatedAt", new Date());
        query.setInteger("ownerId", owner.getId());
        query.setInteger("userId", owner.getUserId());
        
        int rowCount = query.executeUpdate();
       // tx.commit();
	
        return owner;
	}
    
 
    
	@Override
	public Owner getOwnerByUserId(int userId) 
	{
		logger.info("Getting owner using userId: " +  userId);
		
		Session session = sessionFactory.getCurrentSession();
		Owner owner = null;
		
		if (userId > 0)
		{
			Query query = session.createQuery(GET_OWNER_BY_USER_ID_HQL);
			
			query.setParameter("userId", userId);
			
			owner = (Owner) query.list().get(0);
		}
	
		return owner;
	}
	
	 /**
	  * Updates the adjustment (use to update the value of the paid_amount during billing payment)
	  * PROPER WAY OF UPDATING AN OBJECT!
	* */
	public void update(Owner owner) throws HibernateException
	{
		System.out.println("DAO updating Owner...");
		
		Session session = sessionFactory.getCurrentSession();
		session.update(owner);
	}
}

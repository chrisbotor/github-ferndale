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
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.homeportal.bean.HouseBean;
import com.homeportal.bean.LesseeBean;
import com.homeportal.dao.ILesseeDAO;
import com.homeportal.model.Lessee;
import com.homeportal.model.Owner;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/**
 *
 * @author PSP36488
 */
@Repository
public class LesseeDaoImpl implements ILesseeDAO
{
	private static Logger logger = Logger.getLogger(LesseeDaoImpl.class);
	
	private static final String GET_ALL_LEESEE_SQL = MessageBundle.getSqlProperty("get.all.lessee.sql");
	private static final String GET_ALL_LEESEE_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.lessee.sql.scalar");
	private static final String GET_LESSEE_BY_USER_ID_HOUSE_ID_HQL = MessageBundle.getSqlProperty("get.lessee.by.user.id.and.house.id.hql");
	
	
	
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	
	/**
	 * 
	 * 
	 * */
    @SuppressWarnings("unchecked")
    public List<LesseeBean> getLesseeList()
    {
         // Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		String sql = "select h.id, Concat(l.firstname, ' ' , l.lastname) as fullname," +
				"Concat(h.addr_number, ' ' , h.addr_street) as address, m.move_in as moveinDate,m.move_out as moveoutDate " +
				"from house h join leesee l on h.id = l.house_id join move_in_out m on m.user_id = l.user_id";
        Query query = session.createSQLQuery(sql)
        		 .addScalar("fullname")
                 .addScalar("address")
                 .addScalar("moveInDate")
                 .addScalar("moveOutDate")
                 .setResultTransformer(Transformers.aliasToBean(LesseeBean.class));
        return query.list();
    }

    
    
    /**
     * 
     * */
    public void deleteLessee(int id)
    {
         // Retrieve session from Hibernate
    	Session session = sessionFactory.getCurrentSession();
        Lessee l = (Lessee) session.get(Lessee.class, id);
        // Delete
        session.delete(l);
        // Create a Hibernate query (HQL)
        //String hql = "update Leesee l set l.status = 'Inactive' WHERE l.id = :id";
        //Query query = session.createQuery(hql);
        //query.setInteger("id",id);
        //int rowCount = query.executeUpdate();
        //System.out.println("Owner deleteLeesees list is : " + rowCount);
    }

    
    
    /**
     * 
     * */
   /* public Lessee saveLessee(Lessee lessee)
    {
        // Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		session.save(lessee);
		return lessee;
    }*/

    
    
    /**
     * 
     * */
    public Lessee updateLeesee(Lessee leesee)
    {
        // Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Save
		session.update(leesee);
		return leesee;
    }

    
    
    /**
     * 
     * */
    public List<HouseBean> getOwnerInfo() {
        Session session = sessionFactory.getCurrentSession();
        
        String sql = "select o.user_id as userId, Concat(h.addr_number, ' ', h.addr_street) as address from owner o join house h on h.owner_id = o.id ";
        Query query = session.createSQLQuery(sql)
                    .addScalar("userId")
                    .addScalar("address")
                    .setResultTransformer(Transformers.aliasToBean(HouseBean.class));
        System.out.println("query list " + query.list().size());
        return query.list();
    }

    public List<Lessee> getLesseeById(String userId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select * from leesee l where l.user_id = :id";
        List<Lessee> list = session.createSQLQuery(sql)
        .addEntity(Lessee.class)
        .setParameter("id", userId).list();
        
        return list;
    }
    
    
    /**
     * Gets an owner using its ID
     * */
    @Override
	public Lessee getLesseeByUserIdHouseId(int userId, int houseId)
    {
		logger.info("DAO Getting lessee using userId: " +  userId + " and houseId: " + houseId);
		
		Session session = sessionFactory.getCurrentSession();
		Lessee lessee = null;
		
		if (userId > 0)
		{
			Query query = session.createQuery(GET_LESSEE_BY_USER_ID_HOUSE_ID_HQL);
			query.setParameter("userId", userId);
			query.setParameter("houseId", houseId);
			
			lessee = (Lessee) query.list().get(0);
		}
	
		return lessee;
	}
    
    

    public Lessee updateLesseeById(Lessee l) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        
        String sql = "update leesee set "
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
        query.setString("birthdate", l.getBirthdate());
        query.setString("civil_status", l.getCivilStatus());
        query.setString("email_address", l.getEmailAddress());
        query.setString("home_number", l.getHomeNumber());
        query.setString("mobile_number", l.getMobileNumber());
        query.setString("work_address", l.getWorkAddress());
        query.setString("work_email", l.getWorkEmail());
        query.setString("work_landline", l.getWorkLandline());
        query.setString("work_mobile", l.getWorkMobile());
        query.setString("work_name", l.getWorkName());
        query.setDate("updated_at", new Date());
        query.setInteger("user_id", l.getUserId());
        int rowCount = query.executeUpdate();
	return l;
    }
    
    
    /**
     * Saves the newly created Leesee
     * */
    public Lessee saveLessee(Lessee lessee)
    {
    	Session session = sessionFactory.getCurrentSession();
        session.save(lessee);
        return lessee;
    }
    
    
    /**
   	 * Gets the list of ALL lessee from the database
   	 * @return list of all lessee
   	 */
    @SuppressWarnings("unchecked")
   	public List<Lessee> getAllLessee() throws SQLException
   	{
    	logger.info("DAO Getting ALL lessee from the database...");
    	System.out.println("DAO Getting ALL lessee from the database...");
   		
   		Session session = sessionFactory.getCurrentSession();
   		Query query = null;
   		List<String> scalarPropsList = null;
   		
   		try
   		{
   			scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_LEESEE_SQL_SCALAR);
   			
   			if (scalarPropsList != null)
   			{
   				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_ALL_LEESEE_SQL);
   				query.setResultTransformer(Transformers.aliasToBean(LesseeBean.class));
   			}
   		}
   		catch(Exception ex)
   		{
   			logger.error("Error in getting all lessee " + ex.getMessage());
   			System.out.println("Error in getting all lessee " + ex.getMessage());
   		}
   		
   		return query.list();
   	}


    
    

	@Override
	public List<LesseeBean> getLessee() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	 /**
	  * Updates the lessee object
	  * PROPER WAY OF UPDATING AN OBJECT!
	* */
	@Override
	public void updateLessee(Lessee lessee)
	{
		logger.info("DAO updating lessee with user ID " + lessee.getUserId());
		System.out.println("DAO updating lessee with user ID " + lessee.getUserId());
		
		Session session = sessionFactory.getCurrentSession();
		session.update(lessee);	
	}
    
}

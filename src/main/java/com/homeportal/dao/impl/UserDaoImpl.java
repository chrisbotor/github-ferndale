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

import com.homeportal.bean.RequestorBean;
import com.homeportal.bean.UserBean;
import com.homeportal.dao.IUserDAO;
import com.homeportal.model.AssociationDue;
import com.homeportal.model.User;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;
import com.homeportal.util.ValidationUtil;


@Repository
public class UserDaoImpl implements IUserDAO
{
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	private static final String GET_USER_BY_USERNAME_AND_PASSWORD_HQL = MessageBundle.getSqlProperty("get.user.by.username.and.password.hql");
	private static final String GET_USER_BY_USERNAME_HQL = MessageBundle.getSqlProperty("get.user.by.username.hql");
	private static final String GET_USER_BY_USER_ID_HQL = MessageBundle.getSqlProperty("get.user.by.user.id.hql");
	
	// private static final String GET_ALL_ACTIVE_USER_HQL = MessageBundle.getSqlProperty("get.all.active.user.hql");
	private static final String GET_ALL_ACTIVE_USER_SQL = MessageBundle.getSqlProperty("get.all.active.user.sql");
	private static final String GET_ALL_ACTIVE_USER_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.active.user.sql.scalar");
	
	
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	
	/**
	 *  Gets the user using the userName and password during login
	 * */
	public User getUserByUsernamePassword(final String username, final String password)
	{
		System.out.println("Getting user by username: " +  username);
		logger.info("Getting user by username: " +  username);
		
	    Session session = sessionFactory.getCurrentSession();
	    User user = null;
            
		Query query = session.createQuery(GET_USER_BY_USERNAME_AND_PASSWORD_HQL);
				
		query.setParameter("username", username);
		query.setParameter("password", password);
				
		user = (User) query.list().get(0);
		
		if (user != null)
		{
			System.out.println("User: " + user.getUsername());
		}
		else
		{
			System.out.println("User is NULL.");
		}
		
		return user;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<User> getUsers() 
	{
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
                String sql = "select * from users";
                Query query = session.createSQLQuery(sql).addEntity(User.class);
		System.out.println("getUsers list is : " + query.list().size());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<UserBean> getHouseIdAndOwner(int userId, int roleId)
	{
		System.out.println("========== inside getHouseIdAndOwner() method...");
		System.out.println("userId params: " + userId);
		System.out.println("roleId params: " + roleId);
    	
    	List<UserBean> beanList = null;

        Session session = sessionFactory.getCurrentSession();
        /*String sql = "select h.id as houseId,o.firstname || ' ' || o.middlename || ' ' || o.lastname as owner " +
                "from owner o join house h on h.owner_id = o.id where o.user_id ="+userId;*/
        
        StringBuilder sb = new StringBuilder();
        //String sqlToPass = null;
        
        if (roleId == 3)
        {
        	System.out.println("fetching owner details...");
        	
        	sb.append("select h.id as houseId, Concat(o.firstname, ' ' , o.lastname) as owner, Concat(h.addr_number, ' ' , h.addr_street) as ownerAddress ");
        	sb.append("from owner o join house h on h.owner_id = o.id where o.user_id =");
        	sb.append(userId);
        
        	logger.debug("Created query for Owner user...");
        }
        else if (roleId == 4)
        {
        	System.out.println("fetching leesee details...");
        	
        	sb.append("select h.id as houseId, Concat(l.firstname, ' ' , l.lastname) as owner, Concat(h.addr_number, ' ' , h.addr_street) as ownerAddress ");
        	sb.append("from house h join leesee l on l.house_id = h.id where l.user_id =");
        	sb.append(userId);
        }

        // sqlToPass = sb.toString();
        
        logger.debug("Sending sqlToPass to session.createSQLQuery...");
        Query query = session.createSQLQuery(sb.toString())
                .addScalar("houseId")
                .addScalar("owner")
                .addScalar("ownerAddress")
                .setResultTransformer(Transformers.aliasToBean(UserBean.class));
        
        logger.debug("RACS Found " + query.list().size() + " number of house");
        
        beanList = (List<UserBean>) query.list();
        
        for (UserBean bean : beanList)
        {
        	logger.debug("RACS UserBean houseID: " + bean.getHouseId());
        }
        
        return query.list();
    }        
        
	
	
	public List<RequestorBean> getRequestors() 
	{
		logger.info("Getting all home owners.");
		
        Session session = sessionFactory.getCurrentSession();
        
        String sql = SQLUtil.getRequestorSQL();
        
        Query query = session.createSQLQuery(sql)
                    .addScalar("id")
                    .addScalar("userId")
                    .addScalar("requestor")
                    .setResultTransformer(Transformers.aliasToBean(RequestorBean.class));
        
        List<RequestorBean> beans = (List<RequestorBean>) query.list();
        
        /*	for (RequestorBean rb : beans)
        	{
        		logger.info("=================== HOUSE ID: " + rb.getId());
            	logger.info("=================== USER ID: " + rb.getUserId());
        	}*/
       
            
            return query.list();
        }
	
	

	
	/*public UserBean getUser(final String username, final String password) {
	    Session session = sessionFactory.getCurrentSession();
            
	    String sql = "select id, username, password, role_id"
                    + " from users u where u.username='"+username+"' and password='"+password+"'";
            
	    Query query = session.createSQLQuery(sql)
                    .addScalar("id")
                    .addScalar("username")
                    .addScalar("password")
                    .addScalar("role_id")
                    .setResultTransformer(Transformers.aliasToBean(UserBean.class));
                    return (UserBean) query.list().get(0);
	}*/

	public void deleteUser(int id) {
		 Session session = sessionFactory.getCurrentSession();
		 User u = (User) session.get(User.class, id);
	     session.delete(u);
	}

	public User saveUser(User user) {
	Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
	}

	/**
	 * Updates the user (use to de-activate a user or reset its password by admin)
	 * */
	public void updateUser(User user) throws HibernateException
	{
	     Session session = sessionFactory.getCurrentSession();
	     session.update(user);
	}
	
	
	 
    public User changePassword(User user)
	{
	     Session session = sessionFactory.getCurrentSession();
             String sql = "update users u set u.password = :password, u.status =:status, u.updated_at=:updatedAt where u.username = :uname";
	     Query query = session.createSQLQuery(sql);
             query.setString("password", user.getPassword());
             query.setString("status", user.getStatus());
             query.setDate("updatedAt", new Date());
             query.setString("uname", user.getUsername());
             int rowCount = query.executeUpdate();
	     return user;
	}

	
    @Override
	public User getUserByUserId(int userId) 
    {
		logger.info("Getting user by userId: " +  userId);
			
		Session session = sessionFactory.getCurrentSession();
		User user = null;
			
		if (userId > 0)
		{
			Query query = session.createQuery(GET_USER_BY_USER_ID_HQL);
				
			query.setParameter("userId", userId);
				
			user = (User) query.list().get(0);
		}
		
			return user;
	}
    
    
    
    @Override
	public User getUserByUsername(String username) 
    {
		logger.info("Getting user by username: " +  username);
			
		Session session = sessionFactory.getCurrentSession();
		User user = null;
			
		if (ValidationUtil.hasValue(username))
		{
			Query query = session.createQuery(GET_USER_BY_USERNAME_HQL);
				
			query.setParameter("username", username);
				
			user = (User) query.list().get(0);
		}
		
		return user;
	}
		
		

	/**
	 * Get List of ALL active users from database
	 * @return list of all active users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUser() throws SQLException
	{
		logger.info("Getting ALL active users from the database...");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_ACTIVE_USER_SQL_SCALAR);
			
		if (scalarPropsList != null)
		{
			query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_ALL_ACTIVE_USER_SQL);
			query.setResultTransformer(Transformers.aliasToBean(UserBean.class));
		}
		
		if (query.list().get(0) != null)
		{
			UserBean bean = (UserBean) query.list().get(0);
			System.out.println("Testing User full name for de-activate user: " + bean.getFullName());
			System.out.println("Username: " + bean.getUsername());
		}
		
		return query.list();
	}
	
	
	

	/**
	 * Deactivate this specific user
	 * @return number of updated row
	 */
	@SuppressWarnings("unchecked")
	public void deactivateUser(int userId, String userName) throws SQLException
	{
		logger.info("#### De-activating user  " + userName);
		//logger.info("USER STATUS: " + user.getStatus());
		
	/*	Session session = sessionFactory.getCurrentSession();
		session.update(user);*/
		
		Session session = sessionFactory.getCurrentSession();
        String sql = "update users u set u.status = 'I' where u.id = :id and u.username = :uname";
  
        Query query = session.createSQLQuery(sql);
        
        // query.setString("status", user.getStatus());
        query.setInteger("id", userId);
        query.setString("uname", userName);
       
        int rowCount = query.executeUpdate();
        
        //return user;
	}
	
}

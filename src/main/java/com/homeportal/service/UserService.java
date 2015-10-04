package com.homeportal.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.RequestorBean;
import com.homeportal.bean.UserBean;
import com.homeportal.dao.impl.UserDaoImpl;
import com.homeportal.model.AssociationDue;
import com.homeportal.model.User;
import com.homeportal.util.ValidationUtil;


@Service
public class UserService 
{
	private static Logger logger = Logger.getLogger(UserService.class);
	
	private UserDaoImpl userDaoImpl;
	
	
	/**
	 * Get a user
	 * @return
	 */
	@Transactional(readOnly=true)
	public User getUserByUsernamePassword(String username, String password){
		User user = null;
		
		if(username != null && password != null){
			user = userDaoImpl.getUserByUsernamePassword(username, password);
		}
		
		return user;
	}
	
	
	

    
    /**
	 * Get all users
	 * @return
	 */
	/*@Transactional(readOnly=true)
	public List<User>  getUsersList(){
            return userDaoImpl.getUsers();
	}*/
	
	

	/**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserBean> getHouseIdAndOwner(int userId, int roleId) {
        return userDaoImpl.getHouseIdAndOwner(userId, roleId);
    }
    
    @Transactional(readOnly=true)
    public List<RequestorBean> getRequestors(){
        return userDaoImpl.getRequestors();
    }

    @Transactional
    public User addUser(User u) {
            return userDaoImpl.saveUser(u);
     }
    
    
    @Transactional
    public User changePassword(User u) {
            return userDaoImpl.changePassword(u);
     }

    
    
    @Transactional
    public boolean updateUser(User u) 
    {
    	try
    	{
    		userDaoImpl.updateUser(u);
    		return true;
    	}
    	catch(HibernateException ex)
    	{
    		logger.error("Error encountered in updating user. " + ex.getMessage());
    		throw new HibernateException("Error encountered in updating user. ", ex);
    	}
   }
    
    
    /**
	 * Get a user by its ID
	 * @return User
	 */
	@Transactional(readOnly=true)
	public User getUserByUserId(int userId)
	{
		User user = null;
		
		if(userId > 0)
		{
			user = userDaoImpl.getUserByUserId(userId);
		}
		 
		return user;
	}
   
	
	 /**
	  * Get a user by its username
	  * @return User
	  */
	@Transactional(readOnly=true)
	public User getUserByUsername(String username)
	{
		User user = null;
			
		if(ValidationUtil.hasValue(username))
		{
			user = userDaoImpl.getUserByUsername(username);
		}
			 
		return user;
	}
	
	
	/**
	 * Get all owner for the Owner Search
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<User> getAllUser() throws SQLException
	{
		return userDaoImpl.getAllUser();
	}
     
	
	/**
	 * Get all owner for the Owner Search
	 * @return
	 */
	@Transactional
	public void deactivateUser(int userId, String userName) throws SQLException
	{
		logger.info("******* De-activating user...");
		userDaoImpl.deactivateUser(userId, userName);
	}
	
	
	/**
     * @param DaoImpl the HouseDaoImpl to set
     */
    @Autowired
    public void setUserDAOImpl(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

   

}
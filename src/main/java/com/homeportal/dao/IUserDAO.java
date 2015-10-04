package com.homeportal.dao;

import java.sql.SQLException;
import java.util.List;

import com.homeportal.bean.RequestorBean;
import com.homeportal.bean.UserBean;
import com.homeportal.model.User;

public interface IUserDAO 
{
	 User getUserByUsernamePassword(String username, String password);
	 List<User> getUsers();
	 void deleteUser(int id);
	 User saveUser(User user);
	 void updateUser(User user);
     List<UserBean> getHouseIdAndOwner(int userId, int roleId);
     User changePassword(User user);
     List<RequestorBean> getRequestors();
     User getUserByUserId(int userId);
     User getUserByUsername(String username);
     List<User> getAllUser() throws SQLException;
}

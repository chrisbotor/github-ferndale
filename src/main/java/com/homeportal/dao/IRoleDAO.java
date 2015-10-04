package com.homeportal.dao;

import java.util.List;

import com.homeportal.model.UserRole;

public interface IRoleDAO {
	
	   List<UserRole> getRoles();
	    void deleteRole(int id);
	    UserRole saveRole(UserRole role);
	    UserRole updateRole(UserRole role);

}

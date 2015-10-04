package com.homeportal.dao.impl;

import com.homeportal.dao.IRoleDAO;
import com.homeportal.model.UserRole;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RoleDaoImpl implements IRoleDAO {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<UserRole> getRoles() {
		//Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery("from user_roles");
		System.out.println("getRoles list is : " + query.list().size());
		return query.list();
	}

	public void deleteRole(int id) {
		Session session = sessionFactory.getCurrentSession();
		UserRole r = (UserRole) session.get(UserRole.class, id);
	    session.delete(r);
	}

	public UserRole saveRole(UserRole role) {
		Session session = sessionFactory.getCurrentSession();
        session.save(role);
        return role;
	}

	public UserRole updateRole(UserRole role) {
		Session session = sessionFactory.getCurrentSession();
	    session.update(role);
	    return role;
	}

}

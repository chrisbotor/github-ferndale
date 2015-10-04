/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.dao.IOccupant;
import com.homeportal.model.Occupant;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Peter
 */
@Repository
public class OccupantDaoImpl implements IOccupant{

    @Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

    public List<Occupant> getOccupants() {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from occupant");
	System.out.println("getOccupants list is : " + query.list().size());
	return query.list();
    }

    public void deleteOccupants(int id) {
        Session session = sessionFactory.getCurrentSession();
        Occupant v = (Occupant) session.get(Occupant.class, id);
        session.delete(v);
    }

    public Occupant saveOccupants(Occupant occupant) {
        Session session = sessionFactory.getCurrentSession();
        session.save(occupant);
        return occupant;
    }

    public Occupant updateOccupant(Occupant occupant) {
        Session session = sessionFactory.getCurrentSession();
        session.update(occupant);
        return occupant;
    }
    
}

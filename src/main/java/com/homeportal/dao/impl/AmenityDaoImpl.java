/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao.impl;

import com.homeportal.dao.IAmenityDAO;
import com.homeportal.model.Amenity;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;


/**
 *
 * @author PSP36488
 */
@Repository
public class AmenityDaoImpl implements IAmenityDAO {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public List<Amenity> getAmenities() {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        String sql = "select * from amenity";
        Query query = session.createSQLQuery(sql).addEntity(Amenity.class);
	System.out.println("getAmenity list is : " + query.list().size());
	return query.list();
    }

    public void deleteAmenity(int id) {
         Session session = sessionFactory.getCurrentSession();
        Amenity v = (Amenity) session.get(Amenity.class, id);
        session.delete(v);
    }

    public Amenity saveAmenity(Amenity amenity) {
        Session session = sessionFactory.getCurrentSession();
        session.save(amenity);
        return amenity;
    }

    public Amenity updateAmenity(Amenity amenity) {
        Session session = sessionFactory.getCurrentSession();
        session.update(amenity);
        return amenity;
    }

}

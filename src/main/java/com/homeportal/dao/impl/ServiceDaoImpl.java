/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao.impl;

import com.homeportal.dao.IServiceDAO;
import com.homeportal.model.Services;
import com.homeportal.util.SQLUtil;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;


/**
 *
 * @author PSP36488
 */
@Repository
public class ServiceDaoImpl implements IServiceDAO
{
	private static Logger logger = Logger.getLogger(AmenitiesRequestDaoImpl.class);

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public List<Services> getServices() {
        Session session = sessionFactory.getCurrentSession();
        
        String sql = "select * from service";
        Query query = session.createSQLQuery(sql).addEntity(Services.class);
        
        System.out.println("Got number of services recorded in Service table: " + query.list().size());
        
        return query.list();
    }

    public void deleteServices(int id) {
         Session session = sessionFactory.getCurrentSession();
        Services v = (Services) session.get(Services.class, id);
        session.delete(v);
    }

    public Services saveServices(Services services) {
        Session session = sessionFactory.getCurrentSession();
        session.save(services);
        return services;
    }

    public Services updateServices(Services services) {
        Session session = sessionFactory.getCurrentSession();
        session.update(services);
        return services;
    }
    
    
    /**
	 * Checks whether there is an overlapping date and time when creating an Amenity or Service request
	 * 
	 * */
	public boolean validateServiceRequestAndDateOverlap(int serviceReqId, String preferredDate)
	{
		final String CHECK_SERVICE_REQUEST_SQL = SQLUtil.getCheckServiceRequestSQL(serviceReqId, preferredDate);
		Session session = sessionFactory.getCurrentSession();
        
        System.out.println("Checking for Service Request and Date Overlap...");
        logger.info("Checking for Service Request and Date Overlap...");
        
        boolean isValid = false;
        
        int withOverlap = (Integer) session.createSQLQuery(CHECK_SERVICE_REQUEST_SQL).uniqueResult();
        
        System.out.println("SERVICE WITH OVERLAP? " + withOverlap);
        logger.info("SERVICE WITH OVERLAP? " + withOverlap);
        
        if (withOverlap == 1)
        {
        	isValid = true;
        }
		
		return isValid;
	}

}

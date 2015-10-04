/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.dao.IOrSeriesDAO;
import com.homeportal.model.OrSeries;
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
public class OrSeriesDaoImpl implements IOrSeriesDAO{
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public List<OrSeries> getSeriesNumber(){
        Session session = sessionFactory.getCurrentSession();
        String sql = "select * from or_series ";
        Query query = session.createSQLQuery(sql).addEntity(OrSeries.class);
        return query.list();
    }

    public OrSeries updateSeriesNumber(OrSeries os) {
        Session session = sessionFactory.getCurrentSession();
        session.update(os);
        return os;
    }

    public int updateCurrentNumber(int current) {
        System.out.println("went to update current number............." + current);
        Session session = sessionFactory.getCurrentSession();
        String sql = "update or_series os set os.current =:current, os.updated_At=Now() "; 
        Query query = session.createSQLQuery(sql);
        query.setInteger("current", current);
        int rowCount = query.executeUpdate();
        return current;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.dao.IJobOrderDAO;
import com.homeportal.model.JobOrder;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Peter
 */
@Repository
public class JobOrderDaoImpl implements IJobOrderDAO{
    
    @Resource(name="sessionFactory")
     private SessionFactory sessionFactory;

    public List<JobOrder> getJobOrder() {
        Session session = sessionFactory.getCurrentSession();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JobOrder saveJobOrder(JobOrder jobOrder) {
        Session session = sessionFactory.getCurrentSession();
        session.save(jobOrder);
        return jobOrder;
    }

    public JobOrder updateJobOrder(JobOrder jobOrder) {
        Session session = sessionFactory.getCurrentSession();
        session.update(jobOrder);
        return jobOrder;
    }
    
}

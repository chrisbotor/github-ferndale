/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.dao.IMoveInOutDAO;
import com.homeportal.model.MoveInOut;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Peter
 */
@Repository
public class MoveInOutDaoImpl implements IMoveInOutDAO{
    
    @Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

    public MoveInOut saveMoveInOut(MoveInOut mio) {
        Session session = sessionFactory.getCurrentSession();
        session.save(mio);
        return mio;
    }

    public MoveInOut updateMoveInOut(MoveInOut mio) {
        Session session = sessionFactory.getCurrentSession();
        session.update(mio);
        return mio;
    }
    
}

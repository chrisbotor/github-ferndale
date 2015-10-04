/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.dao.impl.MoveInOutDaoImpl;
import com.homeportal.model.MoveInOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class MoveInOutService {
    
    private MoveInOutDaoImpl inOutDaoImpl;
    
    @Transactional
    public MoveInOut addMoveIn(MoveInOut mio) {
            return inOutDaoImpl.saveMoveInOut(mio);
     }

    /**
     * @param inOutDaoImpl the inOutDaoImpl to set
     */
    @Autowired
    public void setInOutDaoImpl(MoveInOutDaoImpl inOutDaoImpl) {
        this.inOutDaoImpl = inOutDaoImpl;
    }
    
}

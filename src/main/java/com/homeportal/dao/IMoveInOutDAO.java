/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.model.MoveInOut;

/**
 *
 * @author Peter
 */
public interface IMoveInOutDAO {
    MoveInOut saveMoveInOut(MoveInOut mio);
    MoveInOut updateMoveInOut(MoveInOut mio);
}

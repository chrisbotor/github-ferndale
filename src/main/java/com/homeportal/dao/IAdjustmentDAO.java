/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import java.sql.SQLException;
import java.util.List;

import com.homeportal.model.Adjustment;



/**
 *
 * @author racs
 */
public interface IAdjustmentDAO
{
	Adjustment createAdjustment(Adjustment adjustment) throws SQLException;
	List<Adjustment> getAdjustmentsByUserIdAndHouseId(int userId, int houseId) throws SQLException;
	public Adjustment getAdjustmentById(int id) throws SQLException;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import java.sql.SQLException;

import com.homeportal.model.AssociationDue;

/**
 *
 * @author racs
 */
public interface IAssociationDueDAO
{
	boolean updateAssocDuePaidAmount(double paidAmount, int requestId, double amount);
	
	AssociationDue getAssociationDue(int userId, int houseId);
	
	void updateAssocDue(AssociationDue assocDue);
	
	public AssociationDue saveAssociationDue(AssociationDue assocDue) throws SQLException;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import java.sql.SQLException;
import java.util.List;
import com.homeportal.model.LedgerSummary;



/**
 *
 * @author Racs
 */
public interface ILedgerDAO
{
	LedgerSummary getLedgerSummaryByHouseId(int houseId) throws SQLException;
	List<LedgerSummary> getAllLedgerSummary() throws SQLException;
	LedgerSummary saveLedgerSummary(LedgerSummary ledgerSummary) throws SQLException;
}

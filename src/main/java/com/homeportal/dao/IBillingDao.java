/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.bean.BillingBean;
import com.homeportal.model.SOAHeader;
import com.homeportal.model.SOASummary;

import java.util.List;

/**
 *
 * @author Peter
 */
public interface IBillingDao {
    
    List<BillingBean> viewBillingByPortal(int userId, int houseId, String payeeName);
    List<BillingBean> viewOrByPortal(int userId);
    List<BillingBean> getUserMonthlyStatementList(int userId, int houseId);
    List<BillingBean> viewBillingByAdmin();
    List<BillingBean> getPayeeDetails(int userId,int roleId);
    List<BillingBean> viewORByAdmin(int userId,int orNumber, String postedDate);
    List<BillingBean> viewStatementByAdmin(int userId, String statementDate);
    List<BillingBean> viewCollections(int userId);
    List<BillingBean> getRoleId(int userId);
    
    SOAHeader getSOAHeader(int userId, int houseId, String billMonth);
    SOASummary getSOASummary(int soaHeaderId);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.BillingBean;
import com.homeportal.dao.impl.BillingDaoImpl;
import com.homeportal.model.SOAHeader;
import com.homeportal.model.SOASummary;
import com.homeportal.util.BillingUtil;

/**
 *
 * @author Peter
 */
@Service
public class BillingService
{

	private static Logger logger = Logger.getLogger(BillingService.class);
	
    private BillingDaoImpl billingDaoImpl;
    private BillingUtil billingUtil;
    
   
    /**
     * Gets all the outstanding balances for a user/owner
     * */
    @Transactional(readOnly = true)
    public List<BillingBean> getUserOutstandingBalances(int userId, int houseId)
    {
    	return billingDaoImpl.getUserOutstandingBalances(userId, houseId);
    }
    
    
    // OVERLOADED METHOD, THE OTHER ONE FOR DELETION
    @Transactional(readOnly = true)
    public List<BillingBean> viewBillingByPortal(int userId, int houseId, String payeeName)
    {
    	System.out.println("SERVICE Getting list of bills payable by this user: " + payeeName);
    	logger.info("SERVICE Getting list of bills payable by this user: " + payeeName);
    	
    	return billingDaoImpl.viewBillingByPortal(userId, houseId, payeeName);
    }
    
    @Transactional(readOnly = true)
    public List<BillingBean> viewBillingByPortal(int userId) {
        return billingDaoImpl.viewBillingByPortal(userId);
    }
    
    @Transactional(readOnly = true)
    public List<BillingBean> getUserMonthlyStatementList(int userId, int houseId) {
        return billingDaoImpl.getUserMonthlyStatementList(userId, houseId);
    }

    @Transactional(readOnly = true)
    public List<BillingBean> viewBillingByAdmin() {
        return billingDaoImpl.viewBillingByAdmin();
    }

     @Transactional(readOnly = true)
    public List<BillingBean> getAllocated(Object data) {
        return billingUtil.getBillingBeanFromRequest(data);
    }

     @Transactional(readOnly = true)
    public List<BillingBean> getPayeeDetails(int userId, int roleId) {
        return billingDaoImpl.getPayeeDetails(userId, roleId);
    }

     @Transactional(readOnly = true)
    public List<BillingBean> getRoleId(int userId) {
        return billingDaoImpl.getRoleId(userId);
    }
     
    @Transactional(readOnly = true)
    public List<BillingBean> viewORByAdmin(int userId, int orNumber, String postedDate) {
        return billingDaoImpl.viewORByAdmin(userId,orNumber, postedDate);
    }
    
    @Transactional(readOnly = true)
    public List<BillingBean> viewCollections(int userId) {
        return billingDaoImpl.viewCollections(userId);
    }
    // 

    @Transactional(readOnly = true)
    public List<BillingBean> viewOrByPortal(int userId)
    {
        return billingDaoImpl.viewOrByPortal(userId);
    }

    @Transactional(readOnly = true)
    public List<BillingBean> viewStatementByAdmin(int userId, String statementDate)
    {
        return billingDaoImpl.viewStatementByAdmin(userId, statementDate);
    }
    
    
    @Transactional(readOnly = true)
    public SOAHeader getSOAHeader(int userId, int houseId, String billMonth)
    {
        return billingDaoImpl.getSOAHeader(userId, houseId, billMonth);
    }

    
    @Transactional(readOnly = true)
    public SOASummary getSOASummary(int soaHeaderId)
    {
        return billingDaoImpl.getSOASummary(soaHeaderId);
    }
    
    
    /**
     * @param billingDaoImpl the billingDaoImpl to set
     */
    @Autowired
    public void setBillingDaoImpl(BillingDaoImpl billingDaoImpl) {
        this.billingDaoImpl = billingDaoImpl;
    }

    /**
     * @param billingUtil the billingUtil to set
     */
    @Autowired
    public void setBillingUtil(BillingUtil billingUtil) {
        this.billingUtil = billingUtil;
    }

    
}

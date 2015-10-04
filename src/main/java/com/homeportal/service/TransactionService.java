/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.bean.TransactionBean;
import com.homeportal.dao.impl.TransactionDaomImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class TransactionService {
    
    private TransactionDaomImpl transactionDaomImpl;
    
    @Transactional(readOnly=true)
     public List<TransactionBean> viewTransactionHistory(int userId){
         return transactionDaomImpl.viewBillingByAdmin(userId);
     }

    /**
     * @param transactionDaomImpl the transactionDaomImpl to set
     */
    @Autowired
    public void setTransactionDaomImpl(TransactionDaomImpl transactionDaomImpl) {
        this.transactionDaomImpl = transactionDaomImpl;
    }
    
}

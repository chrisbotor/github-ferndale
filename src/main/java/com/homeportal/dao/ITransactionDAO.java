/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.bean.TransactionBean;
import java.util.List;

/**
 *
 * @author Peter
 */
public interface ITransactionDAO {
    
    List<TransactionBean> viewBillingByAdmin(int userId);
}

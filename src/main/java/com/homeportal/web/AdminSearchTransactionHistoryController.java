/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Peter
 */
@Controller
public class AdminSearchTransactionHistoryController {
    
    @RequestMapping("/admin-searchTransactionHistory")
    String view() throws Exception {
         System.out.println("went to view of AdminSearchTransactionHistoryController ");
         return "admin-searchTransactionHistory";
    }
    
}

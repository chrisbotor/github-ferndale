/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Peter
 */
@Controller
public class AdminPaymentsController {
    
    @RequestMapping("/admin-paymentsDaily.action")
    String view(HttpServletRequest req, HttpSession session) throws Exception {
        System.out.println("went to view of AdminPaymentsController ");
         return "admin-paymentsDaily";
    }
}

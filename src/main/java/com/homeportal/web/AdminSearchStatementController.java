/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Peter
 */
@Controller
public class AdminSearchStatementController {
    
    @RequestMapping("/admin-searchStatement")
    String view(HttpSession session) throws Exception {
         System.out.println("went to view of AdminSearchStatementController ");
         session.removeAttribute("billerId");
         session.removeAttribute("mode");
         session.removeAttribute("chequeNum");
         session.removeAttribute("p_or_number");
         session.removeAttribute("p_user_details");
         session.removeAttribute("p_house_details");
         session.removeAttribute("reportPath");
         session.removeAttribute("jasperFile");
            
         return "admin-searchStatement";
    }
}

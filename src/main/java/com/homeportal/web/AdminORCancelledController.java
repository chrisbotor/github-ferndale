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
public class AdminORCancelledController {
    
    @RequestMapping("/admin-ORCancelled.action")
    String view(HttpServletRequest req, HttpSession session) throws Exception {
        System.out.println("went to view of AdminORCancelledController ");
        try {
            System.out.println("went to try ... ");
          int lenght = session.getAttribute("orNumber").toString().length();
        } catch (NullPointerException e) {
          // orNumber
          if(req.getParameter("orNumber").length() == 0){
          session.setAttribute("orNumber", req.getParameter("orNumber").length());
          }else{
          session.setAttribute("orNumber", req.getParameter("orNumber"));
          }
        }
         return "admin-ORCancelled";
    }
}

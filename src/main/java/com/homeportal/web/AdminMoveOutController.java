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
public class AdminMoveOutController {
    
    @RequestMapping("/admin-moveOut")
    String view() throws Exception {
         System.out.println("went to view of AdminMoveOutController");
        return "admin-moveOut";
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author PSP36488
 */
@Controller
public class AdminController {

    @RequestMapping("/admin")
    String view() throws Exception {
         System.out.println("went to  AdminController...view");
        return "admin";
    }

}

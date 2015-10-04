/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Peter
 */
@Controller
public class PortalController {
    
    @RequestMapping("/user-portal")
    public String myPortal(Map<String, Object> map) {
        System.out.println("went here :: ");
        return "user-portal";
    }
    
}

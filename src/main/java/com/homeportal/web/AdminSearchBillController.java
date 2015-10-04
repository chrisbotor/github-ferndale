/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Peter
 */
@Controller
public class AdminSearchBillController 
{
	private static Logger logger = Logger.getLogger(AdminSearchBillController.class);
	
	@RequestMapping("/admin-search-bill")
    String view(HttpSession session) throws Exception 
    {
    	logger.info("******* Searching bills for payment...");
        session.removeAttribute("billerId");
        session.removeAttribute("mode");
        session.removeAttribute("chequeNum");
        session.removeAttribute("orNumber");
            
         return "admin-search-bill";
    }
}

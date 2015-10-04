/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author Racs
 */
@Controller
public class AdminCollectionsController
{
    private static Logger logger = Logger.getLogger(AdminCollectionsController.class);
    
    
    
    /**
     * 	Shows search box for admin daily collections 
     * 
     * */
    @RequestMapping("/admin-collections-daily.action")
    String viewDailyCollections(HttpServletRequest req, HttpSession session) throws Exception {
    	logger.info("Getting search box for daily collections...");
    	return "admin-collections-daily";
    }
    
    
    /**
     * 	Shows search box for admin detailed daily collections
     * 
     * */
    @RequestMapping("/admin-collections-daily-detailed.action")
    String viewDetailedDailyCollections(HttpServletRequest req, HttpSession session) throws Exception {
    	logger.info("Getting search box for detailed daily collections...");
    	return "admin-collections-daily-detailed";
    }
    
    
    
    /**
     * 	Shows search box for admin monthly collections
     * 
     * */
    @RequestMapping("/admin-collections-monthly.action")
    String viewMonthlyCollections(HttpServletRequest req, HttpSession session) throws Exception {
    	logger.info("Getting search box for monthly collections...");
        return "admin-collections-monthly";
    }
    
    
    
    /**
     * 	Shows search box for admin detailed monthly collections
     * 
     * */
    @RequestMapping("/admin-collections-monthly-detailed.action")
    String viewDetailedMonthlyCollections(HttpServletRequest req, HttpSession session) throws Exception {
    	logger.info("Getting search box for detailed monthly collections...");
        return "admin-collections-monthly-detailed";
    }
    
    
    /**
     * 	Shows search box for admin monthly collections summary
     * 
     * */
    @RequestMapping("/admin-collections-monthly-summary.action")
    String viewMonthlyCollectionsSummary(HttpServletRequest req, HttpSession session) throws Exception {
    	logger.info("Getting search box for monthly collections summary...");
        return "admin-collections-monthly-summary";
    }
    
}

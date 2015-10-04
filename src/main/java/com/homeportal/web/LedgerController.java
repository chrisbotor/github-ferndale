/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.model.LedgerSummary;
import com.homeportal.service.LedgerService;
import com.homeportal.util.ValidationUtil;

/**
 *
 * @author Racs
 */
@Controller
public class LedgerController 
{
	private static Logger logger = Logger.getLogger(LedgerController.class);
	private LedgerService ledgerService;
	
	
	 /**
     *	Gets the ledger summary for a particular user
     * 
     * */
    @RequestMapping(value = "/user/get-ledger-summary.action")
    public @ResponseBody
    Map<String, ? extends Object> getLedgerSummary(HttpServletRequest request, HttpSession session) throws Exception
    {
    	logger.info("Getting the ledger summary...");
        System.out.println("Getting the ledger summary...");
        
        try
    	{
    		String houseIdParam = request.getParameter("houseId");
    		int houseId = 0;
    		
    		if (ValidationUtil.hasValue(houseIdParam))
    		{
    			houseId = Integer.parseInt(houseIdParam);
    		}
    		
    	
    		List<LedgerSummary> ledgerSummaries = new ArrayList<LedgerSummary>();
    		LedgerSummary ledgerSummary = ledgerService.getLedgerSummaryByHouseId(houseId);
    		ledgerSummaries.add(ledgerSummary);
    		
    		Map<String, Object> map = new HashMap<String, Object>(2);
    		map.put("success", true);
    		map.put("data", ledgerSummary);
    		
    		return map;
    		// return getMap(ledgerSummaries);
        } 
    	catch (Exception e)
        {
            return getModelMapError("Error trying to get ledger summary. " + e.getMessage());
        }
    }
	
	      
    
   
   private Map<String, Object> getMap(List<LedgerSummary> ledgerSummaries)
   {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", ledgerSummaries.size());
        modelMap.put("data", ledgerSummaries);
        modelMap.put("success", true);
        System.out.println(modelMap);
        return modelMap;
    }
   
   

    /**
     * Generates modelMap to return in the modelAndView in case of exception
     *
     * @param msg message
     * @return
     */
    private Map<String, Object> getModelMapError(String msg) {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("message", msg);
        modelMap.put("success", false);

        return modelMap;
    }
    

    /**
     * @param ledgerService the ledgerService to set
     */
    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }
    
}

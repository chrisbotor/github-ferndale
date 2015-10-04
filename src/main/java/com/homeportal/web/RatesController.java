/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.Rates;
import com.homeportal.service.RatesService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Peter
 */
@Controller
public class RatesController {
    
    private RatesService ratesService;
    
    @RequestMapping("/admin-rates")
    String view() throws Exception {
        System.out.println("went to RatesController...view");
        return "admin-rates";
    }
    
    @RequestMapping(value = "/rates/view.action")
    public @ResponseBody
    Map<String, ? extends Object> loadRates() throws Exception {
        try {
            System.out.println("\nLoading rates...");
            List<Rates> rateList = ratesService.getRatesList();
            
            if (rateList != null)
            {
            	System.out.println("Rates size: " + rateList.size());
            }
            else
            {
            	System.out.println("NO rates found");
            }
            
            return getMap(rateList);
        } catch (Exception e) {
            return getModelMapError("Error retrieving rates from database.");
        }
    }
    
    
    
    @RequestMapping(value = "/rates/update.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        System.out.println("data : " + data);
        try {
            
            if(ratesService.getAmount(data) == 0){
                return getModelMapError("Amount Not valid");
            }else if(ratesService.getStartDate(data).length() == 0){
                return getModelMapError("Start Date should not be null");
            }else if(ratesService.getEndDate(data).length() == 0){
                return getModelMapError("End Date should not be null");
            }else{
            List<Rates> v = ratesService.update(data);
            return getMap(v);
            }
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }
    
      private Map<String, Object> getMap(List<Rates> amenity) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", amenity.size());
        modelMap.put("data", amenity);
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
     * @param ratesService the ratesService to set
     */
    @Autowired
    public void setRatesService(RatesService ratesService) {
        this.ratesService = ratesService;
    }
}

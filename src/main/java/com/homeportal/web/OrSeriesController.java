/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.OrSeries;
import com.homeportal.service.OrSeriesService;
import java.util.ArrayList;
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
public class OrSeriesController {
    
    private OrSeriesService orSeriesService;
    
    @RequestMapping("/admin-orseries")
    String view() throws Exception {
        System.out.println("went to OrSeriesController...view");
        return "admin-orseries";
    }
    
    @RequestMapping(value = "/loadOrSeries.action")
    public @ResponseBody
    Map<String, ? extends Object> loadorSeries() throws Exception {
        try {
            System.out.println("loadorSeries...");
            List<OrSeries> orSeries = orSeriesService.getSeriesNumber();
            List<OrSeries> list = new ArrayList<OrSeries>();
            OrSeries os = new OrSeries();
            for (OrSeries or : orSeries) {
                os.setId(or.getId());
                os.setStartSeries(or.getStartSeries());
                os.setEndSeries(or.getEndSeries());
                os.setCurrent(or.getCurrent());
                list.add(os);
            }
            return getMap(list);
        } catch (Exception e) {
            return getModelMapError("Error retrieving amenities from database.");
        }
    }
    
    @RequestMapping(value = "/updateOrSeries.action")
    public @ResponseBody
    Map<String, ? extends Object> update(@RequestParam Object data) throws Exception {
        System.out.println("data : " + data);
        try {
            if(orSeriesService.startSeries(data) == 0)
            {
                return getModelMapError("Start Series should not be zero");
            }
            if(orSeriesService.endSeries(data) == 0)
            {
                return getModelMapError("Start Series should not be zero");
            }
            if(orSeriesService.startSeries(data) > orSeriesService.endSeries(data))
            {
                return getModelMapError("Start Series should not be greater than End Series");
            }
            List<OrSeries> v = orSeriesService.updateSeriesNumber(data);
            List<OrSeries> current = orSeriesService.updateFirstNumber(data);
            List<OrSeries> orSeries = orSeriesService.getSeriesNumber();
            List<OrSeries> list = new ArrayList<OrSeries>();
            OrSeries os = new OrSeries();
            for (OrSeries or : orSeries) {
                os.setId(or.getId());
                os.setStartSeries(or.getStartSeries());
                os.setEndSeries(or.getEndSeries());
                os.setCurrent(or.getCurrent());
                list.add(os);
            }
            return getMap(list);
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }
    
     private Map<String, Object> getMap(List<OrSeries> list) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", list.size());
        modelMap.put("data", list);
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
     * @param orSeriesService the orSeriesService to set
     */
    @Autowired
    public void setOrSeriesService(OrSeriesService orSeriesService) {
        this.orSeriesService = orSeriesService;
    }
}

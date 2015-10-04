/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.bean.BillingBean;
import com.homeportal.service.BillingService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Peter
 */
@Controller
public class AdminORHistoryController {
    
    private BillingService billingService;
    
    @RequestMapping("/admin-ORHistory.action")
    String view(HttpServletRequest req, HttpSession session) throws Exception {
        System.out.println("went to view of AdminORHistoryController ");
        try {
            System.out.println("went to try ... ");
          int lenght = session.getAttribute("billerId").toString().length();
        } catch (NullPointerException e) {
          // biller id
          if(req.getParameter("id").length() == 0){
          session.setAttribute("billerId", req.getParameter("id").length());
          }else{
          session.setAttribute("billerId", req.getParameter("id"));
          }
          // orDate
          if(req.getParameter("orDate").length() == 0){
          session.setAttribute("orDate", req.getParameter("orDate").length());
          }else{
          session.setAttribute("orDate", req.getParameter("orDate"));
          }
          //session.setAttribute("orDate", req.getParameter("orDate"));
          //session.setAttribute("orNumber", req.getParameter("orNumber"));
          // orNumber
          if(req.getParameter("orNumber").length() == 0){
          session.setAttribute("orNumber", req.getParameter("orNumber").length());
          }else{
          session.setAttribute("orNumber", req.getParameter("orNumber"));
          }
        }
         return "admin-ORHistory";
    }
    
    @RequestMapping("/admin/viewORNumber.action")
    public @ResponseBody
    Map<String, ? extends Object> viewORNumber(HttpSession session) throws Exception {
        try {
            System.out.println("went to  viewORNumber - AdminORHistoryController ");
            int userId = Integer.parseInt(session.getAttribute("billerId").toString());
            System.out.println("userId : " + userId);
            int orNumber = Integer.parseInt(session.getAttribute("orNumber").toString());
            System.out.println("orNumber : " + orNumber);
            String postedDate = session.getAttribute("orDate").toString();
            System.out.println("postedDate : "+ postedDate);
            List<BillingBean> bill = billingService.viewORByAdmin(userId,orNumber, postedDate);
            return getMap(bill);
        } 
        catch (Exception e) {
             //System.out.println("params : " + e.toString());
             return getModelMapError("Error trying to update vehicle sticker." + e.toString());
        }
    }
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param bill
     * @return
     */
    private Map<String, Object> getMap(List<BillingBean> bill) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", bill.size());
        modelMap.put("data", bill);
        modelMap.put("success", true);
        System.out.println("model map " + modelMap);
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
     * @param billingService the billingService to set
     */
    @Autowired
    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }
}

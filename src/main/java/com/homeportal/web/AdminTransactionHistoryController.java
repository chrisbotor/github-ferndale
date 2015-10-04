/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.bean.TransactionBean;
import com.homeportal.service.TransactionService;
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
public class AdminTransactionHistoryController {
    
    private TransactionService transactionService;
    
    @RequestMapping("/admin-transactionHistory")
    String view(HttpServletRequest req, HttpSession session) throws Exception {
        System.out.println("went to view of AdminTransactionHistoryController ");
        try {
          int lenght = session.getAttribute("transactionHistoryId").toString().length();  
        } catch (NullPointerException e) {
          session.setAttribute("transactionHistoryId", req.getParameter("id"));  
        }
         return "admin-transactionHistory";
    }
    
     @RequestMapping(value = "/admin/viewTransaction.action")
     public @ResponseBody
    Map<String, ? extends Object> viewTransactionHistory(HttpSession session) throws Exception {
        try {
            System.out.println("went to  viewTransactionHistory - AdminTransactionHistoryController");
            int userId = Integer.parseInt(session.getAttribute("transactionHistoryId").toString());
            System.out.println("id is : " + userId);
            List<TransactionBean> sr = transactionService.viewTransactionHistory(userId);
            session.removeAttribute("transactionHistoryId");
            return getMap(sr);
        } catch (Exception e) {
            return getModelMapError("Error services from database.");
        }
    }
     
    private Map<String, Object> getMap(List<TransactionBean> sr) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        modelMap.put("total", sr.size());
        modelMap.put("data", sr);
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
     * @param transactionService the transactionService to set
     */
     @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
}

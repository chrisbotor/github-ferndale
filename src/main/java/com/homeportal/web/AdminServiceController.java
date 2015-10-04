/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.bean.ServiceBean;
import com.homeportal.model.ServiceRequest;
import com.homeportal.service.ServiceRequestService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Peter
 */
@Controller
public class AdminServiceController {
    
    private ServiceRequestService requestService;
    
    @RequestMapping("/admin-serviceRequest")
    String view(HttpServletRequest req, HttpSession session) throws Exception {
         System.out.println("went to view of AdminServiceController");
          try {
            System.out.println("went to try ... ");
          int lenght = session.getAttribute("reqId").toString().length();  
        } catch (NullPointerException e) {
          if(req.getParameter("serviceId").length()==0){
          session.setAttribute("desc", req.getParameter("serviceId").length());
          }else
          {
          session.setAttribute("desc", req.getParameter("serviceId"));
          }
          if(req.getParameter("id").length() == 0){
          session.setAttribute("reqId", req.getParameter("id").length());
          }else{
          session.setAttribute("reqId", req.getParameter("id"));
          }
          session.setAttribute("reqDate", req.getParameter("reqDate"));
          session.setAttribute("reqStatus", req.getParameter("stat"));
        }
        return "admin-serviceRequest";
    }
    
    @RequestMapping(value = "/admin/services.action")
    public @ResponseBody
    Map<String, ? extends Object> viewServicesRequest(HttpSession session) throws Exception {
        try {
            System.out.println("went to  viewServicesRequest - AdminServiceController");
            int userId = Integer.parseInt(session.getAttribute("reqId").toString());
            int amenityId = Integer.parseInt(session.getAttribute("desc").toString());
            String status = session.getAttribute("reqStatus").toString();
            String reqDate = session.getAttribute("reqDate").toString();
            List<ServiceBean> sr = requestService.getServiceRequestList(userId, status, reqDate, amenityId);
            return getMapSR(sr);
        } catch (Exception e) {
            return getModelMapError("Error services from database.");
        }
    }
    
   
    private Map<String, Object> getMapSR(List<ServiceBean> sr) {

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
     * @param requestService the requestService to set
     */
    @Autowired
    public void setRequestService(ServiceRequestService requestService) {
        this.requestService = requestService;
    }
}

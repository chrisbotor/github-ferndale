/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.Lessee;
import com.homeportal.model.Owner;
import com.homeportal.service.LesseeService;
import com.homeportal.service.OwnerService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class UpdateProfileController {
    
    private OwnerService ownerService;
    private LesseeService leeseeService;
    
    @RequestMapping("/user-profile")
    String myProfile(Owner o) throws Exception {
         System.out.println("went to  myProfile...");
        return "user-profile";
    }
    
    @RequestMapping(value="/load.action", method=RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> load(HttpSession session) throws Exception {
        try {
            
            Integer roleId = Integer.parseInt(session.getAttribute("roleId").toString());
            String id = session.getAttribute("userId").toString();
            System.out.println("went to load()..." + id + " roles " + roleId);
            Owner owner = new Owner();
            Lessee leesee = new Lessee();
            
            if (roleId == 3) {

                List<Owner> o = ownerService.getOwner(Integer.parseInt(id));

                for (Owner ownerList : o) {
                    owner.setId(ownerList.getId());
                    owner.setLastname(ownerList.getLastname());
                    owner.setFirstname(ownerList.getFirstname());
                    owner.setMiddlename(ownerList.getMiddlename());
                    owner.setCivilStatus(ownerList.getCivilStatus());
                    owner.setMobileNumber(ownerList.getMobileNumber());
                    owner.setHomeNumber(ownerList.getHomeNumber());
                    owner.setEmailAddress(ownerList.getEmailAddress());
                    owner.setWorkName(ownerList.getWorkName());
                    owner.setWorkAddress(ownerList.getWorkAddress());
                    owner.setWorkLandline(ownerList.getWorkLandline());
                    owner.setWorkMobile(ownerList.getWorkMobile());
                    owner.setWorkEmail(ownerList.getWorkEmail());
                    owner.setBirthdate(ownerList.getBirthdate());
                }
            }
            
            
            
            // TODO RACS
           /*if(roleId == 4){
                    List<Lessee> l = leeseeService.getLeeseeByUserId(id);
                    
                    for (Lessee lees : l) {
                            leesee.setId(lees.getId());
                            leesee.setLastname(lees.getLastname());
                            leesee.setFirstname(lees.getFirstname());
                            leesee.setMiddlename(lees.getMiddlename());
                            leesee.setCivilStatus(lees.getCivilStatus());
                            leesee.setMobileNumber(lees.getMobileNumber());
                            leesee.setHomeNumber(lees.getHomeNumber());
                            leesee.setEmailAddress(lees.getEmailAddress());
                            leesee.setWorkName(lees.getWorkName());
                            leesee.setWorkAddress(lees.getWorkAddress());
                            leesee.setWorkLandline(lees.getWorkLandline());
                            leesee.setWorkMobile(lees.getWorkMobile());
                            leesee.setWorkEmail(lees.getWorkEmail());
                            leesee.setBirthdate(lees.getBirthdate());
                    }
           }*/
            
            Map<String, Object> data = new HashMap<String, Object>();
		data.put("success",Boolean.TRUE);
                if(roleId == 3){
                data.put("data", owner);
                }if(roleId == 4){
                data.put("data", leesee);
                }
		return data;
        } catch (Exception e) {
            return getModelMapError("Error retrieving Owner from database.");
        }
    }
    
    @RequestMapping(value="/update.action", method=RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> add(Owner owner, Lessee leesee, HttpSession session)  throws Exception {
        try {
            Integer roleId = Integer.parseInt(session.getAttribute("roleId").toString());
            String id = session.getAttribute("userId").toString();
            System.out.println("id is : " + id + " role id is " + roleId);
            if(roleId == 3){
            owner.setUserId(Integer.parseInt(id));
            ownerService.updateOwner(owner);
            }
            if(roleId == 4){
            leesee.setUserId(Integer.parseInt(id));
            // leeseeService.updateLeesee(leesee);	// TODO RACS
            }
           
            Map<String, Object> data = new HashMap<String, Object>();
	    data.put("success",Boolean.TRUE);
            return data;
        } catch (Exception e) {
            return getModelMapError("Error trying to update contact.");
        }
    }
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param owner
     * @return
     */
    private Map<String, Object> getMap(List<Owner> owner) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", owner.size());
        modelMap.put("data", owner);
        modelMap.put("success", true);
        
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
     * @param ownerService the ownerService to set
     */
    @Autowired
    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * @param leeseeService the leeseeService to set
     */
    @Autowired
    public void setLeeseeService(LesseeService leeseeService) {
        this.leeseeService = leeseeService;
    }
    
}

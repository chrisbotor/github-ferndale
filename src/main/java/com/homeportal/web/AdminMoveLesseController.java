/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.House;
import com.homeportal.model.Lessee;
import com.homeportal.model.MoveInOut;
import com.homeportal.model.User;
import com.homeportal.service.HouseService;
import com.homeportal.service.LesseeService;
import com.homeportal.service.MoveInOutService;
import com.homeportal.service.UserService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Peter
 */
@Controller
public class AdminMoveLesseController {
    
     private LesseeService leeseeService;
     private UserService userService;
     private MoveInOutService moveInOutService;
     private HouseService houseService;
    
    @RequestMapping("/admin-moveLesse")
    String view() throws Exception {
         System.out.println("went to view of AdminMoveLesseController");
        return "admin-moveLesse";
    }
    
    @RequestMapping(value ="/admin-moveLesse", method = RequestMethod.POST) 
    public @ResponseBody
    Map<String, ? extends Object> moveInAction( @RequestParam("firstname") String firstname,@RequestParam("middlename") String middlename,
            @RequestParam("lastname") String lastname, @RequestParam("userId") String id,@RequestParam("rented") 
                    String rented,@RequestParam("moveIn") String moveIn,@RequestParam("id") int houseId,HttpSession session){
    
        String userId = "";
        User u = new User();
        MoveInOut mio = new MoveInOut();
        Lessee l = new Lessee();
        House h = new House();
        
        System.out.println("house id " + houseId);
        System.out.println("rented " + rented);
        System.out.println("move in " + moveIn);
        
        if(rented.equalsIgnoreCase("Y"))
        {
        	id = "0";
        	
            if (firstname.length() > 0 && lastname.length() > 0) 
            {
            	u.setRoleId(4);
                u.setPassword("123456");
                u.setUsername(lastname.toLowerCase() + "." + firstname.toLowerCase());
                u.setStatus("A");
                u.setCreatedAt(new Date());
                u.setUpdatedAt(new Date());
                u.setMultiOwner("F");

                User user = userService.addUser(u);
                System.out.println("user id is : " + user.getId());
                userId = String.valueOf(user.getId());
                
                l.setFirstname(firstname.toUpperCase());
                l.setMiddlename(middlename.toUpperCase());
                l.setLastname(lastname.toUpperCase());
                l.setUserId(Integer.parseInt(userId));
                l.setStatus("A");
                l.setHouseId(houseId);
                l.setCreatedAt(new Date());
                l.setUpdatedAt(new Date());
                
               // Lessee leesee = leeseeService.addLeesee(l); // TODO RACS
            }
        }
        
        if(rented.equalsIgnoreCase("N"))
        {
	        userId = id;
	        firstname = "";
	        middlename = "";
	        lastname = "";
        }
        
        h.setRented(rented);
        h.setId(houseId);
        
        House house = houseService.updateHouseRented(h);
        
        mio.setUserId(userId);
        mio.setMoveIn(moveIn);
        mio.setCreatedAt(new Date());
        mio.setUpdatedAt(new Date());
        
        MoveInOut inOut = moveInOutService.addMoveIn(mio);
        System.out.println("in out id is " + inOut.getId());
        return getMap(inOut);
    }
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param User
     * @return
     */
    private Map<String, Object> getMap(MoveInOut mio) {
    	Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("data", mio);
        modelMap.put("success", true);

       return modelMap;
    }
    
     /**
     * @param leeseeService the leeseeService to set
     */
    @Autowired
    public void setLeeseeService(LesseeService leeseeService) {
        this.leeseeService = leeseeService;
    }

    /**
     * @param userService the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param moveInOutService the moveInOutService to set
     */
    @Autowired
    public void setMoveInOutService(MoveInOutService moveInOutService) {
        this.moveInOutService = moveInOutService;
    }

    /**
     * @param houseService the houseService to set
     */
    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }
    
    
}

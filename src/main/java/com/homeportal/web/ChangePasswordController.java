/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.web;

import com.homeportal.model.User;
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
public class ChangePasswordController {

    private UserService userService;

    /*@RequestMapping("/change-password.action")
    String view() throws Exception {
        System.out.println("went to  Change Password...view :");
        return "change-password";
    }

    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> validate(@RequestParam("password1") String pwd, HttpSession session) throws Exception {
        User user = null;
        try {
            System.out.println("VALIDATING CHANGE PASSWORD....");
            System.out.println("Password: " + pwd);
            String uname = session.getAttribute("username").toString();
            System.out.println("Username: " + uname);
	   
            User u = new User();
            u.setPassword(pwd);
            u.setUsername(uname);
            u.setStatus("A");
            
            System.out.println("u is " + u.getUsername() + " " + u.getPassword());
            
            user = userService.changePassword(u);
            System.out.println("user is : " + user.getPassword());
            return getMap(user);

        } catch (Exception e) {
            return getModelMapError("Error retrieving user from database.");
        }
    }

    *//**
     * Generates modelMap to return in the modelAndView
     *
     * @param User
     * @return
     *//*
    private Map<String, Object> getMap(User user) {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("data", user);
        modelMap.put("success", true);

        return modelMap;
    }
*/
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
     * @param userService the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

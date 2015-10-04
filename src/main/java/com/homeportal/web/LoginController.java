package com.homeportal.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.homeportal.bean.UserBean;
import com.homeportal.model.User;
import com.homeportal.service.UserService;



@Controller
public class LoginController
{
	private UserService userService;
	
	private static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(value="/login.action", method=RequestMethod.GET)
        public ModelAndView index(){
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }



	@RequestMapping(value = "/login/validate.action", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> validate(@RequestParam("username") String uname, @RequestParam("password") String pwd, HttpSession session) 
    		throws Exception
    {
		User user = null;
		 try {
			 	logger.debug("VALIDATING USER....");
			 	System.out.println("VALIDATING USER....");

			 	user = userService.getUserByUsernamePassword(uname, pwd);
			 	
			 	if (user != null)
			 	{
			 		/*logger.debug("Got user...");
			 		logger.debug("Username: " + user.getUsername());
			 		logger.debug("Password: " + user.getPassword());
			 		logger.debug("Role ID: " + user.getRole_id());
			 		*/
			 		
			 		System.out.println("Got user...");
			 		System.out.println("Username: " + user.getUsername());
			 		System.out.println("Password: " + user.getPassword());
			 		System.out.println("Role ID: " + user.getRoleId());
			 		
			 		
			 		if(user.getRoleId() == 3 | user.getRoleId() == 4)
	                {
	                	Map<String, String> houseIdMap = new HashMap<String, String>();
	                	
	                	String ownerName = null;
	                	
	                	System.out.println("Getting house id and owner... ");
	                	List<UserBean> list = userService.getHouseIdAndOwner(user.getId(), user.getRoleId());
	                	
	                	
	                	if (list != null && list.size() > 0)
	                	{
	                		// get the ownerName from the first UserBean in the list (NOTE: Every UserBean has the same owner name, but different houseId)
	                		// this is a hack code so that UserBean class will not be modified but it should have a property
	                		// private List<Integer> houseIds instead of private int houseId
	                		// to accommodate the possibility that 1 House Owner has multiple house
	                		
	                		ownerName = list.get(0).getOwner();
	                		
	                		for (UserBean bean : list)
		                    {
	                			//logger.debug("Adding UserBean houseId to houseIdList: " + bean.getHouseId());
	                			// houseIdList.add(bean.getHouseId());
	                			
	                			System.out.println("Adding UserBean houseId to houseIdMap: " + bean.getHouseId());
	                			houseIdMap.put(String.valueOf(bean.getHouseId()), bean.getOwnerAddress());
		                	}
	                	}
	                	
	                	// FOR TESTING
	                	// houseIdMap.put("1", "House 1");
	                	// houseIdMap.put("2", "House 2");
	                	
	                	session.setAttribute("houseIdMap", houseIdMap);
	                    session.setAttribute("ownerName", ownerName);
	                 }
			 		
			 			System.out.println("USER DELINQUENT? " + user.getDelinquent());
			 			session.setAttribute("delinquent", user.getDelinquent());    
	                    session.setAttribute("roleId", user.getRoleId());
	                    session.setAttribute("userId", user.getId());
	                    session.setAttribute("username", user.getUsername());
			    }
			 	         
			 	return getMap(user);

	        } catch (Exception e) {
	        	// logger.error("Error retrieving user from database.", e);
	        	
	        	System.out.println("Error retrieving user from database. " + e.getMessage());
	            return getModelMapError("Error retrieving user from database. " + e.getMessage());
	        }
    }

	@RequestMapping(value="/home.action", method=RequestMethod.GET)
       public ModelAndView homeownerPage(@RequestParam("houseId") String houseId, HttpSession session){
     
		System.out.println("================== Got houseId: " + houseId);
		
		// session.setAttribute("houseId", houseId);
		
		// String id = session.getAttribute("userId").toString();
		
		ModelAndView mv = new ModelAndView("home");
        
        return mv;
    }

	
	
    @RequestMapping(value="/home-owner-landing-page.action", method=RequestMethod.GET)
	    public ModelAndView homeownerLandingPage(HttpSession session){
    		System.out.println("In home owner landing page....");
    	
    		ModelAndView mv = new ModelAndView("home-owner-landing-page");
    		return mv;
	 }


    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param User
     * @return
     */
    private Map<String, Object> getMap(User user) {
    	Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("data", user);
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
     * @param userService the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
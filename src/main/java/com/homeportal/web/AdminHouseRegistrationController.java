/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.model.House;
import com.homeportal.model.LedgerSummary;
import com.homeportal.model.Owner;
import com.homeportal.model.User;
import com.homeportal.service.HouseService;
import com.homeportal.service.LedgerService;
import com.homeportal.service.OwnerService;
import com.homeportal.service.UserService;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.ValidationUtil;


/**
 *
 * @author racs
 */
@Controller
public class AdminHouseRegistrationController 
{
	private static Logger logger = Logger.getLogger(AdminHouseRegistrationController.class);

	private UserService userService;
    private OwnerService ownerService;
    private HouseService houseService;
    private LedgerService ledgerService;
    

    /**
	 * Used to add information for the House Registration in the admin main menu
	 * */
	@RequestMapping(value="/admin-house-registration.action", method=RequestMethod.GET)
	//@RequestMapping("/admin-moveIn")
    String view() throws Exception
	{
		System.out.println("went to view of House Registration");
        logger.info("went to view of House Registration");
        
		return "admin-house-registration";
    }
	
	
	
	/**
     * Used to save new House Registration
     * If there is already an existing owner, use that Owner/User to register the Lot Details and create a new record in the house table. 
     * No need to create a new Owner/User since it is already existing.
     * Otherwise, create a new user, owner and house.
     * 
     * */
    @RequestMapping(value ="/admin-house-registration/add-house-owner", method = RequestMethod.POST) 
    public @ResponseBody
    Map<String, ? extends Object> addHouseAndOwner(HttpServletRequest request, HttpSession session) throws Exception
    {
    	System.out.println("========================================  HOUSE REGISTRATION - ADDING NEW HOUSE OWNER ==================================");
    	logger.info("========================================  HOUSE REGISTRATION - ADDING NEW HOUSE OWNER ==================================");
    	logger.info("Registering house and owner...");
    	
    	String addressNumber = request.getParameter("addressNumber");
		String addressStreet = request.getParameter("addressStreet");
		String lotArea = request.getParameter("lotArea");
		String title = request.getParameter("title");
		String firstname = request.getParameter("firstname");
		String middlename = request.getParameter("middlename");
		String lastname = request.getParameter("lastname");
		String existingOwnerId = request.getParameter("existingOwnerId");
    
		logger.info("addressNumber: " + addressNumber);
		logger.info("addressStreet: " + addressStreet);
		logger.info("lotArea: " + lotArea);
		logger.info("title: " + title);
		logger.info("firstname: " + firstname);
		logger.info("middlename: " + middlename);
		logger.info("existingOwnerId: " + existingOwnerId);
		
		
		// CREATE NEW HOUSE/OWNER/USER
		House h = new House();
		Owner o = new Owner();
		User u = new User();
		
        int ownerId = 0;
        int existingUserId = 0;
        User existingUser = null;
        Owner existingOwner = null;
       
        
        // If there is an existing owner, create a house and update the Owner set its Multi owner column to 'Y's
        if (ValidationUtil.hasValue(existingOwnerId))
        {
       	 	ownerId = Integer.parseInt(existingOwnerId);
       	 
	       	 if (ownerId > 0)
	       	 {
	       		 // System.out.println("use existing owner");
                 logger.info("use existing owner");
                 
                 h.setAddressNumber(addressNumber);
                 h.setAddressStreet(addressStreet.toUpperCase());
                 h.setLotArea(Integer.parseInt(lotArea));
                 h.setTitle(title.toUpperCase());
                 h.setOwnerId(ownerId);
                 h.setType(ConstantsUtil.EMPTY_STRING);
                 h.setPhase(ConstantsUtil.ZERO);
                 h.setRented(ConstantsUtil.NO);
                 h.setCreatedAt(new Date());
                 h.setUpdatedAt(new Date());
                 
                 House house = houseService.addHouse(h);
                 
                 //System.out.println("house id is : " + house.getId());
                 logger.info("house id is : " + house.getId());

                 
                 // after adding another house for this owner, set the Multi Owner property of the user to "Y"
                 existingOwner = ownerService.getOwnerByOwnerId(ownerId);
                 
                 if (existingOwner != null)
                 {
                	 existingUserId = existingOwner.getUserId();
                	 
                	 if (existingUserId > 0)
                	 {
                		 existingUser = userService.getUserByUserId(existingUserId);
                		 
                		 if (existingUser != null)
                		 {
                			 existingUser.setMultiOwner("T");
                			 existingUser.setUpdatedAt(new Date());
                			 userService.updateUser(existingUser);
                		 }
                	 }
                 }
                 
                 return getMap(h);
            }
       	}
         
       
        // no existing owner, create House/Owner/User and add a new record to the ledger_summary table (initial values for the amount and penalty is 0.00)
        if (firstname.length() > 0 && lastname.length() > 0)
        {
	    	     System.out.println("Creating a new user...");
	             logger.info("Creating a new user...");
	             
	             String trimmedFirstname = firstname.replaceAll("\\s","");
	             String trimmedLastname = lastname.replaceAll("\\s","");
	                 
           		 u.setRoleId(3);
                 u.setPassword("123456");
                 u.setUsername(trimmedLastname.toLowerCase() + "." + trimmedFirstname.toLowerCase());
                 u.setStatus("A");
                 u.setMultiOwner("F");
                 u.setDelinquent("F");
                 u.setCreatedAt(new Date());
                 u.setUpdatedAt(new Date());

                 User user = userService.addUser(u);
                 
                 System.out.println("user id is : " + user.getId());
                 logger.info("user id is : " + user.getId());

                 o.setFirstname(firstname.toUpperCase());
                 o.setMiddlename(middlename.toUpperCase());
                 o.setLastname(lastname.toUpperCase());
                 o.setStatus("A");
                 o.setUserId(user.getId());
                 o.setCreatedAt(new Date());
                 o.setUpdatedAt(new Date());

                 // ADD NEW OWNER
                 System.out.println("Creating owner...");
	             logger.info("Creating owner...");
                 Owner owner = ownerService.addOwner(o);
                 
                 System.out.println("owner id is : " + owner.getId());
                 logger.info("owner id is : " + owner.getId());

                 h.setAddressNumber(addressNumber);
                 h.setAddressStreet(addressStreet.toUpperCase());
                 h.setTitle(title.toUpperCase());
                 h.setLotArea(Integer.parseInt(lotArea));
                 h.setOwnerId(owner.getId());
                 h.setRented("N");
                 h.setCreatedAt(new Date());
                 h.setUpdatedAt(new Date());

                 // ADD NEW HOUSE
                 System.out.println("Creating house...");
	             logger.info("Creating house...");
                 House house = houseService.addHouse(h);
                 
                 System.out.println("house id is : " + house.getId());
                 logger.info("house id is : " + house.getId());
                 
                 
                 // ADD A NEW RECORD TO ledger_summary TABLE (initial values for the amount and penalty is 0.00)
                 LedgerSummary ledgerSummary = ledgerService.createLedgerSummary(user.getId(), house.getId(), new Double("0.00"), new Double("0.00"));

                 return getMap(h);
             }
        	 else
             {
        		 logger.error("ERROR in saving house registration.");
                 return getModelMapError("ERROR in saving house registration.");
             }
    }
    

    
    
    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param User
     * @return
     */
    private Map<String, Object> getMap(House o) {
    	Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("data", o);
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
     * @param userService the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param ownerService the ownerService to set
     */
    @Autowired
    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * @param houseService the houseService to set
     */
    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }
    
    /**
     * @param ledgerService the ledgerService to set
     */
    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }
}

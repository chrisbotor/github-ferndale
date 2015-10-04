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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.model.House;
import com.homeportal.model.LedgerSummary;
import com.homeportal.model.Lessee;
import com.homeportal.model.Owner;
import com.homeportal.model.User;
import com.homeportal.service.HouseService;
import com.homeportal.service.LedgerService;
import com.homeportal.service.LesseeService;
import com.homeportal.service.OwnerService;
import com.homeportal.service.UserService;
import com.homeportal.util.ConstantsUtil;
import com.homeportal.util.ValidationUtil;


/**
 *
 * @author racs
 */
@Controller
public class AdminMoveInOutController 
{
	private static Logger logger = Logger.getLogger(AdminMoveInOutController.class);

    private UserService userService;
    private OwnerService ownerService;
    private HouseService houseService;
    private LesseeService lesseeService;
    private LedgerService ledgerService;
    
    
    
    
    /* FORM LOGIC
     * 
     * MOVE IN
     * Upon move in ng Owner, update House and update Owner updated_at property set to the day of move in
     * Upon move in ng Lessee, create User, Lessee record using house_id coming selected address. Update House with Rented = 'Y' and updated_at set to the day of move_in
     * 
     * MOVE OUT
     * SCENARIO 1: 1 house, no leesee
     * User table: set status to 'I' for Inactive (A is for Active)
     * Owner table: set status to 'I' for Inactive (A is for Active)
     * House table: set owner_id to zero (0)
     *  
     * SCENARIO 2: with leessee
     * User table: set status to 'I' for Inactive (A is for Active)
     * Leesee table: set status to 'I' for Inactive (A is for Active)
     *  
     */
   
   
    /**
     * Displays the Move in/Move out Form
     * */
    @RequestMapping("/admin-move-in-out")
    String getForm() throws Exception 
    {
         System.out.println("went to view of AdminMoveInOutController...");
         logger.info("went to view of AdminMoveInOutController...");
         
        return "admin-move-in-out";
    }
    
    
    /**
     * Used in saving the data during Move out (with or without a Lessee)
     * */
    @RequestMapping(value="/admin-move-out.action")
    public @ResponseBody
    Map<String, ? extends Object> saveMoveOutData(HttpServletRequest request, HttpSession session) throws Exception
   {
    	System.out.println("==== RACS Saving Move out details...");
    	logger.info("===== RACS Saving Move out details...");
    	
    	String isLessee = request.getParameter("lessee");
    	String userIdParam = request.getParameter("userId");
    	String houseIdParam = request.getParameter("houseIdItem");
    	String moveInOutDateParam = request.getParameter("moveInOutDate");
    	
    	String ownerIdParam = request.getParameter("ownerIdItem");
    	
    	logger.info("isLessee: " + isLessee);
    	logger.info("userId: " + userIdParam);
    	logger.info("houseId: " + houseIdParam);
    	logger.info("moveInOutDateParam: " + moveInOutDateParam);
    	logger.info("ownerId: " + ownerIdParam);
    	
    	System.out.println("isLessee: " + isLessee);
    	System.out.println("userId: " + userIdParam);
    	System.out.println("houseId: " + houseIdParam);
    	System.out.println("moveInOutDateParam: " + moveInOutDateParam); // TODO Convert this to the date then save in the updated_at column
    	System.out.println("ownerId: " + ownerIdParam);
    	
    	
    	User user = null;
    	Lessee lessee = null;
    	Owner owner = null;
    	
    	try
    	{
    		int userId = 0;
    		int houseId = 0;
    		int ownerId = 0;
    		
    		if (ValidationUtil.hasValue(userIdParam))
    		{
    			userId = Integer.parseInt(userIdParam);
    		}
    		
    		if (ValidationUtil.hasValue(houseIdParam))
    		{
    			houseId = Integer.parseInt(houseIdParam);
    		}
    		
    		if (ValidationUtil.hasValue(ownerIdParam))
    		{
    			ownerId = Integer.parseInt(ownerIdParam);
    		}
    		
    		
    		
    		if(ValidationUtil.hasValue(isLessee) && ConstantsUtil.YES.equals(isLessee))
    		{
    			user = userService.getUserByUserId(userId);
    			lessee = lesseeService.getLesseeByUserIdHouseId(userId, houseId);
    			
    			if (lessee != null)
    			{
    				lessee.setStatus(ConstantsUtil.INACTIVE);
    				lessee.setUpdatedAt(new Date());
    				
    				lesseeService.updateLessee(lessee);
    			}
    			
    			if (user != null)
    			{
    				user.setStatus(ConstantsUtil.INACTIVE);
    				user.setUpdatedAt(new Date());
    				
    				userService.updateUser(user);
    			}
    		}
    		else
    		{
    			owner = ownerService.getOwnerByOwnerId(ownerId);
    			
    			if (owner != null)
    			{
    				owner.setStatus(ConstantsUtil.INACTIVE);
    				owner.setUpdatedAt(new Date());
    				
    				ownerService.updateOwner(owner);
    				
    				user = userService.getUserByUserId(owner.getUserId());
    				
    				if (user != null)
        			{
        				user.setStatus(ConstantsUtil.INACTIVE);
        				user.setUpdatedAt(new Date());
        				
        				userService.updateUser(user);
        			}
    			}
    		}
    		
    		
    		Map<String, Object> modelMap = new HashMap<String, Object>(4);
    		
    		if (lessee != null)
    		{
    			modelMap.put("data", lessee);
    		}
    		
    		if (owner != null)
    		{
    			modelMap.put("data", owner);
    		}
    		
            modelMap.put("success", true);
            System.out.println(modelMap);
          
            return modelMap;
    		
    	}
    	catch (Exception e)
    	{
    		// logger.error("Error retrieving user from database.", e);
    		e.printStackTrace();
    		System.out.println("Error saving move out details. " + e.getMessage());
    		return getModelMapError("Error saving move out details. " + e.getMessage());
    	}
    }
    
    
    /**
     * Used in saving the data during Move in (with or without a Lessee)
     * */
    @RequestMapping(value="/admin-move-in.action")
    public @ResponseBody
    Map<String, ? extends Object> saveMoveInData(HttpServletRequest request, HttpSession session) throws Exception
   {
    	System.out.println("Saving Move in details...");
    	logger.info("Saving Move in details...");
    	
    	String ownerIdParam = request.getParameter("owner");
		String houseIdParam = request.getParameter("address");
		System.out.println("ownerIdParam: " + ownerIdParam);
		System.out.println("houseIdParam: " + houseIdParam);
		
		
		String ownerIdItemParam = request.getParameter("ownerIdItem");
		String houseIdItemParam = request.getParameter("houseIdItem");
		String rentedParam = request.getParameter("rented");
    	
		System.out.println("ownerIdItemParam: " + ownerIdItemParam);
		System.out.println("houseIdItemParam: " + houseIdItemParam);
		System.out.println("rentedParam: " + rentedParam);
		
		// LESSEEE
		String firstnameParam = request.getParameter("firstname");
		String middlenameParam = request.getParameter("middlename");
		String lastnameParam = request.getParameter("lastname");
		
		System.out.println("firstnameParam: " + firstnameParam);
		System.out.println("middlenameParam: " + middlenameParam);
		System.out.println("lastnameParam: " + lastnameParam);
    	
    	
    	try
    	{
    		//String ownerIdParam = request.getParameter("owner");
    		//String houseIdParam = request.getParameter("address");
    		String moveInDateParam = request.getParameter("moveIn");
    		
    		
    		
    		int owner = Integer.parseInt(ownerIdItemParam);
    		int house = Integer.parseInt(houseIdItemParam);
    		
    		
    		// get the Owner object using the above info
    		Owner movingInOwner = ownerService.getOwnerByOwnerId(owner);
    		// get the House object using the above info
    		House movingInHouse = houseService.getHouseByHouseId(house); 
    		
    		// once nakita na yung objects, use it just update the necessary files then rakenroll!:)
    		if (movingInOwner!= null)
    		{
    			movingInOwner.setUpdatedAt(new Date()); // SHOULD BE THE MOVE IN DATE!!
    			// ownerService.update(movingInOwner);
    			
    			ownerService.updateOwner(movingInOwner);
    		}
    		
    		
    		if (movingInHouse!= null)
    		{
    			movingInHouse.setUpdatedAt(new Date()); // SHOULD BE THE MOVE IN DATE!!
    			houseService.update(movingInHouse);
    		}
    		
    		
    		
    		// FOR THE LESSEE
    		if(ValidationUtil.hasValue(rentedParam) && "Y".equals(rentedParam))
    		{
    			if (ValidationUtil.hasValue(lastnameParam) && ValidationUtil.hasValue(firstnameParam)) // i set na REQUIRED ito
    			{
    				String username = lastnameParam.toLowerCase() + "." + firstnameParam.toLowerCase();
    				int userId = 0;
    				
    				// create new user
        			User user = new User();
        			user.setUsername(username);
        			user.setPassword("123456");
        			user.setRoleId(4);
        			user.setStatus("A");
        			user.setMultiOwner("F");
        			user.setCreatedAt(new Date());
        			user.setUpdatedAt(new Date());
        			user.setDelinquent("F");
        			userService.addUser(user);
        			
        			// once the user has been created, search for it to get its user id
        			User createdUser = userService.getUserByUsername(username);
        			if(createdUser != null)
        			{
        				userId = createdUser.getId();	
        			}
        			
        			
        			Lessee leesee = new Lessee();
        			leesee.setUserId(userId);
        			leesee.setHouseId(house);
        			leesee.setFirstname(firstnameParam);
        			leesee.setMiddlename(middlenameParam);
        			leesee.setLastname(lastnameParam);
        			leesee.setStatus("A");
        			leesee.setCreatedAt(new Date());
        			leesee.setUpdatedAt(new Date());
        			lesseeService.add(leesee);
        			movingInHouse.setRented("Y");
        			movingInHouse.setUpdatedAt(new Date());
        			houseService.update(movingInHouse);
        			
        			// ADD A NEW RECORD TO ledger_summary TABLE (initial values for the amount and penalty is 0.00)
                    ledgerService.createLedgerSummary(userId, house, new Double("0.00"), new Double("0.00"));
        		}
    		}
    		
    		
    		
    		Map<String, Object> modelMap = new HashMap<String, Object>(4);
    		modelMap.put("owner", movingInOwner);
            modelMap.put("house", movingInHouse);
    		modelMap.put("data", null);
            modelMap.put("success", true);
            System.out.println(modelMap);
          
            return modelMap;
    		
    	}
    	catch (Exception e)
    	{
    		// logger.error("Error retrieving user from database.", e);
    		e.printStackTrace();
    		System.out.println("Error getting association dues from database. " + e.getMessage());
    		return getModelMapError("Error getting association dues from database. " + e.getMessage());
    	}
    }
    
    
    
    
    

    @RequestMapping("/admin-moveIn")
    String view() throws Exception {
         System.out.println("went to view of AdminMoveInController");
        return "admin-moveIn";
    }
    
    
    /**
     * Used to save new House Registration
     * 
     * */
    @RequestMapping(value ="/admin-moveIn/addHouseOwner", method = RequestMethod.POST) 
    public @ResponseBody
    Map<String, ? extends Object> addHouseAndOwner(@RequestParam("addressNumber") String addressNumber, @RequestParam("addressStreet") String addressStreet,
            @RequestParam("lotArea") String lotArea, @RequestParam("title") String title,
            @RequestParam("firstname") String firstname,@RequestParam("middlename") String middlename,
            @RequestParam("lastname") String lastname, @RequestParam("existingOwnerId") String existingOwnerId,HttpSession session) throws Exception{
         
         /*System.out.println("went to Add of Houses and Owner AdminMoveInController");
         
         System.out.println("addressNumber: " + addressNumber);
         System.out.println("addressStreet: " + addressStreet);
         System.out.println("lotArea: " + lotArea);
         System.out.println("title: " + title);
         System.out.println("firstname: " + firstname);
         System.out.println("middlename: " + middlename);
         System.out.println("lastname: " + lastname);
         System.out.println("id: " + existingOwnerId);*/
         
         logger.info("========================================  ADDING HOUSE OWNER ==================================");
         logger.info("addressNumber: " + addressNumber);
         logger.info("addressStreet: " + addressStreet);
         logger.info("lotArea: " + lotArea);
         logger.info("title: " + title);
         logger.info("firstname: " + firstname);
         logger.info("middlename: " + middlename);
         logger.info("lastname: " + lastname);
         logger.info("id: " + existingOwnerId);
         
         
         Owner o = new Owner();
         User u = new User();
         House h = new House();
         
        int ownerId = 0;
        int existingUserId = 0;
        User existingUser = null;
        Owner existingOwner = null;
       
        
        if (ValidationUtil.hasValue(existingOwnerId))
        {
       	 	ownerId = Integer.parseInt(existingOwnerId);
       	 
	       	 if (ownerId > 0)
	       	 {
	       		 // System.out.println("used existing owner");
                 logger.info("use existing owner");
                 
                 h.setAddressNumber(addressNumber);
                 h.setAddressStreet(addressStreet.toUpperCase());
                 h.setLotArea(Integer.parseInt(lotArea));
                 h.setTitle(title.toUpperCase());
                 h.setOwnerId(ownerId);
                 h.setRented("N");
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
                			 userService.updateUser(existingUser);
                		 }
                	 }
                 }
                 
                 return getMap(h);
            }
       	}
         
       
         
	       if (firstname.length() > 0 && lastname.length() > 0)
	       {
	    	     System.out.println("Creating user...");
	             logger.info("Creating user...");
	             
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
    public void setLeeseeService(LesseeService lesseeService) {
        this.lesseeService = lesseeService;
    }
    
    /**
     * @param ledgerService the ledgerService to set
     */
    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }
}

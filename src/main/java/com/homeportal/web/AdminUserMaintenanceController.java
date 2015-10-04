package com.homeportal.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.homeportal.model.House;


/**
*
* @author racs
*/
@Controller
public class AdminUserMaintenanceController
{
	private static Logger logger = Logger.getLogger(AdminUserMaintenanceController.class);


	/**
	 * Used to Update information for House/Owner/Lessee in the admin main menu
	 * */
	@RequestMapping(value="/admin-user-maintenance.action", method=RequestMethod.GET)
	public ModelAndView index()
	{
		System.out.println("went to view of House/Owner/Lessee");
        logger.info("went to view of House/Owner/Lessee");
        
        ModelAndView mv = new ModelAndView("admin-user-maintenance");
        return mv;
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

}
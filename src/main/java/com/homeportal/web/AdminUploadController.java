package com.homeportal.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.bean.ExtJSFormResult;
import com.homeportal.bean.FileUploadBean;
import com.homeportal.bean.UserBean;
import com.homeportal.model.User;
import com.homeportal.util.MessageBundle;

/**
*
* @author ranievas
* */

@Controller
public class AdminUploadController
{
	private static Logger logger = Logger.getLogger(AdminUploadController.class);
	
	 
	@RequestMapping("/admin-upload-water")
	String viewUploadWater() throws Exception 
	{
		System.out.println("went to view of AdminUploadWaterController ");
		return "admin-upload-water";
	}
	
	
	@RequestMapping("/admin-upload-delinquent")
	String viewUploadDelinquent() throws Exception 
	{
		System.out.println("went to view of AdminUploadDelinquentController ");
	    return "admin-upload-delinquent";
	}
	
	
	
}




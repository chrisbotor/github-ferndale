package com.homeportal.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.homeportal.bean.ExtJSFormResult;
import com.homeportal.bean.FileUploadBean;
import com.homeportal.util.MessageBundle;

/**
 * Controller - Spring
 * 
 * @author Loiane Groner
 * http://loiane.com
 * http://loianegroner.com
 */
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {
	
	private static final String WATER_READING_DIR = MessageBundle.getProperty("water.reading.dir");
	private static final String DELINQUENT_DIR = MessageBundle.getProperty("delinquent.dir");

	@RequestMapping(value = "/water-reading.action", method = RequestMethod.POST)
	public @ResponseBody String uploadWater(FileUploadBean uploadItem, BindingResult result)
	{
		System.out.println("Uploading RACS file...");
		
		// System.out.println("Uploading " + session.getAttribute("file"));
	
		
		ExtJSFormResult extjsFormResult = new ExtJSFormResult();
		
		if (result.hasErrors()){
			for(ObjectError error : result.getAllErrors()){
				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
			}
			
			//set extjs return - error
			extjsFormResult.setSuccess(false);
			
			return extjsFormResult.toString();
		}

		// Some type of file processing...
		System.err.println("-------------------------------------------");
		System.err.println("Test upload: " + uploadItem.getFile().getOriginalFilename());
		// System.err.println("BYTES: " + uploadItem.getFile().getBytes());
		
		FileOutputStream fop = null;
		File file;
 
		try {
 
			file = new File(WATER_READING_DIR + uploadItem.getFile().getOriginalFilename());
			fop = new FileOutputStream(file);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = uploadItem.getFile().getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.err.println("-------------------------------------------");
		
		//set extjs return - sucsess
		extjsFormResult.setSuccess(true);
		
		System.out.println("RESULT: " + extjsFormResult.toString());
		return extjsFormResult.toString();
	}
	
	
	
	
	@RequestMapping(value = "/delinquent.action", method = RequestMethod.POST)
	public @ResponseBody String uploadDelinquent(FileUploadBean uploadItem, BindingResult result)
	{
		System.out.println("Uploading RACS file...");
		
		// System.out.println("Uploading " + session.getAttribute("file"));
	
		
		ExtJSFormResult extjsFormResult = new ExtJSFormResult();
		
		if (result.hasErrors()){
			for(ObjectError error : result.getAllErrors()){
				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
			}
			
			//set extjs return - error
			extjsFormResult.setSuccess(false);
			
			return extjsFormResult.toString();
		}

		// Some type of file processing...
		System.err.println("-------------------------------------------");
		System.err.println("Test upload: " + uploadItem.getFile().getOriginalFilename());
		// System.err.println("BYTES: " + uploadItem.getFile().getBytes());
		
		FileOutputStream fop = null;
		File file;
 
		try {
 
			file = new File(DELINQUENT_DIR + uploadItem.getFile().getOriginalFilename());
			fop = new FileOutputStream(file);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = uploadItem.getFile().getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.err.println("-------------------------------------------");
		
		//set extjs return - sucsess
		extjsFormResult.setSuccess(true);
		
		System.out.println("RESULT: " + extjsFormResult.toString());
		return extjsFormResult.toString();
	}

}

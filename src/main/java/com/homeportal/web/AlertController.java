package com.homeportal.web;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.EmployeeBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.bean.VehicleBean;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homeportal.model.Occupant;
import com.homeportal.service.AlertService;


@Controller
public class AlertController {
	
	private AlertService alertService;
	
	//Amenity Alerts
	@RequestMapping(value = "/alert/amenity/view.action", method=RequestMethod.POST)
    public @ResponseBody
    String viewAmenityAlerts() throws Exception {
        try {
        	System.out.println("inside AlertController...");
        	
        	List<AmenityBean> amenityRequests = alertService.getNewAmenityRequests();
        	System.out.println("@AlertController: #of Amenity Requests = " + amenityRequests.size());
        	
            return getObjectList(amenityRequests);
            
        }catch (Exception e) {
            System.out.println("Error retrieving Amenity Requests from database.");
            e.printStackTrace();
        	return "";
        }
    }
	
	
	//Service Alerts
	@RequestMapping(value = "/alert/service/view.action", method=RequestMethod.POST)
    public @ResponseBody
    String viewServiceAlerts() throws Exception {
        try {
        	List<ServiceBean> serviceRequests = alertService.getNewServiceRequests();
        	System.out.println("@AlertController: #of Service Requests = " + serviceRequests.size());
        	
            return getObjectList(serviceRequests);
            
        }catch (Exception e) {
            System.out.println("Error retrieving Service Requests from database.");
        	return "";
        }
    }
	
	//Employee Alerts
	@RequestMapping(value = "/alert/employee/view.action", method=RequestMethod.POST)
    public @ResponseBody
    String viewEmployeeAlerts() throws Exception {
        try {
        	List<EmployeeBean> newEmployees = alertService.getNewEmployees();
        	
        	System.out.println("@AlertController: #of new new Employees = " + newEmployees.size());
        	
            return getObjectList(newEmployees);
            
        }catch (Exception e) {
            System.out.println("Error retrieving new Employees from database.");
        	return "";
        }
    }
	
	
	//Occupant Alerts
	@RequestMapping(value = "/alert/occupant/view.action", method=RequestMethod.POST)
    public @ResponseBody
    String viewOccupantAlerts() throws Exception {
        try {
        	List<Occupant> newOccupants = alertService.getNewOccupants();
        	
        	System.out.println("@AlertController: #of new new Occupants = " + newOccupants.size());
        	
            return getObjectList(newOccupants);
            
        }catch (Exception e) {
            System.out.println("Error retrieving new Occupants from database.");
        	return "";
        }
    }
	
	
	//Vehicle Alerts
	@RequestMapping(value = "/alert/vehicle/view.action", method=RequestMethod.POST)
    public @ResponseBody
    String viewVehicleAlerts() throws Exception {
        try {
        	List<VehicleBean> newVehicles = alertService.getNewVehicles();
        	
        	System.out.println("@AlertController: #of new new Vehicles = " + newVehicles.size());
        	
            return getObjectList(newVehicles);
            
        }catch (Exception e) {
            System.out.println("Error retrieving new Vehicles from database.");
        	return "";
        }
    }
	
	
	
	
	@SuppressWarnings("rawtypes")
	private String getObjectList(List objectList) {
		 List<Object> mapList = new ArrayList<Object>();
		 
			 for(Object o:objectList){
				 mapList.add(createObjAttributesMap(o)); 
		     }
		 System.out.println("RETURNING... \n" + mapList.toString());
		 return mapList.toString();
	   }
	 
	 
	 private String createObjAttributesMap(Object obj){
		 StringBuilder sb = new StringBuilder();
		 
		 String objectClass = obj.getClass().toString();
		 
		  if(objectClass.contains("AmenityBean")){
			  AmenityBean ar = (AmenityBean) obj;
			  sb.append("{");
			  sb.append("\"id\":\"source/");
			  sb.append(ar.getAddress());
			  sb.append("\",\"text\":\"");
                          //sb.append("<a href='home.action'>"+ar.getAddress()+"</a>");
			  sb.append(ar.getAddress());
			  sb.append("\",\"leaf\":true,\"cls\":\"file\"}");
			
		  }else if(objectClass.contains("ServiceBean")){
			  ServiceBean sr = (ServiceBean) obj;
			  
			  sb.append("{");
			  sb.append("\"id\":\"source/");
			  sb.append(sr.getAddress());
			  sb.append("\",\"text\":\"");
			  sb.append(sr.getAddress());
			  sb.append("\",\"leaf\":true,\"cls\":\"file\"}");
		   
		  }else if(objectClass.contains("Employee")){
			  EmployeeBean e = (EmployeeBean) obj;
			  
			  sb.append("{");
			  sb.append("\"id\":\"source/");
			  sb.append(e.getAddress());
			  sb.append("\",\"text\":\"");
			  sb.append(e.getAddress());
			  sb.append("\",\"leaf\":true,\"cls\":\"file\"}");
		   
		  }else if(objectClass.contains("Vehicle")){
			  VehicleBean v = (VehicleBean) obj;
			  
			  sb.append("{");
			  sb.append("\"id\":\"source/");
			  sb.append(v.getAddress());
			  sb.append("\",\"text\":\"");
			  sb.append(v.getAddress());
			  sb.append("\",\"leaf\":true,\"cls\":\"file\"}");
		   }
		  
		 return sb.toString();
	 }

	 
	 @Autowired
	    public void setAlertService(AlertService alertService) {
	        this.alertService = alertService;
	    }
}

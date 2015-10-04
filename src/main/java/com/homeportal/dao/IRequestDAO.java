package com.homeportal.dao;

import java.util.List;
import com.homeportal.bean.RequestBean;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.ServiceRequest;


public interface IRequestDAO 
{
	public List<Object> getRequests(int jobOrderTypeID);
	
	public List<RequestBean> getRequestors();
	
	public boolean insertAmenityRequest(AmenitiesRequest amenityRequest);
	
	public boolean insertServiceRequest(ServiceRequest serviceRequest);
	

}

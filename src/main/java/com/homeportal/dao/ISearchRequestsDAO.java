/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.ServiceBean;

import java.util.List;

/**
 *
 * @author ranievas
 */
public interface ISearchRequestsDAO 
{
	// AMENITY
	List<AmenityBean> searchAmenityRequestsAll();
	
	List<AmenityBean> searchAmenityRequestsByAmenityOnly(int amenityId);
	
	List<AmenityBean> searchAmenityRequestsByFromDateOnly(String fromDate);
	
	List<AmenityBean> searchAmenityRequestsByFromDateAndToDate(String fromDate, String toDate);
	
	List<AmenityBean> searchAmenityRequestsByAmenityAndStatus(int amenityId, String status);
	
	List<AmenityBean> searchAmenityRequestsByStatusOnly(String status);
	
	List<AmenityBean> searchAmenityRequestsByRequestorOnly(int userId, int houseId);
	
	List<AmenityBean> searchAmenityRequestsByRequestorAndAmenity(int userId, int houseId, int amenityId);
	
	List<AmenityBean> searchAmenityRequestsByRequestorAndStatus(int userId, int houseId, String status);
	
	List<AmenityBean> searchAmenityRequestsByRequestorAmenityAndStatus(int userId, int houseId, int amenityId, String status);
	
	
	// SERVICE
	List<ServiceBean> searchServiceRequestsByServiceOnly(int serviceId);
	
	List<ServiceBean> searchServiceRequestsByFromDateOnly(String fromDate);
	
	List<ServiceBean> searchServiceRequestsByFromDateAndToDate(String fromDate, String toDate);
	
	List<ServiceBean> searchServiceRequestsByServiceAndStatus(int serviceId, String status);
	
	List<ServiceBean> searchServiceRequestsByStatusOnly(String status);
	
	List<ServiceBean> searchServiceRequestsByRequestorAndService(int userId, int houseId, int serviceId);
	
	List<ServiceBean> searchServiceRequestsByRequestorAndStatus(int userId, int houseId, String status);
	
	List<ServiceBean> searchServiceRequestsByRequestorServiceAndStatus(int userId, int houseId, int amenityId, String status);
	
	List<ServiceBean> searchServiceRequestsAll();
	
	List<ServiceBean> searchServiceRequestsByRequestorOnly(int userId, int houseId);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import com.homeportal.bean.AmenityBean;
import com.homeportal.model.AmenitiesRequest;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author PSP36488
 */
public interface IAmenitiesRequestDAO {

    List<AmenityBean> getAmenities_Requests();
    List<AmenityBean> getAmenities_RequestsByAdmin(int userId, String status, String requestedDate, int amenityId);
    List<AmenityBean> getAmenitiesRequests(int userId, int houseId);
    void deleteAmenities_Requests(int id);
    AmenitiesRequest saveAmenitiesRequest(AmenitiesRequest ar) throws SQLException;
    AmenitiesRequest updateAmenities_Request(AmenitiesRequest ar);
    AmenitiesRequest updateAmenities_RequestViaPortal(AmenitiesRequest ar, String uom);
    AmenitiesRequest updateAmenities_RequestViaAdmin(AmenitiesRequest ar);
    Double updatePaidAmount(Double paidAmount, int requestId, Double amount);
    AmenitiesRequest getAmenityRequestById(int id) throws SQLException;
    
    // TO COMPUTE AMENITY FEE VIA AJAX
    Double computeAmenityFee(int amenityId, int hoursOrPcs);
    
    // TO CHECK IF THERE IS AN OVERLAPPING DATE OR TIME WHEN CREATING A REQUEST
    boolean checkAmenityRequest(int amenityId, String date, String startTime, String endTime);
    
}

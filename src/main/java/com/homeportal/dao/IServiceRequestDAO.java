/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;
import com.homeportal.bean.ServiceBean;
import com.homeportal.model.ServiceRequest;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author PSP36488
 */
public interface IServiceRequestDAO {

    List<ServiceBean> getService_Requests();
    List<ServiceBean> getService_Requests(int id, int houseId);
    List<ServiceBean> getService_RequestsByAdmin(int userId, String status,String requestedDate, int serviceId);
    void deleteService_Requests(int id);
    // ServiceRequest saveService_Requests(ServiceRequest ar);
    ServiceRequest updateService_Request(ServiceRequest ar);
    ServiceRequest updateService_RequestViaPortal(ServiceRequest ar, String uom);
    ServiceRequest updateService_RequestViaAdmin(ServiceRequest ar);
    public Double updatePaidAmount(Double paidAmount, int requestId, Double amount);
    
    // computes for Service Fee
    public Double computeServiceFee(int serviceId, int param2);
    public ServiceRequest saveServiceRequest(ServiceRequest sr) throws SQLException;
    public ServiceRequest getServiceRequestById(int id) throws SQLException;
}

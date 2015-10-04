/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import com.homeportal.model.Services;

import java.util.List;

/**
 *
 * @author PSP36488
 */
public interface IServiceDAO {

    List<Services> getServices();
    void deleteServices(int id);
    Services saveServices(Services service);
    Services updateServices(Services service);
    boolean validateServiceRequestAndDateOverlap(int serviceReqId, String date);
}

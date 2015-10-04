/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.bean;

import com.homeportal.model.Employee;

/**
 *
 * @author Peter
 */
public class EmployeeBean extends Employee{
    
    private String address;
    private String requestor;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the requestor
     */
    public String getRequestor() {
        return requestor;
    }

    /**
     * @param requestor the requestor to set
     */
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }
}

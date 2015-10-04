/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.bean;

import org.apache.log4j.Logger;

import com.homeportal.dao.impl.BillingDaoImpl;

/**
 *
 * @author Peter
 */
public class BillingBean {
	
	private static Logger logger = Logger.getLogger(BillingBean.class);
    
    private int id;
    private int userId;
    private int requestId;
    private int orderTypeId;
    private String reference;
    private String description;
    private String requestedDate;
    private String postedDate;
    private Double amount;
    private String address;
    private String fullname;
    private Double paidAmount;
    private Double amountToPay;
    private Double balance;
    private String payeeName;
    private String payeeAddress;
    private int roleId;
    private int orNumber;
    private String reportName;
    private int houseId;

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the requestedDate
     */
    public String getRequestedDate() {
        return requestedDate;
    }

    /**
     * @param requestedDate the requestedDate to set
     */
    public void setRequestedDate(String requestedDate) {
    	logger.info("======================== SETTING requestedDate: "  + requestedDate);
    	logger.info("======================== CLASS of requestedDate: "  + requestedDate.getClass());
    	
        this.requestedDate = requestedDate;
    }

    /**
     * @return the price
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param price the price to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

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
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
        logger.info("======================== SETTING fullname: "  + fullname);
    	logger.info("======================== CLASS of setFullName: "  + requestedDate.getClass());
    }

    /**
     * @return the postedDate
     */
    public String getPostedDate() {
        return postedDate;
    }

    /**
     * @param postedDate the postedDate to set
     */
    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    /**
     * @return the paidAmount
     */
    public Double getPaidAmount() {
        return paidAmount;
    }

    /**
     * @param paidAmount the paidAmount to set
     */
    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * @return the jobOrderId
     */
    public int getId() {
        return id;
    }

    /**
     * @param jobOrderId the jobOrderId to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the amountToPay
     */
    public Double getAmountToPay() {
        return amountToPay;
    }

    /**
     * @param amountToPay the amountToPay to set
     */
    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    /**
     * @return the requestId
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the orderTypeId
     */
    public int getOrderTypeId() {
        return orderTypeId;
    }

    /**
     * @param orderTypeId the orderTypeId to set
     */
    public void setOrderTypeId(int orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    /**
     * @return the balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * @return the payeeName
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * @param payeeName the payeeName to set
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * @return the payeeAddress
     */
    public String getPayeeAddress() {
        return payeeAddress;
    }

    /**
     * @param payeeAddress the payeeAddress to set
     */
    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    /**
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the orNumber
     */
    public int getOrNumber() {
        return orNumber;
    }

    /**
     * @param orNumber the orNumber to set
     */
    public void setOrNumber(int orNumber) {
        this.orNumber = orNumber;
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the houseId
     */
    public int getHouseId() {
        return houseId;
    }

    /**
     * @param houseId the houseId to set
     */
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    
    
}

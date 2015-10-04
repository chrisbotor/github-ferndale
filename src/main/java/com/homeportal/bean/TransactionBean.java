/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.bean;

/**
 *
 * @author Peter
 */
public class TransactionBean {
    
    private int req_id; // test
    private int job_order_id;
    private int order_type_id;
    private String referenceNumber;
    private String requestId;
    private int userId;
    private String description;
    private String requestedDate;
    private String postedDate;
    private int quantity;
    private Double basic_cost;
    private Double additional_cost;
    private Double otherCharges;
    private Double total_cost;
    private String uom;

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
        this.requestedDate = requestedDate;
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
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the basic_cost
     */
    public Double getBasic_cost() {
        return basic_cost;
    }

    /**
     * @param basic_cost the basic_cost to set
     */
    public void setBasic_cost(Double basic_cost) {
        this.basic_cost = basic_cost;
    }

    /**
     * @return the additional_cost
     */
    public Double getAdditional_cost() {
        return additional_cost;
    }

    /**
     * @param additional_cost the additional_cost to set
     */
    public void setAdditional_cost(Double additional_cost) {
        this.additional_cost = additional_cost;
    }

    /**
     * @return the otherCharges
     */
    public Double getOtherCharges() {
        return otherCharges;
    }

    /**
     * @param otherCharges the otherCharges to set
     */
    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    /**
     * @return the total_cost
     */
    public Double getTotal_cost() {
        return total_cost;
    }

    /**
     * @param total_cost the total_cost to set
     */
    public void setTotal_cost(Double total_cost) {
        this.total_cost = total_cost;
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
     * @return the job_order_id
     */
    public int getJob_order_id() {
        return job_order_id;
    }

    /**
     * @param job_order_id the job_order_id to set
     */
    public void setJob_order_id(int job_order_id) {
        this.job_order_id = job_order_id;
    }

    /**
     * @return the order_type_id
     */
    public int getOrder_type_id() {
        return order_type_id;
    }

    /**
     * @param order_type_id the order_type_id to set
     */
    public void setOrder_type_id(int order_type_id) {
        this.order_type_id = order_type_id;
    }

    /**
     * @return the referenceNumber
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * @param referenceNumber the referenceNumber to set
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * @return the req_id
     */
    public int getReq_id() {
        return req_id;
    }

    /**
     * @param req_id the req_id to set
     */
    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    /**
     * @return the uom
     */
    public String getUom() {
        return uom;
    }

    /**
     * @param uom the uom to set
     */
    public void setUom(String uom) {
        this.uom = uom;
    }
}

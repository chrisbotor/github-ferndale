/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 *
 * @author Peter
 */
@JsonAutoDetect
@Entity
@Table(name = "job_orders")
public class JobOrder {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "house_id")
    private int houseId;
    @Column(name = "order_type_id")
    private int orderTypeId;
    @Column(name = "request_id")
    private int requestId;
    @Column(name = "price")
    private double price;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    
}

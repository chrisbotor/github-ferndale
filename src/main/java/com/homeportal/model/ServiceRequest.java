/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonAutoDetect;



/**
 *
 * @author Racs
 */
@JsonAutoDetect
@Entity
@Table(name = "service_request")
public class ServiceRequest 
{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "house_id")
    private int houseId;
    
    @Column(name = "service_id")
    private int serviceId;
    
    @Column(name = "additional_cost")
    private double additionalCost;
    
    @Column(name = "basic_cost")
    private double basicCost;
    
    @Column(name = "total_cost")
    private double totalCost;
    
    @Column(name="preferred_date")
    private String preferredDate;
    
    @Column(name="preferred_time")
    private String preferredTime;
    
    @Column(name="confirmed_date")
    private String confirmedDate;
    
    @Column(name="status")
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_At", nullable = true)
    private Date createdAt;
     
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_At", nullable = true)
    private Date updatedAt;
    
    @Column(name="quantity")
    private int quantity;
    
    @Column(name="remarks")
    private String remarks;
    
    @Column(name = "other_charges")
    private double otherCharges;
    
    @Column(name = "paid_amount")
    private double paidAmount;
    
    @Column(name="misc_names")
    private String miscNames;
    
    @Column(name="payeeName")
    private String payeeName;
    
    @Column(name="billed")
    private int billed;
    
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	
	public double getAdditionalCost() {
		return additionalCost;
	}

	public void setAdditionalCost(double additionalCost) {
		this.additionalCost = additionalCost;
	}

	public double getBasicCost() {
		return basicCost;
	}

	public void setBasicCost(double basicCost) {
		this.basicCost = basicCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getPreferredDate() {
		return preferredDate;
	}

	public void setPreferredDate(String preferredDate) {
		this.preferredDate = preferredDate;
	}

	public String getPreferredTime() {
		return preferredTime;
	}

	public void setPreferredTime(String preferredTime) {
		this.preferredTime = preferredTime;
	}

	public String getConfirmedDate() {
		return confirmedDate;
	}

	public void setConfirmedDate(String confirmedDate) {
		this.confirmedDate = confirmedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getMiscNames() {
		return miscNames;
	}

	public void setMiscNames(String miscNames) {
		this.miscNames = miscNames;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public int getBilled() {
		return billed;
	}

	public void setBilled(int billed) {
		this.billed = billed;
	}
    
}

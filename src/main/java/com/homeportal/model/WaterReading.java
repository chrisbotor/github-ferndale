package com.homeportal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;


@JsonAutoDetect
@Entity
@Table(name = "water_reading")
public class WaterReading 
{
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "house_id")
	private int houseId;
	
	@Column(name = "billing_month")
	private String billing_month;
	
	@Column(name = "posted_date")
	private String postedDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "start_reading")
	private Double startReading;
    
	@Column(name = "end_reading")
	private Double endReading;
	
	@Column(name = "consumption")
	private Double consumption;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "paid_amount")
	private Double paidAmount;
	
	@Column(name = "billed")
	private int billed;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "used_month")
	private String usedMonth;

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

	public String getBilling_month() {
		return billing_month;
	}

	public void setBilling_month(String billing_month) {
		this.billing_month = billing_month;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getStartReading() {
		return startReading;
	}

	public void setStartReading(Double startReading) {
		this.startReading = startReading;
	}

	public Double getEndReading() {
		return endReading;
	}

	public void setEndReading(Double endReading) {
		this.endReading = endReading;
	}

	public Double getConsumption() {
		return consumption;
	}

	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public int getBilled() {
		return billed;
	}

	public void setBilled(int billed) {
		this.billed = billed;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getUsedMonth() {
		return usedMonth;
	}

	public void setUsedMonth(String usedMonth) {
		this.usedMonth = usedMonth;
	}
	
}

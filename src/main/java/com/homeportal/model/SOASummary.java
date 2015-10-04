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
 *  Class that contains the SOA summary to be used when generating Statement of accounts PDF
 * */
@JsonAutoDetect
@Entity
@Table(name = "soa_summary")
public class SOASummary
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
	private String billingMonth;
	 
	@Column(name = "payment")
	private double payment;
	
	@Column(name = "charges")
	private double charges;
	
	@Column(name = "balance")
	private double balance;
	
	@Column(name = "total_balance")
	private double totalBalance;
     
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_At", nullable = true)
    private Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_At", nullable = true)
    private Date updatedAt;

    
    
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

	public String getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getCharges() {
		return charges;
	}

	public void setCharges(double charges) {
		this.charges = charges;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
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

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

}

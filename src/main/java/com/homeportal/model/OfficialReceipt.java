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
 * @author Peter
 */
@JsonAutoDetect
@Entity
@Table(name = "official_receipt")
public class OfficialReceipt {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "or_number")
    private int orNumber;
    
    @Column(name = "job_order_id")
    private int jobOrderId;
    
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "house_id")
    private int houseId;
    
    @Column(name = "amount")
    private double amount;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_At", nullable = true)
    private Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_At", nullable = true)
    private Date updatedAt;
    
    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "cheque_number")
    private String chequeNumber;
    
    @Column(name = "status")
    private String status;
    
    
   
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
     * @return the jobOrderId
     */
    public int getJobOrderId() {
        return jobOrderId;
    }

    /**
     * @param jobOrderId the jobOrderId to set
     */
    public void setJobOrderId(int jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
    
    
 
    public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

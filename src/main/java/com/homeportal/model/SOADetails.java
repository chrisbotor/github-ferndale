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
 *  Class that contains the SOA details to be used when generating Statement of accounts PDF
 * */
@JsonAutoDetect
@Entity
@Table(name = "soa_details")
public class SOADetails
{
	@Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "soa_header_id")
    private int soaHeaderId;
    
    @Column(name = "ref_num")
    private String refNum;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "amount")
    private double amount;
    
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

	public int getSoaHeaderId() {
		return soaHeaderId;
	}

	public void setSoaHeaderId(int soaHeaderId) {
		this.soaHeaderId = soaHeaderId;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
    
}

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
 * @author racs
 */
@JsonAutoDetect
@Entity
@Table(name = "official_receipt_header")
public class OfficialReceiptHeader 
{
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "or_number")
    private int orNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "or_date", nullable = true)
    private Date orDate;
    
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "house_id")
    private int houseId;
    
    @Column(name = "payee_name")
    private String payeeName;
    
    @Column(name = "payee_address")
    private String payeeAddress;
    
    @Column(name = "bank_name")
    private String bankName;
    
    @Column(name = "cheque_number")
    private String chequeNumber;
    
    
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrNumber() {
		return orNumber;
	}

	public void setOrNumber(int orNumber) {
		this.orNumber = orNumber;
	}

	public Date getOrDate() {
		return orDate;
	}

	public void setOrDate(Date orDate) {
		this.orDate = orDate;
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

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeAddress() {
		return payeeAddress;
	}

	public void setPayeeAddress(String payeeAddress) {
		this.payeeAddress = payeeAddress;
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
    
}

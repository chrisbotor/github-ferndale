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
 * @author racs
 */
@JsonAutoDetect
@Entity
@Table(name = "official_receipt_details")
public class OfficialReceiptDetails 
{
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "header_id")
    private int headerId;
    
    @Column(name = "ref_num")
    private int refNum;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "amount")
    private double amount;
    
    @Column(name = "cost")
    private double cost;
    
    @Column(name = "excess")
    private double excess;

    
    
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHeaderId() {
		return headerId;
	}

	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}

	public int getRefNum() {
		return refNum;
	}

	public void setRefNum(int refNum) {
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

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getExcess() {
		return excess;
	}

	public void setExcess(double excess) {
		this.excess = excess;
	}
}

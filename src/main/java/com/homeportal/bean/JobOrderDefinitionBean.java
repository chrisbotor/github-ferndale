package com.homeportal.bean;

import java.math.BigDecimal;
import java.util.Date;


public class JobOrderDefinitionBean {
	
    
	private int id;
    
	private int jobOrderTypeId;
	
    private String code;
    
    private String description;
    
    private Double regValue;
    
    private String uom;
    
    private int maxRegular;
    
    private Double excessValue;
    
    private Date createdAt;
    
    private Date updatedAt;

	
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJobOrderTypeId() {
		return jobOrderTypeId;
	}

	public void setJobOrderTypeId(int jobOrderTypeId) {
		this.jobOrderTypeId = jobOrderTypeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getMaxRegular() {
		return maxRegular;
	}

	public void setMaxRegular(int maxRegular) {
		this.maxRegular = maxRegular;
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

	public Double getRegValue() {
		return regValue;
	}

	public void setRegValue(Double regValue) {
		this.regValue = regValue;
	}

	public Double getExcessValue() {
		return excessValue;
	}

	public void setExcessValue(Double excessValue) {
		this.excessValue = excessValue;
	}
    
	
 
}

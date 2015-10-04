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
@Table(name = "house")
public class House {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "owner_id")
    private int ownerId;
    @Column(name = "addr_number")
    private String addressNumber;
    @Column(name = "addr_street")
    private String addressStreet;
    @Column(name = "type")
    private String type;
    @Column(name = "phase")
    private int phase;
    @Column(name = "rented")
    private String rented;
    @Column(name = "title")
    private String title;
    @Column(name = "lot_area")
    private int lotArea;
    @Column(name = "created_At", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_At", nullable = true)
    private Date updatedAt;

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
     * @return the ownerId
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the addressNumber
     */
    public String getAddressNumber() {
        return addressNumber;
    }

    /**
     * @param addressNumber the addressNumber to set
     */
    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    /**
     * @return the addressStreet
     */
    public String getAddressStreet() {
        return addressStreet;
    }

    /**
     * @param addressStreet the addressStreet to set
     */
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    /**
     * @return the rented
     */
    public String getRented() {
        return rented;
    }

    /**
     * @param rented the rented to set
     */
    public void setRented(String rented) {
        this.rented = rented;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the lotArea
     */
    public int getLotArea() {
        return lotArea;
    }

    /**
     * @param lotArea the lotArea to set
     */
    public void setLotArea(int lotArea) {
        this.lotArea = lotArea;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

}

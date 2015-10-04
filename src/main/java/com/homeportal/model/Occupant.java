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
@Table(name = "occupant")
public class Occupant {
    
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "house_id")
    private int houseId;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "middlename")
    private String middlename;
    @Column(name = "relation_to_principal")
    private String relationTo;
     @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_At", nullable = true)
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
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the middlename
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * @param middlename the middlename to set
     */
    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    /**
     * @return the relationTo
     */
    public String getRelationTo() {
        return relationTo;
    }

    /**
     * @param relationTo the relationTo to set
     */
    public void setRelationTo(String relationTo) {
        this.relationTo = relationTo;
    }
    
}

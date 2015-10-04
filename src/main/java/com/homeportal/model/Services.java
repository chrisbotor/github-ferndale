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
 * @author PSP36488
 */
@JsonAutoDetect
@Entity
@Table(name = "service")
public class Services {

   @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
    @Column(name = "reg_value")
    private Double reg_value;
    @Column(name = "excess_value")
    private Double excess_value;
    @Column(name = "max_regular")
    private int max_regular;
    @Column(name = "uom")
    private String uom;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
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
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the reg_value
     */
    public Double getReg_value() {
        return reg_value;
    }

    /**
     * @param reg_value the reg_value to set
     */
    public void setReg_value(Double reg_value) {
        this.reg_value = reg_value;
    }

    /**
     * @return the excess_value
     */
    public Double getExcess_value() {
        return excess_value;
    }

    /**
     * @param excess_value the excess_value to set
     */
    public void setExcess_value(Double excess_value) {
        this.excess_value = excess_value;
    }

    /**
     * @return the uom
     */
    public String getUom() {
        return uom;
    }

    /**
     * @param uom the uom to set
     */
    public void setUom(String uom) {
        this.uom = uom;
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
     * @return the max_regular
     */
    public int getMax_regular() {
        return max_regular;
    }

    /**
     * @param max_regular the max_regular to set
     */
    public void setMax_regular(int max_regular) {
        this.max_regular = max_regular;
    }

    
}

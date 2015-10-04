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
@Table(name = "or_series")
public class OrSeries {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "start_series")
    private int startSeries;
    @Column(name = "end_series")
    private int endSeries;
    @Column(name = "current")
    private int current;
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
     * @return the startSeries
     */
    public int getStartSeries() {
        return startSeries;
    }

    /**
     * @param startSeries the startSeries to set
     */
    public void setStartSeries(int startSeries) {
        this.startSeries = startSeries;
    }

    /**
     * @return the endSeries
     */
    public int getEndSeries() {
        return endSeries;
    }

    /**
     * @param endSeries the endSeries to set
     */
    public void setEndSeries(int endSeries) {
        this.endSeries = endSeries;
    }

    /**
     * @return the current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(int current) {
        this.current = current;
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
}

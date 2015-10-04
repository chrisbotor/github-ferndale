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
@Table(name = "leesee")
public class Lessee {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name="user_id")
    private int userId;
    @Column(name = "house_id")
    private int houseId;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "middlename")
    private String middlename;
    @Column(name = "civil_status")
    private String civilStatus;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "home_number")
    private String homeNumber;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "work_name")
    private String workName;
    @Column(name = "work_address")
    private String workAddress;
    @Column(name = "work_landline")
    private String workLandline;
    @Column(name = "work_mobile")
    private String workMobile;
    @Column(name = "work_email")
    private String workEmail;
    @Column(name = "status")
    private String status;
    @Column(name = "birthdate")
    //@Temporal(TemporalType.DATE)
    private String birthdate;
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
     * @return the civilStatus
     */
    public String getCivilStatus() {
        return civilStatus;
    }

    /**
     * @param civilStatus the civilStatus to set
     */
    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the homeNumber
     */
    public String getHomeNumber() {
        return homeNumber;
    }

    /**
     * @param homeNumber the homeNumber to set
     */
    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the workName
     */
    public String getWorkName() {
        return workName;
    }

    /**
     * @param workName the workName to set
     */
    public void setWorkName(String workName) {
        this.workName = workName;
    }

    /**
     * @return the workAddress
     */
    public String getWorkAddress() {
        return workAddress;
    }

    /**
     * @param workAddress the workAddress to set
     */
    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    /**
     * @return the workLandline
     */
    public String getWorkLandline() {
        return workLandline;
    }

    /**
     * @param workLandline the workLandline to set
     */
    public void setWorkLandline(String workLandline) {
        this.workLandline = workLandline;
    }

    /**
     * @return the workMobile
     */
    public String getWorkMobile() {
        return workMobile;
    }

    /**
     * @param workMobile the workMobile to set
     */
    public void setWorkMobile(String workMobile) {
        this.workMobile = workMobile;
    }

    /**
     * @return the workEmail
     */
    public String getWorkEmail() {
        return workEmail;
    }

    /**
     * @param workEmail the workEmail to set
     */
    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

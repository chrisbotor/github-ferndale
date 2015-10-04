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


@JsonAutoDetect
@Entity
@Table(name="user_roles")
public class UserRole {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
     @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_At", nullable = true)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_At", nullable = true)
    private Date updatedAt;
}

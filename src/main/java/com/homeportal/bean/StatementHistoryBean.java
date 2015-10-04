/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.bean;

/**
 *
 * @author Peter
 */
public class StatementHistoryBean {
    
    private int userId;
    private String reportName;

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
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}

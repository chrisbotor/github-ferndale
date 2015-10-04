/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.model.JobOrder;
import java.util.List;

/**
 *
 * @author Peter
 */
public interface IJobOrderDAO {
 
    List <JobOrder> getJobOrder();
    JobOrder saveJobOrder(JobOrder jobOrder);
    JobOrder updateJobOrder(JobOrder jobOrder);
}

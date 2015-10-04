/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.dao.impl.JobOrderDaoImpl;
import com.homeportal.model.JobOrder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class JobOrderService {
    
    private JobOrderDaoImpl jobOrderDaoImpl;
    
    	 
	@Transactional(readOnly=true)
	public List<JobOrder>  getJobOrderList(){
            return jobOrderDaoImpl.getJobOrder();
	}
        
        @Transactional()
	public JobOrder  addJobOrder(JobOrder jobOrder){
            return jobOrderDaoImpl.saveJobOrder(jobOrder);
	}
        
        @Transactional()
	public  JobOrder  updateJobOrder(JobOrder jobOrder){
            return jobOrderDaoImpl.updateJobOrder(jobOrder);
	}

    

    /**
     * @param jobOrderDaoImpl the jobOrderDaoImpl to set
     */
    @Autowired
    public void setJobOrderDaoImpl(JobOrderDaoImpl jobOrderDaoImpl) {
        this.jobOrderDaoImpl = jobOrderDaoImpl;
    }
    
}

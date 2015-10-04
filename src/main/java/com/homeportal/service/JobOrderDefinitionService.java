package com.homeportal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.dao.impl.JobOrderDefinitionDaoImpl;
import com.homeportal.bean.JobOrderDefinitionBean;


/**
*
* @author Racs
*/
@Service
public class JobOrderDefinitionService 
{
	
	  private JobOrderDefinitionDaoImpl jobOrderDefinitionDaoImpl;
	   
	  
	  /**
	     * Retrieves all the Job Orders for a given jobOrderTypeId
	     * 
	     * @param jobOrderTypeId - the job order type id
	     * @return list of JobOrderDefinitionBean objects
	     */
	    @Transactional(readOnly = true)
	    public List<JobOrderDefinitionBean> getJobOrderDefinitionList(int jobOrderTypeId) {
	        return jobOrderDefinitionDaoImpl.getJobOrderBasedOnJobOrderType(jobOrderTypeId);
	    }
	    
	    
	    /**
	     * @param jobOrderDefinitionDaoImpl the jobOrderDefinitionDaoImpl to set
	     */
	    @Autowired
	    public void setJobOrderDefinitionDaoImpl(JobOrderDefinitionDaoImpl jobOrderDefinitionDaoImpl) {
	        this.jobOrderDefinitionDaoImpl = jobOrderDefinitionDaoImpl;
	    }

}

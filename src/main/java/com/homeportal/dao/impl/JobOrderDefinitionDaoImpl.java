package com.homeportal.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.homeportal.bean.JobOrderDefinitionBean;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;


/*
 * Class used to get or update Amenities table
 * */
@Repository
public class JobOrderDefinitionDaoImpl {
	private static Logger logger = Logger.getLogger(JobOrderDefinitionDaoImpl.class);

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private static final String GET_JOD_AMENITY_SQL = MessageBundle.getSqlProperty("get.jod.amenity.sql");
	private static final String GET_JOD_SERVICE_SQL = MessageBundle.getSqlProperty("get.jod.service.sql");
	private static final String GET_JOD_SQL_SCALAR = MessageBundle.getSqlProperty("get.jod.sql.scalar");
	
	
	@SuppressWarnings("unchecked")
	public List<JobOrderDefinitionBean> getJobOrderBasedOnJobOrderType(int jobOrderTypeId) {
		System.out.println("\nDAO getting Job Order Definition...");
		logger.info("DAO getting Job Order Definition...");
		
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		
		List<JobOrderDefinitionBean> jodList = null;
		List<String> scalarPropsList = null;
		
		
		if (jobOrderTypeId != 0)
		{
			// map the scalar properties
			scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_JOD_SQL_SCALAR);
			
			// System.out.println("======================== scalarPropsList ====================  \n" + scalarPropsList);
			
			if (jobOrderTypeId == 1)
			{
				// create SQL query for AMENITY
				if (scalarPropsList != null)
				{
					query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_JOD_AMENITY_SQL);
				}
			}
			else if (jobOrderTypeId == 2)
			{
				// create SQL query for SERVICE
				if (scalarPropsList != null)
				{
					query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_JOD_SERVICE_SQL);
					// System.out.println("GOT QUERY: \n" + query);
				}
			}
			
			
			// map result set to JobOrderDefinitionBean class
			try
			{
				// System.out.println("======================== setting Transformer values  ===========  \n" + scalarPropsList);
				
				query.setResultTransformer(Transformers.aliasToBean(JobOrderDefinitionBean.class));
				
				// System.out.println("======================== AFTER setting Transformer values  ===========  \n" + scalarPropsList);
			}
			catch(Exception ex)
			{
				logger.info("EXCEPTION: Error mapping result to " + ex.getMessage());
			}
			
			
			jodList = (List<JobOrderDefinitionBean>) query.list();   // query is NULL
			
			if (jodList != null)
			{
				System.out.println("Got " + jodList.size() + " JOB ORDERS.");
				logger.info("Got " + jodList.size() + " JOB ORDERS.");
			}
			
		}
	
		return jodList;
	}

}

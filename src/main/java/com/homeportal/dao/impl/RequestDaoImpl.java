package com.homeportal.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.homeportal.bean.RequestBean;
import com.homeportal.dao.IRequestDAO;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.ServiceRequest;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;


/**
 * Class used to get general Request used for the Create and View Request functions
 * */
@Repository
public class RequestDaoImpl implements IRequestDAO
{
	private static Logger logger = Logger.getLogger(RequestDaoImpl.class);

	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
	
	private static final String GET_REQUESTOR_SQL = MessageBundle.getSqlProperty("get.requestor.sql");
	private static final String GET_REQUESTOR_SQL_SCALAR = MessageBundle.getSqlProperty("get.requestor.sql.scalar");
	
	
	@SuppressWarnings("unchecked")
	public List<RequestBean> getRequestors() {
		logger.debug("Getting requestors from db...");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_REQUESTOR_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_REQUESTOR_SQL);
				query.setResultTransformer(Transformers.aliasToBean(RequestBean.class));
			}
			
			
			/*Query query2 = session.createSQLQuery(GET_REQUESTOR_SQL)
                    .addScalar("id")
                    .addScalar("requestor")
                    .setResultTransformer(Transformers.aliasToBean(RequestBean.class));
         
			
			if (query2.list() != null)
			{
				logger.debug("Total # of Requestors: " + query2.list().size());
			}
		*/
		
		return query.list();
	}


	@Override
	public List<Object> getRequests(int jobOrderTypeID) {
		// TODO Auto-generated method stub
		return null;
	}


	/*@Override
	public boolean insertRequest(Request request) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();  // got error here Transaction not successfully started
		
		try
		{
			session.save(request);
			tx.commit();
			
			return true;
		}
		catch(HibernateException ex)
		{
			tx.rollback();
			logger.error("Error saving request to DB: " + ex.getMessage(), ex);
		}
		finally
		{
			session.close();
		}
		
		return false;
	}

*/
	@Override
	public boolean insertAmenityRequest(AmenitiesRequest amenityRequest) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean insertServiceRequest(ServiceRequest serviceRequest) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

}

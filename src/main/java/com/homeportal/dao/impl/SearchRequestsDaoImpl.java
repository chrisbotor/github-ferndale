/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;


import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.dao.ISearchRequestsDAO;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/**
 *
 * @author Racs
 */
@Repository
public class SearchRequestsDaoImpl implements ISearchRequestsDAO 
{
	private static Logger logger = Logger.getLogger(SearchRequestsDaoImpl.class);
	
	private static final String GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL = MessageBundle.getSqlProperty("get.all.amenities.requests.admin.sql");
	private static final String GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.amenities.requests.admin.sql.scalar");
	
	private static final String GET_ALL_SERVICE_REQUESTS_ADMIN_SQL = MessageBundle.getSqlProperty("get.all.service.requests.admin.sql");
	private static final String GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.service.requests.admin.sql.scalar");
	
	
	
	

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    
    
    /**
     * Gets ALL Amenity requests (similar to Admin home page query)
     * 
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsAll()
	{
    	System.out.println("Getting ALL amenity requests for the search page...");
    	logger.info("Getting ALL amenity requests for the search page...");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL);
				query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
			}
	
		return query.list();
	}
    
    
    
    
    /**
     * Gets Amenity requests by AMENITY only
     * 
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByAmenityOnly(int amenityId)
	{
    	System.out.println("Getting amenity requests by AMENITY only...");;
    	logger.info("Getting amenity requests by AMENITY only...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_AMENITY_ONLY_SQL = SQLUtil.adminSearchAmenityRequestsByAmenityOnlySQL(amenityId);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_AMENITY_ONLY_SQL);
				query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
			}
	
		return query.list();
    }
    
    
    
    /**
     * Gets Amenity requests by AMENITY and STATUS only
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByAmenityAndStatus(int amenityId, String status)
	{
    	System.out.println("Getting amenity requests by AMENITY and STATUS...");
    	logger.info("Getting amenity requests by AMENITY and STATUS...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_AMENITY_AND_STATUS_SQL = SQLUtil.adminSearchAmenityRequestsByAmenityAndStatusSQL(amenityId, status);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_AMENITY_AND_STATUS_SQL);
				query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
			}
	
		return query.list();
    	
	}
    
    
    /**
     * Gets Amenity requests by FROM DATE only
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByFromDateOnly(String fromDate)
	{
    	System.out.println("Getting amenity requests by FROM DATE only...");
    	logger.info("Getting amenity requests by FROM DATE only...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_FROM_DATE_ONLY_SQL = SQLUtil.adminSearchAmenityRequestsByFromDateOnlySQL(fromDate);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_FROM_DATE_ONLY_SQL);
				query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
			}
	
		return query.list();
    }
    
    
    /**
     * Gets Amenity requests by fromDate and toDate
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByFromDateAndToDate(String fromDate, String toDate)
	{
    	System.out.println("Getting amenity requests by fromDate and toDate...");
    	logger.info("Getting amenity requests by fromDate and toDate...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_FROM_DATE_AND_TO_DATE_SQL = SQLUtil.adminSearchAmenityRequestsByFromDateAndToDateSQL(fromDate, toDate);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_FROM_DATE_AND_TO_DATE_SQL);
				query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
			}
	
		return query.list();
    }
    
    
    
    /**
     * Gets Amenity requests by STATUS only
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByStatusOnly(String status)
	{
    	System.out.println("Getting amenity requests by STATUS only...");
    	logger.info("Getting amenity requests by STATUS only...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_STATUS_ONLY_SQL = SQLUtil.adminSearchAmenityRequestsByStatusOnlySQL(status);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_STATUS_ONLY_SQL);
				query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
			}
	
		return query.list();
    }
    
    

    /**
     * Gets Amenity requests by REQUESTOR only
     * 
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByRequestorOnly(int userId, int houseId)
	{
    	System.out.println("Getting amenity requests by REQUESTOR only...");
    	logger.info("Getting amenity requests by REQUESTOR only...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_ONLY_SQL = SQLUtil.adminSearchAmenityRequestsByRequestorOnlySQL(userId, houseId);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_ONLY_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
    	}
    	        
    	return query.list();
	}
    
    
    /**
     *  Gets Amenity requests by REQUESTOR and AMENITY
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByRequestorAndAmenity(int userId, int houseId, int amenityId)
	{
    	System.out.println("Getting amenity requests by REQUESTOR and AMENITY...");
    	logger.info("Getting amenity requests by REQUESTOR and AMENITY...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_AND_AMENITY_SQL = SQLUtil.adminSearchAmenityRequestsByRequestorAndAmenitySQL(userId, houseId, amenityId);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_AND_AMENITY_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
    	}
    	        
    	return query.list();
    
	}
    
    
    /**
     * Gets Amenity requests by REQUESTOR and STATUS
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByRequestorAndStatus(int userId, int houseId, String status)
	{
    	System.out.println("Getting amenity requests by REQUESTOR and STATUS...");
    	logger.info("Getting amenity requests by REQUESTOR and STATUS...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_AND_STATUS_SQL = SQLUtil.adminSearchAmenityRequestsByRequestorAndStatusSQL(userId, houseId, status);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_AND_STATUS_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
    	}
    	        
    	return query.list();
    }
    
    
    /**
     * Gets Amenity requests by REQUESTOR, AMENITY and STATUS
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<AmenityBean> searchAmenityRequestsByRequestorAmenityAndStatus(int userId, int houseId, int amenityId, String status)
	{
    	System.out.println("Getting amenity requests by REQUESTOR, AMENITY and STATUS...");
    	logger.info("Getting amenity requests by REQUESTOR, AMENITY and STATUS...");
    	
    	final String ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_AMENITY_AND_STATUS_SQL = 
    									SQLUtil.adminSearchAmenityRequestsByRequestorAmenityAndStatusSQL(userId, houseId, amenityId, status);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_AMENITY_REQUESTS_BY_REQUESTOR_AMENITY_AND_STATUS_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
    	}
    	        
    	return query.list();
    }
    
    
    //  #####################################################   SERVICE   #######################################################
    
    /**
     * Gets Service requests by SERVICE only
     * 
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByServiceOnly(int serviceId)
	{
    	System.out.println("Getting amenity requests by SERVICE only...");;
    	logger.info("Getting amenity requests by SERVICE only...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_SERVICE_ONLY_SQL = SQLUtil.adminSearchServiceRequestsByServiceOnlySQL(serviceId);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_SERVICE_ONLY_SQL);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
			}
	
		return query.list();
    }
    
    
    /**
     * Gets Service requests by FROM DATE only
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByFromDateOnly(String fromDate)
	{
    	System.out.println("Getting service requests by FROM DATE only...");
    	logger.info("Getting service requests by FROM DATE only...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_FROM_DATE_ONLY_SQL = SQLUtil.adminSearchServiceRequestsByFromDateOnlySQL(fromDate);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_FROM_DATE_ONLY_SQL);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
			}
	
		return query.list();
    }
    
    
    /**
     * Gets Service requests by fromDate and toDate
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByFromDateAndToDate(String fromDate, String toDate)
	{
    	System.out.println("Getting service requests by fromDate and toDate...");
    	logger.info("Getting service requests by fromDate and toDate...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_FROM_DATE_AND_TO_DATE_SQL = SQLUtil.adminSearchServiceRequestsByFromDateAndToDateSQL(fromDate, toDate);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_FROM_DATE_AND_TO_DATE_SQL);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
			}
	
		return query.list();
    }
    
    
    /**
     * Gets Service requests by SERVICE and STATUS only
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByServiceAndStatus(int serviceId, String status)
	{
    	System.out.println("Getting service requests by SERVICE and STATUS...");
    	logger.info("Getting service requests by SERVICE and STATUS...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_SERVICE_AND_STATUS_SQL = SQLUtil.adminSearchServiceRequestsByServiceAndStatusSQL(serviceId, status);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_SERVICE_AND_STATUS_SQL);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
			}
	
		return query.list();
    	
	}
    
    
    /**
     * Gets Service requests by STATUS only
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByStatusOnly(String status)
	{
    	System.out.println("Getting service requests by STATUS only...");
    	logger.info("Getting service requests by STATUS only...");
    
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_STATUS_ONLY_SQL = SQLUtil.adminSearchServiceRequestsByStatusOnlySQL(status);
    	
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_STATUS_ONLY_SQL);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
			}
	
		return query.list();
    }
    
    
    /**
     *  Gets Service requests by REQUESTOR and SERVICE
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByRequestorAndService(int userId, int houseId, int serviceId)
	{
    	System.out.println("Getting amenity requests by REQUESTOR and SERVICE...");
    	logger.info("Getting amenity requests by REQUESTOR and SERVICE...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_AND_SERVICE_SQL = SQLUtil.adminSearchServiceRequestsByRequestorAndServiceSQL(userId, houseId, serviceId);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_AND_SERVICE_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
    	}
    	        
    	return query.list();
    }
    
    
    /**
     * Gets Service requests by REQUESTOR and STATUS
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByRequestorAndStatus(int userId, int houseId, String status)
	{
    	System.out.println("Getting service requests by REQUESTOR and STATUS...");
    	logger.info("Getting service requests by REQUESTOR and STATUS...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_AND_STATUS_SQL = SQLUtil.adminSearchServiceRequestsByRequestorAndStatusSQL(userId, houseId, status);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_AND_STATUS_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
    	}
    	        
    	return query.list();
    }
    
    
    /**
     * Gets Service requests by REQUESTOR, SERVICE and STATUS
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByRequestorServiceAndStatus(int userId, int houseId, int serviceId, String status)	// RACS
	{
    	System.out.println("Getting service requests by REQUESTOR, SERVICE and STATUS...");
    	logger.info("Getting service requests by REQUESTOR, SERVICE and STATUS...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_SERVICE_AND_STATUS_SQL = 
    			SQLUtil.adminSearchServiceRequestsByRequestorServiceAndStatusSQL(userId, houseId, serviceId, status);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_SERVICE_AND_STATUS_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
    	}
    	        
    	return query.list();
    }
    
    
    /**
     * Gets ALL Service requests (similar to Admin home page query)
     * 
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsAll()
	{
    	System.out.println("Getting ALL service requests for the search page...");
    	logger.info("Getting ALL service requests for the search page...");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_ALL_SERVICE_REQUESTS_ADMIN_SQL);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
			}
	
		return query.list();
	}
    
    
    
    /**
     * Gets Service requests by REQUESTOR only
     * 
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<ServiceBean> searchServiceRequestsByRequestorOnly(int userId, int houseId)
	{
    	System.out.println("Getting service requests by REQUESTOR only...");
    	logger.info("Getting service requests by REQUESTOR only...");
    	
    	final String ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_ONLY_SQL = SQLUtil.adminSearchServiceRequestsByRequestorOnlySQL(userId, houseId);
		
    	List<String> scalarPropsList = null;
    	Session session = sessionFactory.getCurrentSession();
    	Query query = null;
    	
    	scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
    				
    	if (scalarPropsList != null)
    	{
    		query = SQLUtil.buildSQLQuery(session, scalarPropsList, ADMIN_SEARCH_SERVICE_REQUESTS_BY_REQUESTOR_ONLY_SQL);
    		query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
    	}
    	        
    	return query.list();
	}
    
    
    
    
}

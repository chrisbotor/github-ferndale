/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

/**
 *
 * @author PSP36488
 */
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.homeportal.bean.ServiceBean;
import com.homeportal.dao.IServiceRequestDAO;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.model.ServiceRequest;
import com.homeportal.util.HomePortalUtil;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;
import com.homeportal.util.StringUtil;

/**
 *
 * @author PSP36488
 */
@Repository
public class ServiceRequestDaoImpl implements IServiceRequestDAO 
{
	private static Logger logger = Logger.getLogger(ServiceRequestDaoImpl.class);
	
	private static final String GET_ALL_SERVICE_REQUESTS_ADMIN_SQL = MessageBundle.getSqlProperty("get.all.service.requests.admin.sql");
	private static final String GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.service.requests.admin.sql.scalar");
	
	// private static final String GET_DUPLICATE_SERVICE_REQUEST_SAME_USER_HQL = MessageBundle.getSqlProperty("get.duplicate.service.request.same.user.hql");
	// private static final String GET_DUPLICATE_SERVICE_REQUEST_DIFF_USER_HQL = MessageBundle.getSqlProperty("get.duplicate.service.request.different.user.hql");
	
	private static final String GET_SERVICE_REQUEST_BY_ID_HQL = MessageBundle.getSqlProperty("get.service.request.by.id.hql");
	private static final String GET_DUPLICATE_SERVICE_REQUEST_HQL = MessageBundle.getSqlProperty("get.duplicate.service.request.hql");
	
	
	
	/**
     * Creates a new Service request record in amenities_request table
     * 
     * @param ar Service request object
     * @return ar
     * */
	@Override
	public ServiceRequest saveServiceRequest(ServiceRequest sr) throws SQLException
	{ 
		System.out.println("Saving service request...");
		logger.info("Saving service request...");
		
		Session session = sessionFactory.getCurrentSession();
		
		try
        {
        	session.save(sr);
        }
        catch (Exception ex)
        {
        	System.out.println("Error encountered while saving Service request. " + ex.getMessage());
        	logger.error("Error encountered while saving Service request. " + ex.getMessage());
        	
        	throw new SQLException("Error encountered while saving Service request. " + ex.getMessage());
        }
		
		return sr;
	}
	
	
	 /**
     * Get service requests by STATUS (used by admin home in loading NEW service requests)
     *
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<ServiceBean> getServiceRequestsByStatus(List<String> statusList)
	{
		logger.debug("Getting service requests for the admin home page...");
		System.out.println("Getting service requests for the admin home page...");
		
		Session session = sessionFactory.getCurrentSession();
		
		String statuses = StringUtil.getStatusList(statusList);
	    String sql = SQLUtil.getServiceRequestsByStatus(statuses);
	        
	    Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, sql);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
			}
	
		return query.list();
	 }
	
	
	
	

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<ServiceBean> getService_Requests(int userId, int houseId) {
        Session session = sessionFactory.getCurrentSession();
        
        String sql = SQLUtil.getOwnerServiceRequestsSQL(userId, houseId);
        
       /* String sql = "select ar.id as id, a.DESCRIPTION as des, "
                + "ar.preferred_date as preferredDate, ar.confirmed_date as confirmedDate, "
                + "concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId,\n"
                + "ar.preferred_time as preferredTime,ar.quantity as quantity,"
                + "ar.STATUS,a.uom as uom from service_request ar join service a on a.ID = ar.SERVICE_ID where ar.USER_ID = " + id + " and ar.STATUS != 'Done' and ar.STATUS != 'Cancelled'  and ar.STATUS != 'Paid' order by ar.updated_At desc";
        */
        
        Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("des")
                .addScalar("preferredDate")
                .addScalar("confirmedDate")
                .addScalar("requestId")
                .addScalar("preferredTime")
                .addScalar("quantity")
                .addScalar("status")
                .addScalar("uom")
                .setResultTransformer(Transformers.aliasToBean(ServiceBean.class));

        return query.list();
    }
    
    
    
    /*
     * 	Gets all the service requests for the Admin page
     * */
    @SuppressWarnings("unchecked")
	public List<ServiceBean> getService_Requests() 
    {
    	logger.info("========================== Getting ALL service requests for the admin home page...  =================================");
		
    	List<ServiceBean> beanList = null;
    	
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_SERVICE_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_ALL_SERVICE_REQUESTS_ADMIN_SQL);
				query.setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
				
				beanList = (List<ServiceBean>) query.list();
			}
			
		return beanList;
    }

    
    
    
    public List<ServiceBean> getService_RequestsByAdmin(int userId, String status,String requestedDate, int serviceId) {
        
        System.out.println("went to getService_RequestsByAdmin list ......");
        
        Session session = sessionFactory.getCurrentSession();
        
         StringBuffer statusSb = new StringBuffer();
        
        if(status.equalsIgnoreCase("Select All")){
            statusSb.append("in ('Confirmed','Re-Scheduled','Re-Schedule','New')");
        }else{
            statusSb.append("='"+status+"'");
        }
        
        StringBuffer userSb = new StringBuffer();
        if(userId == 0){
            userSb.append("");
        }else{
            userSb.append("and ar.user_id = "+userId);
        }
        
        StringBuffer sbRequestedDate = new StringBuffer();
        if(requestedDate.length() == 0){
        
        }else{
           sbRequestedDate.append(" and ar.confirmed_date='"+requestedDate+"'");
        }
        
        StringBuffer sbAmenityId = new StringBuffer();
        if(serviceId == 0){
        
        }else{
            sbAmenityId.append(" and a.id="+serviceId);
        }
        
        String sql = "(select ar.id as id, Concat(h.addr_number, ' ', h.addr_street) as address,\n"
                + "concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId,\n"
                + "Concat(o.firstname, ' ' , o.lastname) as requestor,\n"
                + "CASE WHEN ar.misc_names != '' then Concat(a.DESCRIPTION, ' ' , Concat('-',' ',ar.misc_names)) else a.Description end  as des, \n"
                + "ar.preferred_date as preferredDate, ar.basic_cost,ar.additional_cost, ar.total_cost,\n"
                + "ar.confirmed_date as confirmedDate, ar.preferred_time as preferredTime, a.uom as uom, ar.STATUS,"
                + "a.reg_value as regularPrice, a.excess_value as excessPrice,ar.quantity,a.max_regular as maxRegular,ar.remarks as remarks,ar.payeeName as payeeName, ar.other_charges as otherCharges,ar.updated_At as updated\n"
                + "from service_request ar join service a on a.ID = ar.SERVICE_ID join\n"
                + "owner o on o.user_id = ar.user_id join house h on h.owner_id = o.id\n"
                + "where ar.STATUS "+statusSb.toString()+" "+ userSb.toString() +" "+sbRequestedDate+" "+sbAmenityId+")\n"
                + "UNION\n"
                + "(select ar.id as id, Concat(h.addr_number, ' ', h.addr_street) as address,\n"
                + "concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId,\n"
                + "Concat(l.firstname, ' ' , l.lastname) as requestor,\n"
                + "a.DESCRIPTION as des, ar.preferred_date as preferredDate, ar.basic_cost,ar.additional_cost, ar.total_cost,\n"
                + "ar.confirmed_date as confirmedDate, ar.preferred_time as preferredTime, a.uom as uom, ar.STATUS,"
                + "a.reg_value as regularPrice, a.excess_value as excessPrice,ar.quantity,a.max_regular as maxRegular,ar.remarks as remarks,ar.payeeName as payeeName, ar.other_charges as otherCharges,ar.updated_At as updated\n"
                + "from service_request ar join service a on a.ID = ar.SERVICE_ID join\n"
                + "leesee l on l.user_id = ar.user_id join house h on h.id = l.house_id\n"
                + "where ar.STATUS "+statusSb.toString()+" "+ userSb.toString() +" "+sbRequestedDate+" "+sbAmenityId+") order by updated desc";
        //String sql = "select ar.id as id, h.addr_number || ' ' || h.addr_street as address, a.DESCRIPTION as des, ar.preferred_date as preferredDate, ar.confirmed_date as confirmedDate, ar.preferred_time as preferredTime, ar.confirmed_time as confirmedTime,ar.STATUS, ar.VERIFIED from SERVICE_REQUEST ar join SERVICES a on a.ID = ar.SERVICE_ID join Owner o on o.user_id = ar.user_id join House h on h.owner_id = o.id where ar.STATUS in ('Confirmed','Re-Scheduled') order by CREATED desc";
        
        Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("address")
                .addScalar("requestId")
                .addScalar("des")
                .addScalar("preferredDate")
                .addScalar("confirmedDate")
                .addScalar("preferredTime")
                .addScalar("basic_cost")
                .addScalar("additional_cost")
                .addScalar("total_cost")
                .addScalar("uom")
                .addScalar("status")
                .addScalar("requestor")
                .addScalar("regularPrice")
                .addScalar("excessPrice")
                .addScalar("quantity")
                .addScalar("maxRegular")
                .addScalar("remarks")
                .addScalar("payeeName")
                .addScalar("otherCharges")
                .setResultTransformer(Transformers.aliasToBean(ServiceBean.class));
        
        return query.list();
    }

    public void deleteService_Requests(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

    public ServiceRequest updateService_Request(ServiceRequest sr) {
        Session session = sessionFactory.getCurrentSession();
        session.update(sr);
        return sr;
    }

    public ServiceRequest updateService_RequestViaPortal(ServiceRequest ar,  String uom) {
        try {
            Session session = sessionFactory.getCurrentSession();

            StringBuffer sb = new StringBuffer();
            sb.append("update service_request ar set ar.status = :status,\n");
            sb.append("ar.preferred_date=:preferred_date,\n");
            if(uom.equalsIgnoreCase("pc")){
            sb.append("ar.quantity=:quantity,\n");
            }
            if(uom.equalsIgnoreCase("hr")){
            sb.append("ar.preferred_time=:preferred_time,\n");
            }
            if(uom.equalsIgnoreCase("day")){
            sb.append("ar.quantity=:quantity,\n"); 
            sb.append("ar.preferred_time=:preferred_time,\n");
            }
            if(uom.equalsIgnoreCase("hph")){
            sb.append("ar.quantity=:quantity,\n"); 
            sb.append("ar.preferred_time=:preferred_time\n");
            }
            sb.append("ar.updated_At=Now() WHERE ar.id=:id");
            
            System.out.println("sql is --------------- " + sb.toString());
            Query query = session.createSQLQuery(sb.toString());
            
            query.setInteger("id", ar.getId());
            query.setString("status", ar.getStatus());
            query.setString("preferred_date", HomePortalUtil.getparsedDate(ar.getPreferredDate()));
            if(uom.equalsIgnoreCase("pc")){
            
            	// query.setInteger("quantity", ar.getQuantity());
            }
            if(uom.equalsIgnoreCase("hr")){
            query.setString("preferred_time", ar.getPreferredTime());
            }
            if(uom.equalsIgnoreCase("day")){
            query.setString("preferred_time", ar.getPreferredTime());
            query.setInteger("quantity", 1);
            }
            if(uom.equalsIgnoreCase("hph")){
            query.setString("preferred_time", ar.getPreferredTime());
            query.setInteger("quantity", ar.getQuantity());
            }
            //query.setDate("updatedAt", new Date());// test
            int rowCount = query.executeUpdate();


        } catch (Exception ex) {
            System.out.println("Error in update admin is : " + ex.getCause());
        }

        return ar;
    }

    public ServiceRequest updateService_RequestViaAdmin(ServiceRequest ar) {
        System.out.println("updateAmenities_RequestViaAdmin status is : " + ar.getStatus());
        
        System.out.println("TOTAL COST: " + ar.getTotalCost());
        logger.info("TOTAL COST: " + ar.getTotalCost());
        
        System.out.println("BASIC COST: " + ar.getBasicCost());
        logger.info("BASIC COST: " + ar.getBasicCost());
        
        
        
        try {
            Session session = sessionFactory.getCurrentSession();
            StringBuffer sb = new StringBuffer();
            if(!ar.getStatus().equalsIgnoreCase("")){
                sb.append("ar.status = :status , ");
            }
            String sql = "update service_request ar set "
                    + sb.toString()
                    + "ar.confirmed_date=:confirmed_date, "
                    + "ar.quantity=:quantity, "
                    + "ar.basic_cost=:basic_cost, "
                    + "ar.additional_cost=:additional_cost, "
                    + "ar.other_charges=:other_charges, "
                    + "ar.remarks=:remarks, "
                    + "ar.total_cost=:total_cost, "
                    + "ar.updated_At=Now() "
                    + "WHERE ar.id = :id";
            Query query = session.createSQLQuery(sql);
            query.setInteger("id", ar.getId());
            if(!ar.getStatus().equalsIgnoreCase("")){
             query.setString("status", ar.getStatus());
             }
            query.setString("confirmed_date", ar.getConfirmedDate());
            query.setInteger("quantity", ar.getQuantity());
            query.setDouble("basic_cost", ar.getBasicCost());
            query.setDouble("additional_cost", ar.getAdditionalCost());
            query.setDouble("other_charges", ar.getOtherCharges());
            query.setString("remarks", ar.getRemarks());
            query.setDouble("total_cost", ar.getTotalCost());
            //query.setDate("updated_At", new Date());
            int rowCount = query.executeUpdate();

        } catch (Exception ex) 
        {
        	logger.error("Error in updating service request. " + ex.getMessage());
        }
        
        return ar;
    }

    public Double updatePaidAmount(Double paidAmount, int requestId, Double amount) {
        Double unPaidAmount = 0.0;
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("update service_request ar set ar.paid_amount =:paidAmount,ar.updated_At=Now() ");
        if(amount == 0){
        sb.append(" , ar.status = 'Paid' ");
        }
         sb.append("where ar.id=:requestId");
        Query query = session.createSQLQuery(sb.toString());
        query.setDouble("paidAmount", paidAmount);
        query.setInteger("requestId", requestId);
        int rowCount = query.executeUpdate();
        return unPaidAmount;
    }

    public Double updatePaidAmountAdjustments(Double paidAmount, int requestId, Double amount) {
        Double unPaidAmount = 0.0;
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("update adjustments ar set ar.paid_amount =:paidAmount,ar.updated_At=Now() ");
        if(amount == 0){
        sb.append(" , ar.status = 'Paid' ");
        }
         sb.append("where ar.id=:requestId");
        Query query = session.createSQLQuery(sb.toString());
        query.setDouble("paidAmount", paidAmount);
        query.setInteger("requestId", requestId);
        int rowCount = query.executeUpdate();
        return unPaidAmount;
    }
    
    
    
    /**
     * Checks duplicate entry (same user requesting same request)
     * */
    public boolean checkIfExisting(ServiceRequest sr) throws SQLException
    {
    	System.out.println("Entered checkIfExisting service request...");
    	
    	Session session = sessionFactory.getCurrentSession();
    	ServiceRequest existingSR = null;
    	boolean existing = false;
    	
    	int serviceId = 0;
    	String preferredDate = null;
    	
    	Query query = null;
    	
    	if (sr != null)
    	{
    		serviceId = sr.getServiceId();
    		preferredDate = sr.getPreferredDate();
    		
    		System.out.println("===================== serviceId: " + serviceId);
    		System.out.println("===================== preferredDate: " + preferredDate);
    		
    		
    		try
    		{
    			query = session.createQuery(GET_DUPLICATE_SERVICE_REQUEST_HQL);
        		
        		query.setParameter("serviceId", serviceId);
    			query.setParameter("preferredDate", preferredDate);
    			
    			if (query.list() != null && query.list().size() > 0)
    			{
    				existingSR = (ServiceRequest) query.list().get(0);
    				
    				if (existingSR != null)
    	        	{
    	        		System.out.println("=========== existingSR ID: " + existingSR.getId());
    	        		existing = true;
    	        	}
    			}
    			else
    			{
    				System.out.println("=========== NO DUPLICATE! ");
    			}
    			
    		}
    		catch (Exception ex)
    		{
    			throw new SQLException("Error in validating the request " + ex.getMessage(), ex);
    		}	
        }
    	
    	return existing;
    }
    
    
    /**
     * Computes Service Fee
     * 
     * */
	@Override
	public Double computeServiceFee(int serviceId, int param2)
	{
		final String COMPUTE_SERVICE_FEE_SQL = SQLUtil.getComputeServiceFeeSQL(serviceId, param2);
    	
        Session session = sessionFactory.getCurrentSession();
        
        System.out.println("Computing for Service Fee...");
        logger.info("Computing for Service Fee...");
        
        Double fee = (Double) session.createSQLQuery(COMPUTE_SERVICE_FEE_SQL).uniqueResult();
        
        System.out.println("FEE: " + fee);
        logger.info("FEE: " + fee);
        
        return fee;
	}
	
	
	/**
	 * Get an amenity request by its ID 
	 * */
	@Override
	public ServiceRequest getServiceRequestById(int id) throws SQLException
	{
		logger.info("DAO getting a service request with ID: " + id);
    	System.out.println("DAO getting a service request with ID: " + id);
    	
    	ServiceRequest sr = null;
    	Session session = sessionFactory.getCurrentSession();
    	
		Query query = session.createQuery(GET_SERVICE_REQUEST_BY_ID_HQL);
		query.setParameter("id", id);
		
		if (query.list() != null && query.list().size() > 0)
		{
			sr = (ServiceRequest) query.list().get(0);
		}
		
		return sr;
	}
    
    
	/**
	 * Updates an Service request
	 * 
	 * */
	public ServiceRequest updateServiceRequest(ServiceRequest sr)
    {
	     Session session = sessionFactory.getCurrentSession();
	     session.update(sr);
	     return sr;
	}
	
    
    /*
    
     * Checks duplicate entry (different user requesting same request) 
     * 
    public boolean checkIfOverlappedSchedule(Service_Request sr) 
    {
    	System.out.println("Entered checkIfOverlappedSchedule service request...");
    	
    	Session session = sessionFactory.getCurrentSession();
    	Service_Request existingSR = null;
    	boolean overlappedSched = false;
    	
    	int serviceId = 0;
    	String preferredDate = null;
    	
    	Query query = null;
    	
    	if (sr != null)
    	{
    		query = session.createQuery(GET_DUPLICATE_SERVICE_REQUEST_DIFF_USER_HQL);
    		
    		serviceId = sr.getServiceId();
    		preferredDate = sr.getPreferredDate();
    		
			query.setParameter("serviceId", serviceId);
			query.setParameter("preferredDate", preferredDate);
			
			existingSR = (Service_Request) query.list().get(0);
			
			if (existingSR != null)
        	{
        		System.out.println("================ overlappedSched: " + overlappedSched + ", existingSR ID: " + existingSR.getId());
        		overlappedSched = true;
        	}
        }
    	
    	return overlappedSched;
    }*/

}

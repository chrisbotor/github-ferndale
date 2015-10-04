/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.dao.IAmenitiesRequestDAO;
import com.homeportal.model.Adjustment;
import com.homeportal.model.AmenitiesRequest;
import com.homeportal.util.AmenitiesRequestUtil;
import com.homeportal.util.HomePortalUtil;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;
import com.homeportal.util.StringUtil;

/**
 *
 * @author Racs
 */
@Repository
public class AmenitiesRequestDaoImpl implements IAmenitiesRequestDAO 
{
	private static Logger logger = Logger.getLogger(AmenitiesRequestDaoImpl.class);
	
	private static final String GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL = MessageBundle.getSqlProperty("get.all.amenities.requests.admin.sql");
	private static final String GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.amenities.requests.admin.sql.scalar");
	private static final String GET_AMENITY_REQUEST_BY_ID_HQL = MessageBundle.getSqlProperty("get.amenity.request.by.id.hql");
	
	public static void main (String[] args)
	{
		AmenitiesRequestDaoImpl dao = new AmenitiesRequestDaoImpl();
		dao.computeAmenityFee(16, 10);
		
	}
	
	
	
	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

	
	 /**
     * Get amenity requests by STATUS (used by admin home in loading NEW amenity requests)
     *
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<AmenityBean> getAmenityRequestsByStatus(List<String> statusList)
	{
		logger.debug("Getting amenity requests for the admin home page...");
		System.out.println("Getting amenity requests for the admin home page...");
		
		Session session = sessionFactory.getCurrentSession();
		
		String statuses = StringUtil.getStatusList(statusList);
	    String sql = SQLUtil.getAmenityRequestsByStatus(statuses);
	        
	    Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_AMENITIES_REQUESTS_ADMIN_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, sql);
				query.setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
			}
	
		return query.list();
	 }
	
	
	/**
	 * Get an amenity request by its ID 
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public AmenitiesRequest getAmenityRequestById(int id) throws SQLException
	{
		logger.info("DAO getting an amenity request with ID: " + id);
    	System.out.println("DAO getting an amenity request with ID: " + id);
    	
    	AmenitiesRequest ar = null;
    	Session session = sessionFactory.getCurrentSession();
    	
		Query query = session.createQuery(GET_AMENITY_REQUEST_BY_ID_HQL);
		query.setParameter("id", id);
		
		if (query.list() != null && query.list().size() > 0)
		{
			ar = (AmenitiesRequest) query.list().get(0);
		}
		
		return ar;
	}
	
	
	
	
    @SuppressWarnings("unchecked")
	public List<AmenityBean> getAmenitiesRequests(int userId, int houseId) {
        Session session = sessionFactory.getCurrentSession();
        
        String sql = SQLUtil.getOwnerAmenityRequestsSQL(userId, houseId);
        
        Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("des")
                .addScalar("requestedDate")
                .addScalar("requestId")
                .addScalar("startTime")
                .addScalar("endTime")
                .addScalar("quantity")
                .addScalar("status")
                .addScalar("uom")
                .setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
        return query.list();
    }
    
    
    
    
    /**
     * Gets all the amenities requests to be displayed in the ADMIN page
     * */
    @SuppressWarnings("unchecked")
	public List<AmenityBean> getAmenities_Requests() 
    {
    	logger.debug("Getting ALL amenities requests for the admin home page...");
		
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

    
    
    public List<AmenityBean> getAmenities_RequestsByAdmin(int userId, String status, String requestedDate, int amenityId) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println("went to getAmenities_Requests list ......");
        
        StringBuffer statusSb = new StringBuffer();
        
        if(status.equalsIgnoreCase("Select All")){
            statusSb.append("in ('Reserved','Booked','New','Change Request')");
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
           sbRequestedDate.append(" and ar.requested_date='"+requestedDate+"'");
        }
        
        StringBuffer sbAmenityId = new StringBuffer();
        if(amenityId == 0){
        
        }else{
            sbAmenityId.append(" and a.id="+amenityId);
        }
        
        
        String sql = "(select ar.id as id,a.id as amenityId, h.id as houseId,\n"
                + "concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId,\n"
                + "Concat(h.addr_number, ' ', h.addr_street) as address,\n"
                + "Concat(o.firstname, ' ' , o.lastname) as requestor,\n"
                + "a.DESCRIPTION as des, ar.requested_date as requestedDate,\n"
                + "ar.START_TIME as startTime, ar.END_TIME as endTime, ar.basic_cost as basic_cost,\n"
                + "ar.additional_cost as additional_cost, ar.total_cost as total_cost, a.uom as uom, ar.STATUS,\n"
                + "a.reg_value as regularPrice, a.excess_value as excessPrice,ar.quantity,a.max_regular as maxRegular, ar.remarks as remarks, ar.other_charges as otherCharges, ar.updated_At as updated\n"
                + "from amenities_request ar join amenity a on a.ID = ar.AMENITY_ID\n"
                + "join owner o on o.user_id = ar.user_id join house h on h.owner_id = o.id\n"
                + "where ar.STATUS "+statusSb.toString()+" "+ userSb.toString() +" "+sbRequestedDate+" "+sbAmenityId+")\n"
                + "UNION\n"
                + "(select ar.id as id,a.id as amenityId, h.id as houseId,\n"
                + "concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId,\n"
                + "Concat(h.addr_number, ' ', h.addr_street) as address,\n"
                + "Concat(l.firstname, ' ' , l.lastname) as requestor,\n"
                + "a.DESCRIPTION as des, ar.requested_date as requestedDate,ar.START_TIME as startTime, ar.END_TIME as endTime,\n"
                + "ar.basic_cost as basic_cost,ar.additional_cost as additional_cost, ar.total_cost as total_cost, \n"
                + "a.uom as uom, ar.STATUS, a.reg_value as regularPrice, a.excess_value as excessPrice,ar.quantity,a.max_regular as maxRegular,ar.remarks as remarks, ar.other_charges as otherCharges, ar.updated_At as updated\n"
                + "from amenities_request ar join amenity a on a.ID = ar.AMENITY_ID join \n"
                + "leesee l on l.user_id = ar.user_id join house h on h.id = l.house_id\n"
                + "where ar.STATUS "+statusSb.toString()+" "+ userSb.toString() +" "+sbRequestedDate+" "+ sbAmenityId+") order by updated desc";
        //String sql = "select ar.id as id, h.addr_number || ' ' || h.addr_street as address, a.DESCRIPTION as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, ar.END_TIME as endTime, ar.STATUS, ar.VERIFIED from AMENITIES_REQUEST ar join Amenity a on a.ID = ar.AMENITY_ID join Owner o on o.user_id = ar.user_id join House h on h.owner_id = o.id where ar.STATUS in ('Reserved','Booked') order by CREATED desc";
        System.out.println("get amenity request sql is : " + sql);
        Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("amenityId")
                .addScalar("houseId")
                .addScalar("requestId")
                .addScalar("address")
                .addScalar("des")
                .addScalar("requestedDate")
                .addScalar("startTime")
                .addScalar("endTime")
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
                .addScalar("otherCharges")
                .setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
        return query.list();
    }

    public void deleteAmenities_Requests(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
    /**
     * Creates a new Amenity request record in amenities_request table
     * 
     * @param ar Amenity request object
     * @return ar
     * */
	@Override
	public AmenitiesRequest saveAmenitiesRequest(AmenitiesRequest ar) throws SQLException
	{ 
		System.out.println("Saving amenity request...");
		logger.info("Saving amenity request...");
		
		Session session = sessionFactory.getCurrentSession();
		
		try
        {
        	session.save(ar);
        }
        catch (Exception ex)
        {
        	System.out.println("Error encountered while saving Amenity request. " + ex.getMessage());
        	logger.error("Error encountered while saving Amenity request. " + ex.getMessage());
        	
        	throw new SQLException("Error encountered while saving Amenity request. " + ex.getMessage());
        }
		
		return ar;
	}
    
 
	/**
	 * Updates an Amenity request
	 * 
	 * */
	public AmenitiesRequest updateAmenityRequest(AmenitiesRequest ar)
    {
	     Session session = sessionFactory.getCurrentSession();
	     session.update(ar);
	     return ar;
	}

	
	
	/**
	 * Updates an Amenity request (OLD VERSION)
	 * 
	 * */
    public AmenitiesRequest updateAmenities_Request(AmenitiesRequest ar) {
        Session session = sessionFactory.getCurrentSession();
        session.update(ar);
        return ar;
    }

    
    
    public AmenitiesRequest updateAmenities_RequestViaPortal(AmenitiesRequest ar, String uom) {
        try {
            Session session = sessionFactory.getCurrentSession();
            
            StringBuffer sb = new StringBuffer();
            
            String formattedStartTime = AmenitiesRequestUtil.formatTime(ar.getStartTime());
            String formattedEndTime = AmenitiesRequestUtil.formatTime(ar.getEndTime());
			
            sb.append("update amenities_request ar set ar.status = :status ,ar.requested_date=:requestedDate,\n");
            if(uom.equalsIgnoreCase("pc")){
            sb.append("ar.quantity=:quantity,\n");
            }
            if(uom.equalsIgnoreCase("hr")){
            sb.append("ar.start_time=:startTime,ar.end_time=:endTime,\n");
            }
            if(uom.equalsIgnoreCase("day")){
            sb.append("ar.quantity=:quantity,\n"); 
            sb.append("ar.start_time=:startTime,ar.end_time=:endTime,\n");
            }
            if(uom.equalsIgnoreCase("hph")){
            sb.append("ar.quantity=:quantity,\n"); 
            sb.append("ar.start_time=:startTime,ar.end_time=:endTime,\n");
            }
            sb.append("ar.updated_At=Now() WHERE ar.id = :id");
           
            System.out.println("sql is : " + sb);
            
            Query query = session.createSQLQuery(sb.toString());
            query.setInteger("id", ar.getId());
            query.setString("status", ar.getStatus());
            query.setString("requestedDate", HomePortalUtil.getparsedDate(ar.getRequestedDate()));
            if(uom.equalsIgnoreCase("pc")){
            query.setInteger("quantity", ar.getQuantity());
            }
            
            if(uom.equalsIgnoreCase("hr")){
            	query.setString("startTime", formattedStartTime);
            	query.setString("endTime", formattedEndTime);
            }
            
            if(uom.equalsIgnoreCase("day")){
            	query.setString("startTime", formattedStartTime);
            	query.setString("endTime", formattedEndTime);
            	query.setInteger("quantity", 1);
            }
            
            if(uom.equalsIgnoreCase("hph")){
            	query.setString("startTime", formattedStartTime);
            	query.setString("endTime", formattedEndTime);
            	query.setInteger("quantity", ar.getQuantity());
            }
            
            //query.setDate("updated_At", new Date());
            int rowCount = query.executeUpdate();

        } 
        catch (Exception ex) 
        {
        	logger.error("Error in updating amenity request via admin portal." + ex.getMessage());
        }
        return ar;
    }

    public AmenitiesRequest updateAmenities_RequestViaAdmin(AmenitiesRequest ar) {
        System.out.println("updateAmenities_RequestViaAdmin status is : " + ar.getStatus());
        
        String formattedStartTime = null;
        String formattedEndTime = null;
        
        try {
            Session session = sessionFactory.getCurrentSession();
            StringBuffer sb = new StringBuffer();
            if(!ar.getStatus().equalsIgnoreCase("")){
                sb.append("ar.status = :status , ");
            }
            String sql = "update amenities_request ar set "
                    + sb.toString()
                    + "ar.quantity = :quantity, "
                    + "ar.start_time=:startTime,"
                    + "ar.end_time=:endTime, "
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
            
            	 
            formattedStartTime = AmenitiesRequestUtil.formatTime(ar.getStartTime());
            formattedEndTime = AmenitiesRequestUtil.formatTime(ar.getEndTime());
			
            query.setInteger("quantity", ar.getQuantity());
            query.setString("startTime", formattedStartTime);
            query.setString("endTime", formattedEndTime);
            query.setDouble("basic_cost", ar.getBasicCost());
            query.setDouble("additional_cost", ar.getAdditionalCost());
            query.setDouble("other_charges", ar.getOtherCharges());
            query.setString("remarks", ar.getRemarks());
            query.setDouble("total_cost", ar.getTotalCost());
            //query.setDate("updated_At", new Date());
            int rowCount = query.executeUpdate();

        } 
        catch (Exception ex) 
        {
            // Logger.getLogger(AmenitiesRequestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        	logger.error("Error in updating amenity request." + ex.getMessage());
        }
        
        return ar;
    }

    public Double updatePaidAmount(Double paidAmount, int requestId, Double amount) {
        Double unPaidAmount = 0.0;
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("update amenities_request ar set ar.paid_amount =:paidAmount,ar.updated_At=Now() ");
        if(amount == 0){
        sb.append(" , ar.status = 'Paid' ");
        }
        sb.append("where ar.id=:requestId");
        //String sql = "update amenities_request ar set ar.paid_amount =:paidAmount,ar.updated_At=Now()  where ar.id=:requestId";
        Query query = session.createSQLQuery(sb.toString());
        query.setDouble("paidAmount", paidAmount);
        query.setInteger("requestId", requestId);
        int rowCount = query.executeUpdate();
        return unPaidAmount;
    }
    
    /*
     * Checks duplicate entry (same user requesting same request) 
     * */
    public boolean checkIfExisting(AmenitiesRequest ar) 
    {
    	System.out.println("Entered checkIfExisting...");
    	
    	Session session = sessionFactory.getCurrentSession();
    	boolean existing = false;
    	
    	int userId = 0;
    	int amenityId = 0;
    	String requestedDate = null;
    	String startTime = null;
    	String endTime = null;
    	String formattedStartTime = null;
    	String formattedEndTime = null;
    	String sql = null;
    
    	Query query = null;
    	
    	if (ar != null)
    	{
    		startTime =	AmenitiesRequestUtil.formatTime(ar.getStartTime());
			endTime = AmenitiesRequestUtil.formatTime(ar.getEndTime());
			
			ar.setStartTime(startTime);
			ar.setEndTime(endTime);
    		
    		userId = ar.getUserId();
    		amenityId = ar.getAmenityId();
        	requestedDate = ar.getRequestedDate();
        	formattedStartTime = ar.getStartTime();
        	formattedEndTime = ar.getEndTime();
        	
        	sql = SQLUtil.checkIfExistingAmenityRequestSQL(userId, amenityId, requestedDate, formattedStartTime, formattedEndTime);
        	
        	query = session.createSQLQuery(sql);
        	
        	
        	if (query.list() != null && query.list().get(0) != null)
        	{
        		BigInteger result = (BigInteger) query.list().get(0);
        		
        		
        		if (result.signum() == 1) {
        		
        			existing = true;
        		}
        	}
        	
        }
    	
    	System.out.println("FINAL existing: " + existing);
    	
    	return existing;
    }
    
   
    /*
     * Checks duplicate entry (different user requesting same request) 
     * */
    public boolean checkIfOverlappedSchedule(AmenitiesRequest ar) 
    {
    	System.out.println("Entered checkIfOverlappedSchedule...");
    	
    	Session session = sessionFactory.getCurrentSession();
    	boolean overlappedSched = false;
    	
    	int amenityId = 0;
    	String requestedDate = null;
    	String startTime = null;
    	String endTime = null;
    	String formattedStartTime = null;
    	String formattedEndTime = null;
    	Query query = null;
    	
    	
    	if (ar != null)
    	{
    		StringBuffer sb = new StringBuffer();
    		
    		System.out.println("BEFORE FORMATTING startTime: " + startTime);
    		System.out.println("BEFORE FORMATTING endTime: " + endTime);
    		
    		startTime =	AmenitiesRequestUtil.formatTime(ar.getStartTime());
			endTime = AmenitiesRequestUtil.formatTime(ar.getEndTime());
			
			ar.setStartTime(startTime);
			ar.setEndTime(endTime);
    		
    		amenityId = ar.getAmenityId();
        	requestedDate = ar.getRequestedDate();
        	formattedStartTime = ar.getStartTime();
        	formattedEndTime = ar.getEndTime();
        	
        	System.out.println("RACS formattedStartTime: " + formattedStartTime);
        	System.out.println("RACS formattedEndTime: " + formattedEndTime);
        	

        	sb.append("select count(*) from amenities_request where amenity_id = '");
        	sb.append(amenityId);
        	sb.append("' and requested_date = '");
        	sb.append(requestedDate);
        	sb.append("' and ((start_time between '");
        	sb.append(formattedStartTime);
        	sb.append("' and '");
        	sb.append(formattedEndTime);
        	sb.append("') or (end_time between '");
        	sb.append(formattedStartTime);
        	sb.append("' and '");
        	sb.append(formattedEndTime);
        	sb.append("')) and (status = 'Booked' or status = 'Done')");
        	
        	
        	System.out.println("\nSQL Query: " + sb.toString());
        	
        	query = session.createSQLQuery(sb.toString());
        	
        	
        	//System.out.println("RACS: " + result);
        	
        	if (query.list() != null && query.list().get(0) != null)
        	{
        		BigInteger result = (BigInteger) query.list().get(0);
        		
        		
        		if (result.signum() == 1) {
        		
        			overlappedSched = true;
        		}
        	}
        	
        }
    	
    	System.out.println("FINAL overlappedSched: " + overlappedSched);
    	
    	return overlappedSched;
   }





    /**
     * Computes Amenity Fee
     * 
     * */
	@Override
	public Double computeAmenityFee(int amenityId, int param2)
	{
		final String COMPUTE_AMENITY_FEE_SQL = SQLUtil.getComputeAmenityFeeSQL(amenityId, param2);
    	
        Session session = sessionFactory.getCurrentSession();
        
        System.out.println("Computing for Amenity Fee...");
        logger.info("Computing for Amenity Fee...");
        
        Double fee = (Double) session.createSQLQuery(COMPUTE_AMENITY_FEE_SQL).uniqueResult();
        
        System.out.println("FEE: " + fee);
        logger.info("FEE: " + fee);
        
        return fee;
	}
	
	
	/**
	 * Checks whether there is an overlapping date and time when creating an Amenity or Service request
	 * 
	 * */
	public boolean validateDateAndTimeOverlap(int amenityId, String date, String startTime, String endTime)
	{
		final String CHECK_AMENITY_REQUEST_SQL = SQLUtil.getCheckAmenityRequestSQL(amenityId, date, startTime, endTime);
		Session session = sessionFactory.getCurrentSession();
        
        System.out.println("Checking for Amenity Request Date and Time Overlap...");
        logger.info("Checking for Amenity Request Date and Time Overlap...");
        
        boolean isValid = false;
        
        int withOverlap = (Integer) session.createSQLQuery(CHECK_AMENITY_REQUEST_SQL).uniqueResult();
        
        System.out.println("ECY WITH OVERLAP? " + withOverlap);
        logger.info("ECY WITH OVERLAP? " + withOverlap);
        
        if (withOverlap == 1)
        {
        	isValid = true;
        }
		
		return isValid;
	}


	@Override
	public boolean checkAmenityRequest(int amenityId, String date,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return false;
	}
    
}

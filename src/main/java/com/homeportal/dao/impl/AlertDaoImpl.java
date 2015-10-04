/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.bean.AmenityBean;
import com.homeportal.bean.EmployeeBean;
import com.homeportal.bean.ServiceBean;
import com.homeportal.bean.VehicleBean;
import com.homeportal.dao.IAlertDAO;
import com.homeportal.model.Occupant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
*
* @author Racs
*/
@Repository
public class AlertDaoImpl implements IAlertDAO{

	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<AmenityBean> getNewAmenityRequests() {
		 Session session = sessionFactory.getCurrentSession();
        
        try{

        }catch(Exception e){
            System.out.println("error is :" + e.getMessage());
        }
        String sql = "select distinct Concat(h.addr_number, ' ', h.addr_street) as address from amenities_request ar join amenity a on a.ID = ar.amenity_id join owner o on o.user_id = ar.user_id join house h on h.owner_id = o.id where ar.status in ('New','Change Request','Cancel') order by ar.created_at desc";
        //String sql = "select ar.id as id, h.addr_number || ' ' || h.addr_street as address, a.DESCRIPTION as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, ar.END_TIME as endTime, ar.STATUS, ar.VERIFIED from AMENITIES_REQUEST ar join Amenity a on a.ID = ar.AMENITY_ID join Owner o on o.user_id = ar.user_id join House h on h.owner_id = o.id where ar.STATUS in ('New','Change Request','Cancel') order by CREATED desc";
        Query query = session.createSQLQuery(sql)
               //.addScalar("id")
               .addScalar("address")
               //.addScalar("des")
               //.addScalar("requestedDate")
               //.addScalar("startTime")
               //.addScalar("endTime")
               //.addScalar("status")
               //.addScalar("verified")
               //.addScalar("quantity")
               .setResultTransformer(Transformers.aliasToBean(AmenityBean.class));
                return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ServiceBean> getNewServiceRequests() {
		Session session = sessionFactory.getCurrentSession();

        String sql = "select distinct Concat(h.addr_number, ' ', h.addr_street) as address from service_request ar join service a on a.id = ar.service_id join owner o on o.user_id = ar.user_id join house h on h.owner_id = o.id where ar.status in ('New','Re-Schedule','Cancel') order by ar.created_at desc";
        //String sql = "select ar.id as id, h.addr_number || ' ' || h.addr_street as address, a.DESCRIPTION as des, ar.preferred_date as preferredDate, ar.confirmed_date as confirmedDate, ar.preferred_time as preferredTime, ar.confirmed_time as confirmedTime,ar.STATUS, ar.VERIFIED from SERVICE_REQUEST ar join SERVICES a on a.ID = ar.SERVICE_ID join Owner o on o.user_id = ar.user_id join House h on h.owner_id = o.id where ar.STATUS in ('New','Re-Schedule','Cancel') order by CREATED desc";
        Query query = session.createSQLQuery(sql)
               //.addScalar("id")
               .addScalar("address")
               //.addScalar("des")
               //.addScalar("preferredDate")
               //.addScalar("confirmedDate")
               //.addScalar("preferredTime")
               //.addScalar("confirmedTime")
               //.addScalar("status")
               //.addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(ServiceBean.class));

                return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<EmployeeBean> getNewEmployees() {
		// Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        String sql = "select distinct Concat(h.addr_number, ' ', h.addr_street) as address "
                //+ ",e.firstname, e.middlename, e.lastname, e.birthdate, e.civil_status as civilStatus"
                //+ ", e.position, e.inhouse, e.startdate, e.enddate, e.status, e.verified "
                + "from employee e join "
                + "owner o on o.user_id = e.user_id join house h on h.owner_id = o.id where e.status in ('New','Update Profile','Renew','End of Contract','Cancel')";

        String sql2 = "select e.id,h.addr_number || ' ' || h.addr_street as address"
                + ",e.firstname, e.middlename, e.lastname, e.birthdate, e.civil_status as civilStatus"
                + ", e.position, e.inhouse, e.startdate, e.enddate, e.status, e.verified from employee e join "
                + "owner o on o.user_id = e.user_id join house h on h.owner_id = o.id where e.status in ('New','Update Profile','Renew','End of Contract','Cancel')";

        // h.addr_number || ' ' || h.addr_street as address
        Query query = session.createSQLQuery(sql)
               //.addScalar("id")
               .addScalar("address")
               //.addScalar("firstname")
               //.addScalar("middlename")
               //.addScalar("lastname")
               //.addScalar("birthdate")
               //.addScalar("civilStatus")
               //.addScalar("position")
               //.addScalar("inhouse")
               //.addScalar("startdate")
               //.addScalar("enddate")
               //.addScalar("status")
               //.addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(EmployeeBean.class));
                return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Occupant> getNewOccupants() {
		List<Occupant> oList = new ArrayList<Occupant>();
		
		Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery("from occupant o where o.status='NEW'");
	    oList = query.list();
	    
		System.out.println("New Occupants: " + query.list().size());
		return oList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<VehicleBean> getNewVehicles() {
		// Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
            String sql = "select distinct Concat(h.addr_number, ' ', h.addr_street) as address"
                    //+ " v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified "
                    + " from vehicle v join owner o on o.user_id = v.user_Id join house h on h.owner_id = o.id where v.status in ('New','ReIssue','Renew','Sold','Cancel')";
          //String sql = "select v.id, h.addr_number || ' ' || h.addr_street as address, v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v join owner o on o.user_id = v.user_Id join House h on h.owner_id = o.id where v.status in ('New','ReIssue','Renew','Sold','Cancel')";
        // h.addr_number || ' ' || h.addr_street as address
        Query query = session.createSQLQuery(sql)
               //.addScalar("id")
               .addScalar("address")
               //.addScalar("model")
               //.addScalar("color")
               //.addScalar("plateNumber")
               //.addScalar("sticker")
               //.addScalar("status")
               //.addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(VehicleBean.class));
                return query.list();
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.bean.VehicleBean;
import com.homeportal.dao.IVehicleDAO;
import com.homeportal.model.Vehicle;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Peter
 */
@Repository
public class VehicleDaoImpl implements IVehicleDAO{
    
    @Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
    
    public List<VehicleBean> getVehicles() {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("select v.id, Concat(h.addr_number, ' ', h.addr_street) as address,Concat(o.firstname, ' ' , o.lastname) as requestor, v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v join owner o on o.user_id = v.user_Id join house h on h.owner_id = o.id where ");
        sb.append("v.status");
        sb.append(" in ('For Pick Up','On Process','New','ReIssue','Renew','Sold')");
        sb.append(" UNION ");
        sb.append("select v.id, Concat(h.addr_number, ' ', h.addr_street) as address,Concat(o.firstname, ' ' , o.lastname) as requestor, v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v join leesee o on o.user_id = v.user_Id join house h on h.owner_id = o.id where ");
        sb.append("v.status");
        sb.append(" in ('For Pick Up','On Process','New','ReIssue','Renew','Sold')");
       
          Query query = session.createSQLQuery(sb.toString())
               .addScalar("id")
               .addScalar("address")
               .addScalar("requestor")
               .addScalar("model")
               .addScalar("color")
               .addScalar("plateNumber")
               .addScalar("sticker")
               .addScalar("status")
               .addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(VehicleBean.class));
	return query.list();
    }

    public List<VehicleBean> getVehicles(int userId,String status) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("select v.id, Concat(h.addr_number, ' ', h.addr_street) as address,Concat(o.firstname, ' ' , o.lastname) as requestor, v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v join owner o on o.user_id = v.user_Id join house h on h.owner_id = o.id where ");
        sb.append("v.status");
        if(status.equalsIgnoreCase("Select All")){
            sb.append(" in ('For Pick Up','On Process','New','ReIssue','Renew','Sold')");
        }else{
            sb.append("='"+status+"'");
        }
        if(userId == 0){
            sb.append("");
        }else{
            sb.append("and v.user_id = "+userId);
        }
        sb.append(" UNION ");
        sb.append("select v.id, Concat(h.addr_number, ' ', h.addr_street) as address,Concat(o.firstname, ' ' , o.lastname) as requestor, v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v join leesee o on o.user_id = v.user_Id join house h on h.owner_id = o.id where ");
        sb.append("v.status");
        if(status.equalsIgnoreCase("Select All")){
            sb.append(" in ('For Pick Up','On Process','New','ReIssue','Renew','Sold')");
        }else{
            sb.append("='"+status+"'");
        }
        if(userId == 0){
            sb.append("");
        }else{
            sb.append("and v.user_id = "+userId);
        }
        //String sql = "select v.id, Concat(h.addr_number, ' ', h.addr_street) as address,Concat(o.firstname, ' ' , o.lastname) as requestor, v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v join owner o on o.user_id = v.user_Id join house h on h.owner_id = o.id where v.status in ('On Process','New','ReIssue','Renew','Sold','Cancel')";
        //String sql = "select v.id, h.addr_number || ' ' || h.addr_street as address, v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v join owner o on o.user_id = v.user_Id join House h on h.owner_id = o.id where v.status in ('On Process','New','ReIssue','Renew','Sold','Cancel')";
        // h.addr_number || ' ' || h.addr_street as address
        System.out.println("get vehicle is ::" + sb.toString());
        Query query = session.createSQLQuery(sb.toString())
               .addScalar("id")
               .addScalar("address")
               .addScalar("requestor")
               .addScalar("model")
               .addScalar("color")
               .addScalar("plateNumber")
               .addScalar("sticker")
               .addScalar("status")
               .addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(VehicleBean.class));
	return query.list();
    }
    
    public List<Vehicle> getVehicles(int userId, int houseId) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        String sql = "select v.id,v.model, v.color,v.plate_number as plateNumber,v.sticker, v.status,v.verified from vehicle v where v.user_Id ="+userId;
        Query query = session.createSQLQuery(sql)
               .addScalar("id")
               .addScalar("model")
               .addScalar("color")
               .addScalar("plateNumber")
               .addScalar("sticker")
               .addScalar("status")
               .addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(Vehicle.class));
        System.out.println("size is : " + query.list().size());
	return query.list();
    }

    public void deleteVehicles(int id) {
        Session session = sessionFactory.getCurrentSession();
        Vehicle v = (Vehicle) session.get(Vehicle.class, id);
        session.delete(v);
    }

    public Vehicle saveVehicles(Vehicle vehicle) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println("went to saving Vehicle");
        session.save(vehicle);
        return vehicle;
    }

    public Vehicle updateVehicle(Vehicle v) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "update vehicle v set v.sticker=:sticker, v.status =:status, v.verified=:verified "
                 + "where v.id =:id";
            
            Query query = session.createSQLQuery(sql);
            query.setInteger("id", v.getId());
            query.setString("sticker", v.getSticker());
            query.setString("status", v.getStatus());
            query.setString("verified", v.getVerified());
            int rowCount = query.executeUpdate();
        return v;
    }
    
    public Vehicle updateVehicleViaPortal(Vehicle v) {
         Session session = sessionFactory.getCurrentSession();
         String sql = "update vehicle v set v.status =:status "
                 + "where v.id =:id";
            
            Query query = session.createSQLQuery(sql);
            query.setInteger("id", v.getId());
            query.setString("status", v.getStatus());
            int rowCount = query.executeUpdate();
            return v;
    }
    
}

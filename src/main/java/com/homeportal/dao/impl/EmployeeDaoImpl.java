/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.bean.EmployeeBean;
import com.homeportal.dao.IEmployee;
import com.homeportal.model.Employee;
import com.homeportal.util.HomePortalUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class EmployeeDaoImpl implements IEmployee{

    @Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
    
    public List<EmployeeBean> getEmployees() {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
       
        StringBuffer sb = new StringBuffer();
        sb.append("select e.id,Concat(h.addr_number, ' ', h.addr_street) as address ,e.firstname, "
                + "Concat(o.firstname, ' ' , o.lastname) as requestor, "
                + "e.middlename, e.lastname, e.birthdate, e.civil_status as civilStatus, "
                + "e.position, e.inhouse, e.startdate, e.enddate, e.status, e.verified "
                + "from employee e join owner o on o.user_id = e.user_id join house h on h.owner_id = o.id where e.STATUS ");

        sb.append(" in ('On Process','Decline','New','Update Profile','Renew','End of Contract')");
        sb.append(" UNION ");
        sb.append("select e.id,Concat(h.addr_number, ' ', h.addr_street) as address ,e.firstname, "
                + "Concat(o.firstname, ' ' , o.lastname) as requestor, "
                + "e.middlename, e.lastname, e.birthdate, e.civil_status as civilStatus, "
                + "e.position, e.inhouse, e.startdate, e.enddate, e.status, e.verified "
                + "from employee e join leesee o on o.user_id = e.user_id join house h on h.owner_id = o.id where e.STATUS ");

        sb.append(" in ('On Process','Decline','New','Update Profile','Renew','End of Contract')");

        System.out.println("sql employee is ::: " + sb.toString());
        Query query = session.createSQLQuery(sb.toString())
               .addScalar("id")
               .addScalar("address")
               .addScalar("firstname")
               .addScalar("requestor")
               .addScalar("middlename")
               .addScalar("lastname")
               .addScalar("birthdate")
               .addScalar("civilStatus")
               .addScalar("position")
               .addScalar("inhouse")
               .addScalar("startdate")
               .addScalar("enddate")
               .addScalar("status")
               .addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(EmployeeBean.class));
        
        return query.list();
    }

    public List<EmployeeBean> getEmployees(int userId,String status) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("select e.id,Concat(h.addr_number, ' ', h.addr_street) as address ,e.firstname, "
                + "Concat(o.firstname, ' ' , o.lastname) as requestor, "
                + "e.middlename, e.lastname, e.birthdate, e.civil_status as civilStatus, "
                + "e.position, e.inhouse, e.startdate, e.enddate, e.status, e.verified "
                + "from employee e join owner o on o.user_id = e.user_id join house h on h.owner_id = o.id where e.STATUS ");
        if(status.equalsIgnoreCase("Select All")){
            sb.append(" in ('On Process','Decline','New','Update Profile','Renew','End of Contract')");
        }else{
            sb.append("='"+status+"'");
        }if(userId == 0){
            sb.append("");
        }else{
            sb.append("and e.user_id = "+userId);
        }
        sb.append(" UNION ");
        sb.append("select e.id,Concat(h.addr_number, ' ', h.addr_street) as address ,e.firstname, "
                + "Concat(o.firstname, ' ' , o.lastname) as requestor, "
                + "e.middlename, e.lastname, e.birthdate, e.civil_status as civilStatus, "
                + "e.position, e.inhouse, e.startdate, e.enddate, e.status, e.verified "
                + "from employee e join leesee o on o.user_id = e.user_id join house h on h.owner_id = o.id where e.STATUS ");
        if(status.equalsIgnoreCase("Select All")){
            sb.append(" in ('On Process','Decline','New','Update Profile','Renew','End of Contract')");
        }else{
            sb.append("='"+status+"'");
        }if(userId == 0){
            sb.append("");
        }else{
            sb.append("and e.user_id = "+userId);
        }
       
        Query query = session.createSQLQuery(sb.toString())
               .addScalar("id")
               .addScalar("address")
               .addScalar("firstname")
               .addScalar("requestor")
               .addScalar("middlename")
               .addScalar("lastname")
               .addScalar("birthdate")
               .addScalar("civilStatus")
               .addScalar("position")
               .addScalar("inhouse")
               .addScalar("startdate")
               .addScalar("enddate")
               .addScalar("status")
               .addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(EmployeeBean.class));
        return query.list();
    }
    
    public List<Employee> getEmployees(int userId, int houseId) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        String sql = "select e.id,e.firstname, e.middlename, e.lastname, e.birthdate, e.civil_status as civilStatus"
                + ", e.position, e.inhouse, e.startdate, e.enddate, e.status, e.verified from employee e "
                + "where e.user_id ="+userId;
        Query query = session.createSQLQuery(sql)
               .addScalar("id")
               .addScalar("firstname")
               .addScalar("middlename")
               .addScalar("lastname")
               .addScalar("birthdate")
               .addScalar("civilStatus")
               .addScalar("position")
               .addScalar("inhouse")
               .addScalar("startdate")
               .addScalar("enddate")
               .addScalar("status")
               .addScalar("verified")
               .setResultTransformer(Transformers.aliasToBean(Employee.class));
	return query.list();
    }

    public void deleteEmployees(int id) {
        Session session = sessionFactory.getCurrentSession();
        Employee v = (Employee) session.get(Employee.class, id);
        session.delete(v);
    }

    public Employee saveEmployees(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.save(employee);
        return employee;
    }

    public Employee updateEmployee(Employee e) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String sql = "update employee e set e.status=:status, e.verified=:verified where e.id=:id";
            
            Query query = session.createSQLQuery(sql);
            query.setString("verified", e.getVerified());
            query.setString("status", e.getStatus());
            query.setInteger("id", e.getId());
            int rowCount = query.executeUpdate();
            
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return e;
    }
    
    
    public Employee updateEmployeeViaPortal(Employee e) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String sql = "update employee e set e.civil_status=:civil_status"
                    + ", e.position=:position , e.enddate=:enddate, "
                    + "e.inhouse=:inhouse, e.status=:status where e.id=:id";
            
            Query query = session.createSQLQuery(sql);
            query.setString("civil_status", e.getCivilStatus());
            query.setString("position", e.getPosition());
            query.setString("enddate", HomePortalUtil.getparsedDate(e.getEnddate()));
            query.setString("inhouse", e.getInhouse());
            query.setString("status", e.getStatus());
            query.setInteger("id", e.getId());
            int rowCount = query.executeUpdate();
            
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return e;
    }
    
}

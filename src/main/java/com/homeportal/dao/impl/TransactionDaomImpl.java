/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import com.homeportal.bean.TransactionBean;
import com.homeportal.dao.ITransactionDAO;
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
public class TransactionDaomImpl implements ITransactionDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<TransactionBean> viewBillingByAdmin(int userId) {

        Session session = sessionFactory.getCurrentSession();

        String sql = "select a.uom, jo.id as job_order_id,ar.id as req_id,concat(a.code,'-',LPAD(ABS(jo.id),12,'0')) as referenceNumber, jo.order_type_id,ar.user_id as userId,concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId,\n"
                + "a.description,ar.requested_date as requestedDate,DATE_FORMAT(ar.updated_At,'%m/%d/%Y') as postedDate,\n"
                + "ar.quantity,ar.basic_cost,ar.additional_cost,ar.other_charges as otherCharges,ar.total_cost \n"
                + "from amenities_request ar join amenity a on a.id = ar.amenity_id\n"
                + "join job_orders jo on jo.request_id = ar.id where ar.status = 'Done' and jo.order_type_id = 1 and ar.user_Id = "+userId+"\n"
                + "UNION \n"
                + "select a.uom, jo.id as job_order_id,ar.id as req_id,concat(a.code,'-',LPAD(ABS(jo.id),12,'0')) as referenceNumber,jo.order_type_id,ar.user_id as userId,concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId,\n"
                + "a.description, ar.confirmed_date as requestedDate,DATE_FORMAT(ar.updated_At,'%m/%d/%Y') as postedDate,\n"
                + "ar.quantity,ar.basic_cost,ar.additional_cost,ar.other_charges as otherCharges,ar.total_cost\n"
                + "from service_request ar join service a on a.id = ar.service_id \n"
                + "join job_orders jo on jo.request_id = ar.id where ar.status = 'Done' and jo.order_type_id = 2 and ar.user_Id = "+userId;


        Query query = session.createSQLQuery(sql)
                .addScalar("uom")
                .addScalar("job_order_id")
                .addScalar("order_type_id")
                .addScalar("req_id")
                .addScalar("userId")
                .addScalar("referenceNumber")
                .addScalar("requestId")
                .addScalar("description")
                .addScalar("requestedDate")
                .addScalar("postedDate")
                .addScalar("quantity")
                .addScalar("basic_cost")
                .addScalar("additional_cost")
                .addScalar("otherCharges")
                .addScalar("total_cost")
                .setResultTransformer(Transformers.aliasToBean(TransactionBean.class));
        return query.list();
    }
}

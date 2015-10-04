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

import com.homeportal.bean.BillingBean;
import com.homeportal.dao.IBillingDao;
import com.homeportal.model.SOAHeader;
import com.homeportal.model.SOASummary;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/**
 *
 * @author Peter
 */
@Repository
public class BillingDaoImpl implements IBillingDao 
{
	private static Logger logger = Logger.getLogger(BillingDaoImpl.class);
	
	private static final String GET_USER_OUTSTANDING_BALANCES_SQL_SCALAR = MessageBundle.getSqlProperty("get.user.outstanding.balances.sql.scalar");
	private static final String GET_USER_MONTHLY_STATEMENT_LIST_SQL_SCALAR = MessageBundle.getSqlProperty("get.user.monthly.statement.list.sql.scalar");
	
	private static final String GET_SOA_HEADER_HQL = MessageBundle.getSqlProperty("get.soa.header.hql");
	private static final String GET_SOA_SUMMARY_HQL = MessageBundle.getSqlProperty("get.soa.summary.hql");
	


    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    
    
    /**
     * Gets all the outstanding balances for a user/owner
     * 
     * */
    public List<BillingBean> getUserOutstandingBalances(int userId, int houseId)
    {
    	logger.debug("DAO Getting user outstanding balances...");
    	System.out.println("DAO Getting user outstanding balances...");
    	
    	final String GET_USER_OUTSTANDING_BALANCES_SQL = SQLUtil.getUserOutstandingBillSQL(userId, houseId);
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_USER_OUTSTANDING_BALANCES_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_USER_OUTSTANDING_BALANCES_SQL);
				query.setResultTransformer(Transformers.aliasToBean(BillingBean.class));
			}
	
		return query.list();
	}
    
    
    
    // OVERLOADED METHOD FOR DELETION
    public List<BillingBean> viewBillingByPortal(int userId)
    {
    	return null;
    }

    
    public List<BillingBean> viewBillingByPortal(int userId, int houseId, String payeeName)
    {
    	System.out.println("*** DAO Getting monthly bill for this payee: " + payeeName);
    	logger.info("*** DAO Getting monthly bill for this payee: " + payeeName);
    	
    	Session session = sessionFactory.getCurrentSession();
    	String sql = SQLUtil.getOwnerMonthlyBillSQL(userId, houseId, payeeName);	
    	
    	Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("userId")
                .addScalar("payeeName")
                .addScalar("requestId")
                .addScalar("orderTypeId")
                .addScalar("reference")
                .addScalar("description")
                .addScalar("requestedDate")
                .addScalar("postedDate")
                .addScalar("amount")
                .addScalar("paidAmount")
                .addScalar("balance")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
    	
    	return query.list();
    }
    
    

    public List<BillingBean> viewBillingByAdmin()
    {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select Concat(h.addr_number, ' ', h.addr_street) as address, "
                + "Concat(o.firstname, ' ' , o.lastname)as fullname,sum(jo.price) as amount "
                + "from job_orders jo join amenities_request ar on ar.id = jo.request_id "
                + "join amenity a on a.id = ar.amenity_id join owner o on o.user_id = ar.user_id "
                + "join house h on h.id = jo.house_id group by ar.user_id";
        Query query = session.createSQLQuery(sql)
                .addScalar("address")
                .addScalar("fullname")
                .addScalar("amount")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
        return query.list();
    }
    
    public List<BillingBean>  getPayeeDetails(int userId,int roleId) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println("userId : " + userId + " roleId : " + roleId);
        String sql = "";
        String ownerSql = "select Concat(h.addr_number, ' ', h.addr_street) as payeeAddress, "
                + "Concat(o.firstname, ' ' , o.lastname)as payeeName from "
                + "house h join owner o on o.id = h.owner_id "
                + "join users u on u.id = o.user_id where o.user_id = "+userId;
        
        String leesseSql = "select Concat(h.addr_number, ' ', h.addr_street) as payeeAddress, "
                + "Concat(l.firstname, ' ' , l.lastname)as payeeName from "
                + "house h join leesee l on l.house_id = h.id join "
                + "users u on u.id = l.user_id where l.user_id = "+userId;
        
        if(roleId == 3)
        {
            sql = ownerSql;
        }else
        {
            sql = leesseSql;
        }
        System.out.println("sql is : " + sql);
        Query query = session.createSQLQuery(sql)
                .addScalar("payeeAddress")
                .addScalar("payeeName")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
        return query.list();
    }
    
    public List<BillingBean> getRoleId(int userId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select role_id as roleId from users where id ="+userId;
        Query query = session.createSQLQuery(sql)
                .addScalar("roleId")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
        return query.list();
    }

    
    
    
    /**
     * Gets the list of monthly statement for a particular user and house
     * */
    public List<BillingBean> getUserMonthlyStatementList(int userId, int houseId)
    {
    	logger.info("Getting list of monthly statement...");
    	
    	final String GET_USER_MONTHLY_STATEMENT_LIST_SQL = SQLUtil.getUserMonthlyStatementList(userId, houseId);
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_USER_MONTHLY_STATEMENT_LIST_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_USER_MONTHLY_STATEMENT_LIST_SQL);
				query.setResultTransformer(Transformers.aliasToBean(BillingBean.class));
			}
    
        return query.list();
    }

    
    
    public List<BillingBean> viewORByAdmin(int userId,int orNumber, String postedDate) {
        Session session = sessionFactory.getCurrentSession();
        
        String sql = "select ors.id as id, ors.or_number as orNumber, \n" +
                    "DATE_FORMAT(ors.updated_At,'%m/%d/%Y') as postedDate ,\n" +
                    "Concat(o.firstname, ' ' , o.lastname)as payeeName\n" +
                    "from owner o join users u on u.id = o.user_id\n" +
                    "join official_receipt ors on u.id = ors.user_id\n" +
                    "where o.user_id != 1 and o.user_id = '"+userId+"' \n" +
                    "OR DATE_FORMAT(ors.updated_At,'%m/%d/%Y') = '"+postedDate+"'\n" +
                    "OR ors.or_number = '"+orNumber+"' group by ors.or_number\n"+
                    "UNION\n"+
                    "select ors.id as id,ors.or_number as orNumber, \n" +
                    "DATE_FORMAT(ors.updated_At,'%m/%d/%Y') as postedDate ,\n" +
                    "Concat(o.firstname, ' ' , o.lastname)as payeeName\n" +
                    "from leesee o join users u on u.id = o.user_id\n" +
                    "join official_receipt ors on u.id = ors.user_id\n" +
                    "where o.user_id = '"+userId+"' \n" +
                    "OR DATE_FORMAT(ors.updated_At,'%m/%d/%Y') = '"+postedDate+"'\n" +
                    "OR ors.or_number = '"+orNumber+"' group by ors.or_number\n"+
                    "UNION\n"+
                    "select ors.id as id, ors.or_number as orNumber,"
                    + "DATE_FORMAT(ors.updated_At,'%m/%d/%Y') as postedDate,"
                    + "sr.payeeName from owner o join users u on u.id = o.user_id "
                    + "join official_receipt ors on u.id = ors.user_id "
                    + "join job_orders jo on jo.id = ors.job_order_id "
                    + "join service_request sr on sr.id = jo.request_id where ors.user_id = 1 group by ors.or_number";
        
       
        Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("orNumber")
                .addScalar("postedDate")
                .addScalar("payeeName")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
        return query.list();
    }

    public List<BillingBean> viewStatementByAdmin(int userId, String statementDate)
    {
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select sh.id, Concat(o.firstname, ' ' , o.lastname)as payeeName,");
        sb.append("Concat(o.firstname, o.lastname) as description, h.id as houseId, sh.report_name as reportName, ");
        sb.append("sh.report_name as postedDate ");
        sb.append("from owner o join users u on u.id = o.user_id ");
        sb.append("join statement_history sh on sh.user_id = o.user_id join house h on h.owner_id = o.id ");
        sb.append("where ");
        if(statementDate.equalsIgnoreCase("0") && userId == 0)
        {
            sb.append("o.user_id = '"+userId+"' or sh.report_name like '%"+statementDate+"%'");
        }
        if(!statementDate.equalsIgnoreCase("0"))
        {
            sb.append("sh.report_name like '%"+statementDate+"%'");
        }
        if(!statementDate.equalsIgnoreCase("0") && userId != 0)
        {
            sb.append(" and ");
        }
        if(userId != 0)
        {
            sb.append("o.user_id = '"+userId+"'");
        }
        sb.append("UNION ");
        sb.append("select sh.id, Concat(o.firstname, ' ' , o.lastname)as payeeName,");
        sb.append("Concat(o.firstname, o.lastname) as description, h.id as houseId, sh.report_name as reportName,");
        sb.append("sh.report_name as postedDate ");
        sb.append("from leesee o join users u on u.id = o.user_id ");
        sb.append("join statement_history sh on sh.user_id = o.user_id join house h on h.id = o.house_id ");
        sb.append("where ");
        if(statementDate.equalsIgnoreCase("0") && userId == 0)
        {
            sb.append("o.user_id = '"+userId+"' or sh.report_name like '%"+statementDate+"%'");
        }
        if(!statementDate.equalsIgnoreCase("0"))
        {
            sb.append("sh.report_name like '%"+statementDate+"%'");
        }
        if(!statementDate.equalsIgnoreCase("0") && userId != 0)
        {
            sb.append(" and ");
        }
        if(userId != 0)
        {
            sb.append("o.user_id = '"+userId+"'");
        }
        
        Query query = session.createSQLQuery(sb.toString())
                .addScalar("id")
                .addScalar("payeeName")
                .addScalar("description")
                .addScalar("postedDate")
                .addScalar("houseId")
                .addScalar("reportName")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
        return query.list();
    }

    public List<BillingBean> viewOrByPortal(int userId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select id, or_number as orNumber from official_receipt where user_id = "+userId+" group by or_number order by id desc Limit 10";
        Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("orNumber")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
        return query.list();
    }

    public List<BillingBean> viewCollections(int userId) {
        Session session = sessionFactory.getCurrentSession();
        //String sql = "select sh.id, sh.report_name as postedDate,  concat('Collections for ',MONTHNAME(STR_TO_DATE(MONTH(report_date), '%m')),' ',YEAR(report_date)) as description from collections_history sh "
                //+ " order by report_date DESC Limit 6";
        
        String sql = "select sh.id, sh.report_name as postedDate,  'Collection report' as description from collections_history sh "
                + " order by report_date DESC Limit 6";
        
         Query query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("postedDate")
                .addScalar("description")
                .setResultTransformer(Transformers.aliasToBean(BillingBean.class));
         
        return query.list();
    }
    
    
    
    
    /**
     * Gets the SOAHeader object using the userId, houseId and billMonth
     * */
    public SOAHeader getSOAHeader(int userId, int houseId, String billMonth)
    {
    	System.out.println("Getting the SOAHeader...");
    	logger.info("Getting the SOAHeader...");
		
	    Session session = sessionFactory.getCurrentSession();
	    SOAHeader soaHeader = null;
            
		Query query = session.createQuery(GET_SOA_HEADER_HQL);
				
		query.setParameter("userId", userId);
		query.setParameter("houseId", houseId);
		query.setParameter("billingMonth", billMonth);
				
		soaHeader = (SOAHeader) query.list().get(0);
		
		if (soaHeader != null)
		{
			System.out.println("Got SOAHeader for user: " + soaHeader.getUserId());
		}
		else
		{
			System.out.println("SOAHeader is NULL.");
		}
		
		return soaHeader;
	}
    
    
    
    /**
     * Gets the SOASummary object using the soaHeaderId
     * */
    public SOASummary getSOASummary(int soaHeaderId)
    {
    	System.out.println("Getting the SOASummary...");
    	logger.info("Getting the SOASummary...");
		
	    Session session = sessionFactory.getCurrentSession();
	    SOASummary soaSummary = null;
            
		Query query = session.createQuery(GET_SOA_SUMMARY_HQL);
				
		query.setParameter("id", soaHeaderId);
				
		soaSummary = (SOASummary) query.list().get(0);
		
		if (soaSummary != null)
		{
			System.out.println("Got SOASummary for user: " + soaSummary.getUserId());
		}
		else
		{
			System.out.println("SOASummary is NULL.");
		}
		
		return soaSummary;
	}

}

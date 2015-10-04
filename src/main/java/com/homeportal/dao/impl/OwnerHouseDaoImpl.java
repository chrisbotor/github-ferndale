package com.homeportal.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.homeportal.bean.OwnerHouseBean;
import com.homeportal.dao.IOwnerHouseDAO;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/*
 * Class used to get the Owner - House combination for create request form
 * 
 * */
@Repository
public class OwnerHouseDaoImpl implements IOwnerHouseDAO
{
	private static Logger logger = Logger.getLogger(OwnerHouseDaoImpl.class);
	
	private static final String GET_OWNER_HOUSE_SQL = MessageBundle.getSqlProperty("get.owner.house.sql");
	private static final String GET_OWNER_HOUSE_SQL_SCALAR = MessageBundle.getSqlProperty("get.owner.house.sql.scalar");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	@SuppressWarnings("unchecked")
	@Override
	public List<OwnerHouseBean> getOwnerHouseList() 
	{
		System.out.println("DAO getting all the REQUESTOR in the format OWNER - HOUSE address...");
		logger.info("DAO getting all the REQUESTOR in the format OWNER - HOUSE address...");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		List<String> scalarPropsList = null;
		
		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_OWNER_HOUSE_SQL_SCALAR);
			
			if (scalarPropsList != null)
			{
				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_OWNER_HOUSE_SQL);
				query.setResultTransformer(Transformers.aliasToBean(OwnerHouseBean.class));
			}
		return query.list();
	}

}

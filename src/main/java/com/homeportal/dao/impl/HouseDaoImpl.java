/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.homeportal.bean.HouseBean;
import com.homeportal.bean.OwnerHouseBean;
import com.homeportal.dao.IHouseDAO;
import com.homeportal.model.House;
import com.homeportal.model.Rates;
import com.homeportal.util.MessageBundle;
import com.homeportal.util.SQLUtil;

/**
 *
 * @author Peter
 */
@Repository
public class HouseDaoImpl implements IHouseDAO
{
	private static Logger logger = Logger.getLogger(OwnerDaoImpl.class);
	
	private static final String GET_HOUSE_BY_HOUSE_ID_HQL = MessageBundle.getSqlProperty("get.house.list.by.house.id.hql");
	private static final String GET_HOUSE_LIST_BY_OWNER_ID_HQL = MessageBundle.getSqlProperty("get.house.list.by.owner.id.hql");
	
	private static final String GET_ALL_HOUSE_SQL = MessageBundle.getSqlProperty("get.all.houses.sql");
	private static final String GET_ALL_HOUSE_SQL_SCALAR = MessageBundle.getSqlProperty("get.all.houses.sql.scalar");
	
	
    @Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

     
     /**
      * Gets all the houses from the database (used by Move in/move out service
      * */
     @SuppressWarnings("unchecked")
     public List<House> getAllHouse()
     {
     	logger.info("Getting all HOUSES from the database...");
     	System.out.println("Getting all HOUSES from the database...");
     	
     	Session session = sessionFactory.getCurrentSession();
 		Query query = null;
 		List<String> scalarPropsList = null;
 		
 		scalarPropsList = SQLUtil.parseSQLScalarProperties(GET_ALL_HOUSE_SQL_SCALAR);
 			
 			if (scalarPropsList != null)
 			{
 				query = SQLUtil.buildSQLQuery(session, scalarPropsList, GET_ALL_HOUSE_SQL);
 				query.setResultTransformer(Transformers.aliasToBean(HouseBean.class));
 			}
 		return query.list();
     }
     
     
     
    public List<HouseBean> getHouses() {
        // Retrieve session from Hibernate
         Session session = sessionFactory.getCurrentSession();
          Query query = null;
        try{
	
	String sql = "select h.id, Concat(o.firstname, ' ' , o.lastname) as owner, "
                + "Concat(h.addr_number, ' ' , h.addr_street) as address, "
                + "h.lot_area as lotArea, h.rented, h.title, o.user_Id as userId"
                + " from house h join owner o on h.owner_id = o.id";
         System.out.println("house list query : " + sql);
         query = session.createSQLQuery(sql)
                .addScalar("id")
                .addScalar("owner")
                .addScalar("address")
                .addScalar("lotArea")
                .addScalar("title")
                .addScalar("rented")
                .addScalar("userId")
                .setResultTransformer(Transformers.aliasToBean(HouseBean.class));
	System.out.println("getHouses list is : " + query.list().size());
	
        }catch(Exception e)
        {
            System.out.println("Error is : " + e.getMessage());
        }
        return query.list();
    }

    public void deleteHouses(int id) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
	House h = (House) session.get(House.class, id);
	// Delete
	session.delete(h);
    }

    public House saveHouses(House house) {
         // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
	session.save(house);
	return house;
    }

    public House updateHouse(House house) {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
	session.update(house);
	return house;
    }
    
    public House updateRented(House house) 
    {
        // Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
        String sql = "update house h set h.rented=:rented, h.updated_at=:updated_at where h.id=:id";
	Query query = session.createSQLQuery(sql);
            query.setString("rented", house.getRented());
            query.setDate("updated_at", new Date());
            query.setInteger("id", house.getId());
            int rowCount = query.executeUpdate();
            return house;
    }
    
    
    /**
     * Gets a house using its house id
     * */
    @Override
	public House getHouseByHouseId(int houseId) 
    {
		logger.debug("Getting house using houseId: " +  houseId);
		
		Session session = sessionFactory.getCurrentSession();
		House house = null;
		
		if (houseId > 0)
		{
			Query query = session.createQuery(GET_HOUSE_BY_HOUSE_ID_HQL);
			query.setParameter("houseId", houseId);
			house = (House) query.list().get(0);
		}
	
		return house;
    }
    
    

    /**
     * Gets the list of house of a home owner
     * */
	@SuppressWarnings("unchecked")
	@Override
	public List<House> getHouseListByOwnerId(int ownerId)
	{
		logger.debug("Getting the list of house for the owner id: " +  ownerId);
		
		Session session = sessionFactory.getCurrentSession();
		List<House> houseList = null;
		
		if (ownerId > 0)
		{
			Query query = session.createQuery(GET_HOUSE_LIST_BY_OWNER_ID_HQL);
			query.setParameter("ownerId", ownerId);
			houseList = (List<House>) query.list();
		}
	
		return houseList;
	}
    
}

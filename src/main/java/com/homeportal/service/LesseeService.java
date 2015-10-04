/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.bean.LesseeBean;
import com.homeportal.dao.impl.LesseeDaoImpl;
import com.homeportal.model.Lessee;
import com.homeportal.util.LesseeUtil;

/**
 *
 * @author PSP36488
 */
@Service
public class LesseeService
{

    private LesseeDaoImpl lesseeDaoImpl;
    private LesseeUtil lesseeUtil;

    /**
	 * Get all lessee
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<LesseeBean>  getLeeseeList()
	{
            return lesseeDaoImpl.getLesseeList();
	}
        
     
	
	/**
	 * Get all leesee
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Lessee>  getLesseeByUserId(String userId)
	{
            return lesseeDaoImpl.getLesseeById(userId);
	}
        
	
	/**
	 * Get lessee by user id and house id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Lessee getLesseeByUserIdHouseId(int userId, int houseId)
	{
            return lesseeDaoImpl.getLesseeByUserIdHouseId(userId, houseId);
	}
	
	
        
	/**
	 * 
	 * */
	@Transactional
	public Lessee addLessee(Lessee lessee)
	{
	
		return lesseeDaoImpl.saveLessee(lessee);
    }
        


	/**
	 * 
	 * */
	@Transactional
	public void updateLessee(Lessee l)
	{
		lesseeDaoImpl.updateLessee(l);
    }
	
	
	

	/**
	 * Create new Lessee
	 * @param data - json data from request
	 * @return created contacts
	 */
	@Transactional
	public List<Lessee> create(Object data){

        List<Lessee> newLeesees = new ArrayList<Lessee>();
		List<Lessee> list = lesseeUtil.getLesseeFromRequest(data);
		
		for (Lessee lessee : list)
		{
			newLeesees.add(lesseeDaoImpl.saveLessee(lessee));
		}

		return newLeesees;
	}


	/**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 */
	@Transactional
	public List<Lessee> update(Object data){

		List<Lessee> returnLeesees = new ArrayList<Lessee>();
		List<Lessee> updatedLeesees = lesseeUtil.getLesseeFromRequest(data);
		
		for (Lessee Leesee : updatedLeesees)
		{
		}

		return returnLeesees;
	}

	/**
	 * Delete contact/contacts
	 * @param data - json data from request
	 */
	@Transactional
	public void delete(Object data){

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			List<Integer> deleteLeesees = lesseeUtil.getListIdFromJSON(data);
			for (Integer id : deleteLeesees){
				lesseeDaoImpl.deleteLessee(id);
			}

		} else { //it is only one object - cast to object/bean
			Integer id = Integer.parseInt(data.toString());
			lesseeDaoImpl.deleteLessee(id);
		}
	}
	
	
	
	/**
	 * Get all lessee for the Lessee Search
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Lessee> getAllLessee() throws SQLException
	{
		return lesseeDaoImpl.getAllLessee();
	}
	
	
	
	/**
	 * Create a new Leesee record
	 * */
	 @Transactional
	 public Lessee add(Lessee leesee)
	 {
		 return lesseeDaoImpl.saveLessee(leesee);
	 }
	 
	 

    /**
     * @param lesseeDaoImpl the lesseeDaoImpl to set
     */
    @Autowired
    public void setLesseeDaoImpl(LesseeDaoImpl lesseeDaoImpl) {
        this.lesseeDaoImpl = lesseeDaoImpl;
    }

    /**
     * @param lesseeUtil the lesseeUtil to set
     */
    @Autowired
    public void setLesseeUtil(LesseeUtil lesseeUtil) {
        this.lesseeUtil = lesseeUtil;
    }
}

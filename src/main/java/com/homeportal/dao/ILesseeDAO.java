/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import com.homeportal.bean.LesseeBean;
import com.homeportal.model.Lessee;
import java.util.List;

/**
 *
 * @author PSP36488
 */
public interface ILesseeDAO
{
	List<LesseeBean> getLessee();
    List<Lessee> getLesseeById(String userId);
    void deleteLessee(int id);
    Lessee saveLessee(Lessee lessee);
    void updateLessee(Lessee lessee);
    Lessee getLesseeByUserIdHouseId(int userId, int houseId);
   
}

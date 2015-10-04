/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeportal.dao.impl.OfficialReceiptDaoImpl;
import com.homeportal.model.OfficialReceipt;
import com.homeportal.model.OfficialReceiptDetails;
import com.homeportal.model.OfficialReceiptHeader;

/**
 *
 * @author Peter
 */
@Service
public class OfficialReceiptService 
{
    
    private OfficialReceiptDaoImpl officialReceiptDaoImpl;
    
    @Transactional
    public OfficialReceipt payBills(OfficialReceipt or) 
    {
            return officialReceiptDaoImpl.saveOfficialReceipt(or);
    }
    
    
    /*
     * 	Gets the OfficialReceipt object
     * **/
    @Transactional
    public OfficialReceipt getOR(int userId, int houseId, int orNumber) 
    {
            return officialReceiptDaoImpl.getOR(userId, houseId, orNumber);
    }
    
    
    
    /**
     * Gets the OfficialReceiptHeader object
     * */
    @Transactional
    public OfficialReceiptHeader getORHeader(int userId, int houseId, int orNumber) 
    {
            return officialReceiptDaoImpl.getORHeader(userId, houseId, orNumber);
    }
    
    
    
    /**
     * Gets the OfficialReceiptDetails object using its header_id and ref_num (OR jobOrderId) columns
     * */
    @Transactional
    public OfficialReceiptDetails getORDetails(int orHeaderId, int orJobOrderId)
    {
            return officialReceiptDaoImpl.getORDetails(orHeaderId, orJobOrderId);
    }
    
    
    /**
     * Gets the OR list for home owner view
     * */	
    @Transactional
    public List<OfficialReceipt> getORList(int userId, int houseId) 
    {
            return officialReceiptDaoImpl.getORList(userId, houseId);
    }
    
    
    /**
     * Gets the OR using the OR number
     * */	
    @Transactional
    public List<OfficialReceipt> getORByORNumber(int orNumber) 
    {
            return officialReceiptDaoImpl.getORByORNumber(orNumber);
    }
    
    
    /**
     * Updates the OR and set its status to 'F'
     * */	
    @Transactional
    public OfficialReceipt cancelOR(OfficialReceipt or) 
    {
            return officialReceiptDaoImpl.updateOR(or);
    }
    
    
    
    

    /**
     * @param officialReceiptDaoImpl the officialReceiptDaoImpl to set
     */
    @Autowired
    public void setOfficialReceiptDaoImpl(OfficialReceiptDaoImpl officialReceiptDaoImpl) {
        this.officialReceiptDaoImpl = officialReceiptDaoImpl;
    }
    
}

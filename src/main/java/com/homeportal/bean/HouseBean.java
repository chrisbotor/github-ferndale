/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.bean;



/**
 *
 * @author racs
 */
public class HouseBean
{
    
	private int houseId;
	private int ownerId;
	private int userId;
    private String address;
    private String addressNumber;
    private String addressSt;
    private String type;
    private int phase;
    private String rented;
    private String title;
    private Double lotArea;
    
    
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressNumber() {
		return addressNumber;
	}
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	public String getAddressSt() {
		return addressSt;
	}
	public void setAddressSt(String addressSt) {
		this.addressSt = addressSt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPhase() {
		return phase;
	}
	public void setPhase(int phase) {
		this.phase = phase;
	}
	public String getRented() {
		return rented;
	}
	public void setRented(String rented) {
		this.rented = rented;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getLotArea() {
		return lotArea;
	}
	public void setLotArea(Double lotArea) {
		this.lotArea = lotArea;
	}
}

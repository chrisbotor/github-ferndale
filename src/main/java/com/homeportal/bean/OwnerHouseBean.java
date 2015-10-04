package com.homeportal.bean;

public class OwnerHouseBean
{
	
	// private int id;
	private int houseId;
	private int userId;
    private String firstname;
    private String lastname	;
    private String houseAddrNumber;
    private String houseAddrStreet;
    private String ownerHouseLabel;
    
    
    
/*	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	
	
	public String getFirstname() {
		return firstname;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public String getHouseAddrNumber() {
		return houseAddrNumber;
	}
	public void setHouseAddrNumber(String houseAddrNumber) {
		this.houseAddrNumber = houseAddrNumber;
	}
	public String getHouseAddrStreet() {
		return houseAddrStreet;
	}
	public void setHouseAddrStreet(String houseAddrStreet) {
		this.houseAddrStreet = houseAddrStreet;
	}
	public String getOwnerHouseLabel() {
		return ownerHouseLabel;
	}
	public void setOwnerHouseLabel(String ownerHouseLabel) {
		this.ownerHouseLabel = ownerHouseLabel;
	}
   
    

    
}

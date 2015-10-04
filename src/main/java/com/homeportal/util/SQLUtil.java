package com.homeportal.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.homeportal.model.AssociationDue;
import com.homeportal.model.WaterReading;


/*
 * Utility class for creating SQL queries
 * 
 * */
public class SQLUtil 
{
	
	private static Logger logger = Logger.getLogger(SQLUtil.class);
	
	
	
	/**
	 * Gets the amenity requests based on the given list of status
	 * */
	public static String getServiceRequestsByStatus(String statuses)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(select sr.id as id, Concat(h.addr_number, ' ', h.addr_street) as address, concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, s.DESCRIPTION as des, sr.preferred_date as preferredDate, ");
		sb.append("s.reg_value as basic_cost,sr.additional_cost, sr.total_cost, sr.confirmed_date as confirmedDate, ");
		sb.append("sr.preferred_time as preferredTime, s.uom as uom, sr.STATUS,s.reg_value as regularPrice, s.excess_value as excessPrice, ");
		sb.append("sr.quantity,s.max_regular as maxRegular, sr.remarks as remarks, sr.other_charges as otherCharges,sr.updated_At as updated ");
		sb.append("from service_request sr, service s, owner o, house h ");
		sb.append("where s.id = sr.service_id and o.user_id = sr.user_id and h.owner_id = o.id and sr.house_id = h.id ");
		sb.append("and sr.STATUS in (");
		sb.append(statuses);
		sb.append(")) UNION ");
		sb.append("(select sr.id as id, Concat(h.addr_number, ' ', h.addr_street) as address, concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(l.firstname, ' ' , l.lastname) as requestor, s.DESCRIPTION as des, sr.preferred_date as preferredDate, ");
		sb.append("s.reg_value  as basic_cost,sr.additional_cost, sr.total_cost, sr.confirmed_date as confirmedDate, ");
		sb.append("sr.preferred_time as preferredTime, s.uom as uom, sr.STATUS, s.reg_value as regularPrice, s.excess_value as excessPrice, ");
		sb.append("sr.quantity,s.max_regular as maxRegular,sr.remarks as remarks, sr.other_charges as otherCharges,sr.updated_At as updated ");
		sb.append("from service_request sr, service s, leesee l, house h ");
		sb.append("where s.ID = sr.SERVICE_ID and l.user_id = sr.user_id and h.id = l.house_id and sr.house_id = h.id ");
		sb.append("and sr.STATUS in (");
		sb.append(statuses);
		sb.append(")) order by preferredDate asc");
		
		printSQL(sb.toString(), "getting service requests by status");
		
		return sb.toString();
	}
	
	
	
	/**
	 * Gets the amenity requests based on the given list of status
	 * */
	public static String getAmenityRequestsByStatus(String statuses)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(select ar.id as id,a.id as amenityId, h.id as houseId, concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("a.DESCRIPTION as des, ar.requested_date as requestedDate, ");
		sb.append("ar.START_TIME as startTime, ar.END_TIME as endTime, ");
		sb.append("a.reg_value as basic_cost, ar.additional_cost as additional_cost, ");
		sb.append("ar.total_cost as total_cost, a.uom as uom, ar.STATUS, a.reg_value as regularPrice, ");
		sb.append("a.excess_value as excessPrice, ar.quantity,a.max_regular as maxRegular, ar.remarks as remarks, ");
		sb.append("ar.other_charges as otherCharges, ar.updated_At as updated ");
		sb.append("from amenities_request ar, amenity a, owner o, house h ");
		sb.append("where a.id = ar.amenity_id and o.user_id = ar.user_id ");
		sb.append("and h.owner_id = o.id and ar.house_id = h.id ");
		sb.append("and ar.STATUS in (");
		sb.append(statuses);
		sb.append(")) UNION (select ar.id as id,a.id as amenityId, h.id as houseId, ");
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(l.firstname, ' ' , l.lastname) as requestor, ");
		sb.append("a.DESCRIPTION as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, ar.END_TIME as endTime, ");
		sb.append("a.reg_value as basic_cost, ar.additional_cost as additional_cost, ar.total_cost as total_cost, ");
		sb.append("a.uom as uom, ar.STATUS, a.reg_value as regularPrice, a.excess_value as excessPrice, ");
		sb.append("ar.quantity,a.max_regular as maxRegular, ar.remarks as remarks, ar.other_charges as otherCharges, ar.updated_At as updated ");
		sb.append("from amenities_request ar, amenity a, leesee l, house h ");
		sb.append("where a.id = ar.amenity_id and l.user_id = ar.user_id ");
		sb.append("and l.house_id = l.house_id and ar.house_id = h.id ");
		sb.append("and ar.STATUS in (");
		sb.append(statuses);
		sb.append(")) order by requestedDate asc");
		
		printSQL(sb.toString(), "getting amenity requests by status");
		
		return sb.toString();
	}
	
	
	
	
	/*
	(select ar.id as id,a.id as amenityId, h.id as houseId, concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, Concat(h.addr_number, ' ', h.addr_street) as address, Concat(o.firstname, ' ' , o.lastname) as requestor, 
			a.DESCRIPTION as des, ar.requested_date as requestedDate, 
			ar.START_TIME as startTime, ar.END_TIME as endTime, 
			a.reg_value as basic_cost, ar.additional_cost as additional_cost, 
			ar.total_cost as total_cost, a.uom as uom, ar.STATUS, a.reg_value as regularPrice, a.excess_value as excessPrice,
			ar.quantity,a.max_regular as maxRegular, ar.remarks as remarks, ar.other_charges as otherCharges, ar.updated_At as updated 
			from amenities_request ar, amenity a, owner o, house h 
			where a.id = ar.amenity_id and o.user_id = ar.user_id and h.owner_id = o.id and ar.house_id = h.id 
			and ar.STATUS in ('Reserved','Booked','New','Change Request')) UNION (select ar.id as id,a.id as amenityId, h.id as houseId, 
					Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, 
					Concat(h.addr_number, ' ', h.addr_street) as address, 
					Concat(l.firstname, ' ' , l.lastname) as requestor, 
					a.DESCRIPTION as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, ar.END_TIME as endTime, 
					a.reg_value as basic_cost, ar.additional_cost as additional_cost, ar.total_cost as total_cost, 
					a.uom as uom, ar.STATUS, a.reg_value as regularPrice, a.excess_value as excessPrice,
					ar.quantity,a.max_regular as maxRegular, ar.remarks as remarks, ar.other_charges as otherCharges, ar.updated_At as updated 
					from amenities_request ar, amenity a, leesee l, house h where a.id = ar.amenity_id and l.user_id = ar.user_id 
					and l.house_id = l.house_id and ar.house_id = h.id 
					and ar.STATUS in ('RESERVED','BOOKED','NEW','CHANGE REQUEST')) order by requestedDate asc*/
	
	
	/**
	 * Gets the current water rate.
	 * */
	public static String getWaterRateSQL()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select amount from rates where code = 'water'");
		
		printSQL(sb.toString(), "getting water reading rate");
		
		return sb.toString();
	}
	
	
	/**
	 * Gets the current association due rate.
	 * */
	public static String getAssociationDueRateSQL()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select u.id as userID, h.id as houseID, h.lot_area as houseLotArea, ");
		sb.append(" r.amount as rate, (h.lot_area * r.amount) as amount ");
		sb.append(" from users u, owner o, house h, rates r");
		sb.append(" where u.status = 'A'");
		sb.append(" and r.code = 'assocdue'");
		sb.append(" and u.id = o.user_id ");
		sb.append(" and h.owner_id = o.id");
		
		printSQL(sb.toString(), "getting association due rate");
		
		return sb.toString();
	}
	
	
	/**
	 * Inserts association due.
	 * */
	public static String getInsertAssociationDueSQL(AssociationDue assocDue)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("insert into association_due (user_id,house_id,remarks,lot_area,rate,amount,created_at,updated_at) values (");
		sb.append(assocDue.getUserId());
		sb.append(", ");
		sb.append(assocDue.getHouseId());
		sb.append(", '");
		sb.append(assocDue.getRemarks());
		sb.append("', ");
		sb.append(assocDue.getLotArea());
		sb.append(", ");
		sb.append(assocDue.getRate());
		sb.append(", ");
		sb.append(assocDue.getAmount());
		sb.append(", now(), now())");
		
		printSQL(sb.toString(), "inserting association dues");
		
		return sb.toString();
	}
	
	
	
	/*
	 * Inserts water reading
	 * */
	public static String getInsertWaterReadingSQL(WaterReading waterReading)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("insert into water_reading (user_id,house_id,start_reading,end_reading,consumption,amount,created_at,updated_at) values ("); 
		sb.append(waterReading.getUserId());
		sb.append(", ");
		sb.append(waterReading.getHouseId());
		sb.append(", ");
		sb.append(waterReading.getStartReading());
		sb.append(", ");
		sb.append(waterReading.getEndReading());
		sb.append(", ");
		sb.append(waterReading.getConsumption());
		sb.append(", ");
		sb.append(waterReading.getAmount());
		sb.append(", now(), now())");
		
		printSQL(sb.toString(), "inserting water reading");
		
		return sb.toString();
	}
	
	
	
	/*
	 * Gets the Amenity Requests for the Home Owners page
	 * */
	public static String getOwnerAmenityRequestsSQL(int userId, int houseId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, "); 
		sb.append("a.DESCRIPTION as des, ");  
		sb.append("ar.requested_date as requestedDate, ");
		sb.append("concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, ");
		sb.append("ar.START_TIME as startTime, ");
		sb.append("ar.END_TIME as endTime, ");
		sb.append("ar.quantity as quantity, "); 
		sb.append("ar.STATUS, ");
		sb.append("a.uom as uom ");
		sb.append("from amenities_request ar, amenity a ");
		sb.append("where a.ID = ar.AMENITY_ID ");
		sb.append("and ar.STATUS != 'Done' ");
		sb.append("and ar.STATUS != 'Cancel' ");
		sb.append("and ar.STATUS != 'Paid' ");
		sb.append("and ar.user_id = ");
		sb.append(userId);
		sb.append(" and ar.house_id = ");
		sb.append(houseId);
		sb.append(" order by requested_date desc");
		
		printSQL(sb.toString(), "getting the amenity requests for the owner home page");
		
		return sb.toString();
	}
	
	
	
	/*
	 * Gets the Service Requests for the Home Owners page
	 * */
	public static String getOwnerServiceRequestsSQL(int userId, int houseId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, ");
		sb.append("a.DESCRIPTION as des, "); 
		sb.append("ar.preferred_date as preferredDate, "); 
		sb.append("ar.confirmed_date as confirmedDate, ");
		sb.append("concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, ");
		sb.append("ar.preferred_time as preferredTime, ");
		sb.append("ar.quantity as quantity, ");
		sb.append("ar.STATUS, ");
		sb.append("a.uom as uom ");
		sb.append("from service_request ar, service a ");
		sb.append("where a.ID = ar.SERVICE_ID ");
		sb.append("and ar.STATUS != 'Done' ");
		sb.append("and ar.STATUS != 'Cancel' ");  
		sb.append("and ar.STATUS != 'Paid' ");
		sb.append("and ar.USER_ID = ");
		sb.append(userId);
		sb.append(" and ar.house_id = ");
		sb.append(houseId);
		sb.append(" order by ar.preferred_date desc");
		
		printSQL(sb.toString(), "getting the Service Requests for the Home Owners page");
		
		return sb.toString();
	}
	
	
	
	/**
	 * Gets all the outstanding balances for the user/owner
	 * */
	public static String getUserOutstandingBillSQL(int userId, int houseId)
	{
		StringBuilder sb = new StringBuilder();
		
		// NEED TO VALIDATE, THIS DOES NOT PICK UP PENALTIES
		sb.append("(select jo.id as id, ar.user_id as userId, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat(a.code,'-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("a.description, ar.requested_date as requestedDate, ");
		sb.append("DATE_FORMAT(ar.updated_At,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(ar.total_cost,2) as amount, TRUNCATE(ar.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((ar.total_cost - ar.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("amenities_request ar, amenity a, job_orders jo ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 1 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = ar.id and ");
		sb.append("a.id = ar.amenity_id and ");
		sb.append("ar.status = 'Done' and ");
		sb.append("TRUNCATE((ar.total_cost -  IFNULL(ar.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("ar.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, sr.user_id as userId, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat(s.code,'-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("s.description, sr.preferred_date as requestedDate, ");
		sb.append("DATE_FORMAT(sr.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(sr.total_cost,2) as amount, TRUNCATE(sr.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((sr.total_cost - sr.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("service_request sr, service s, ");
		sb.append("job_orders jo ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 2 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = sr.id and ");
		sb.append("s.id = sr.service_id and ");
		sb.append("sr.status = 'Done' and ");
		sb.append("TRUNCATE((sr.total_cost -  IFNULL(sr.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("sr.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, ad.user_id as userId, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat('assocdue-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Association Due') as description, ad.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(ad.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(ad.amount,2) as amount, ");
		sb.append("TRUNCATE(ad.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((ad.amount - ad.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("job_orders as jo, association_due as ad ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 4 ");
		sb.append("and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = ad.id and ");
		sb.append("TRUNCATE((ad.amount -  IFNULL(ad.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ad.created_at desc) "); 
		sb.append("UNION "); 
		sb.append("(select jo.id as id, wr.user_id as userId, ");
		sb.append("jo.request_id as requestId, ");
		sb.append("jo.order_type_id as orderTypeId, ");
		sb.append("concat('waterdue-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Water Consumption') as description, ");
		sb.append("wr.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(wr.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(wr.amount,2) as amount, ");
		sb.append("TRUNCATE(wr.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((wr.amount - wr.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("job_orders jo, water_reading wr ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 3 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = wr.id and ");
		sb.append("TRUNCATE((wr.amount -  IFNULL(wr.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("wr.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, adj.user_id as userId, ");
		sb.append("jo.request_id as requestId, ");
		sb.append("jo.order_type_id as orderTypeId, ");
		sb.append("concat('penaltydue-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Penalty') as description, ");
		sb.append("adj.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(adj.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(adj.amount,2) as amount, ");
		sb.append("TRUNCATE(adj.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((adj.amount - adj.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("job_orders jo, adjustment adj ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 6 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = adj.id and ");
		sb.append("TRUNCATE((adj.amount -  IFNULL(adj.paid_amount,0.0)),2) > 0 ");
		sb.append("order by adj.created_at desc) ");
		
		/*sb.append("(select jo.id as id, ar.user_id as userId, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat(a.code,'-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("a.description, ar.requested_date as requestedDate, ");
		sb.append("DATE_FORMAT(ar.updated_At,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(ar.total_cost,2) as amount, TRUNCATE(ar.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((ar.total_cost - ar.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("amenities_request ar, amenity a, job_orders jo ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 1 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = ar.id and ");
		sb.append("a.id = ar.amenity_id and ");
		sb.append("ar.status = 'Done' and ");
		sb.append("TRUNCATE((ar.total_cost -  IFNULL(ar.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("ar.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, sr.user_id as userId, ");		
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat(s.code,'-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("s.description, sr.preferred_date as requestedDate, ");
		sb.append("DATE_FORMAT(sr.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(sr.total_cost,2) as amount, TRUNCATE(sr.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((sr.total_cost - sr.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("service_request sr, service s, ");
		sb.append("job_orders jo ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 2 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = sr.id and ");
		sb.append("s.id = sr.service_id and ");
		sb.append("sr.status = 'Done' and ");
		sb.append("TRUNCATE((sr.total_cost -  IFNULL(sr.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("sr.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, ad.user_id as userId, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat('assocdue-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Association Due') as description, ad.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(ad.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(ad.amount,2) as amount, TRUNCATE(ad.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((ad.amount - ad.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("job_orders as jo, association_due as ad ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 4 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = ad.id and ");
		sb.append("TRUNCATE((ad.amount -  IFNULL(ad.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("ad.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, wr.user_id as userId, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat('waterdue-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Water Consumption') as description, wr.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(wr.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(wr.amount,2) as amount, TRUNCATE(wr.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((wr.amount - wr.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("job_orders jo, water_reading wr ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 3 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = wr.id and ");
		sb.append("TRUNCATE((wr.amount -  IFNULL(wr.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("wr.created_at desc) ");*/
		
		printSQL(sb.toString(), "getting all the outstanding balance for the user");
		
		return sb.toString();
	}
	
	
	
	/**
	 * Gets the owner monthly bill based on user id, house id, payee name
	 * */
	public static String getOwnerMonthlyBillSQL(int userId, int houseId, String payeeName)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(select jo.id as id, ar.user_id as userId, ");
		sb.append("concat('");
		sb.append(payeeName);
		sb.append("') as payeeName, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat(a.code,'-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("a.description, ar.requested_date as requestedDate, ");
		sb.append("DATE_FORMAT(ar.updated_At,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(ar.total_cost,2) as amount, TRUNCATE(ar.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((ar.total_cost - ar.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("amenities_request ar, amenity a, job_orders jo ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 1 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = ar.id and ");
		sb.append("a.id = ar.amenity_id and ");
		sb.append("ar.status = 'Done' and ");
		sb.append("TRUNCATE((ar.total_cost -  IFNULL(ar.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("ar.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, sr.user_id as userId, ");
		sb.append("concat('");
		sb.append(payeeName);
		sb.append("') as payeeName, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat(s.code,'-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("s.description, sr.preferred_date as requestedDate, ");
		sb.append("DATE_FORMAT(sr.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(sr.total_cost,2) as amount, TRUNCATE(sr.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((sr.total_cost - sr.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("service_request sr, service s, ");
		sb.append("job_orders jo ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 2 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = sr.id and ");
		sb.append("s.id = sr.service_id and ");
		sb.append("sr.status = 'Done' and ");
		sb.append("TRUNCATE((sr.total_cost -  IFNULL(sr.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("sr.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, ad.user_id as userId, ");
		sb.append("concat('");
		sb.append(payeeName);
		sb.append("') as payeeName, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat('assocdue-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Association Due') as description, ad.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(ad.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(ad.amount,2) as amount, TRUNCATE(ad.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((ad.amount - ad.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("job_orders as jo, association_due as ad ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 4 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = ad.id and ");
		sb.append("TRUNCATE((ad.amount -  IFNULL(ad.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("ad.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, wr.user_id as userId, ");
		sb.append("concat('");
		sb.append(payeeName);
		sb.append("') as payeeName, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat('waterdue-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Water Consumption') as description, wr.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(wr.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(wr.amount,2) as amount, TRUNCATE(wr.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((wr.amount - wr.paid_amount),2) as balance ");
		sb.append("from ");
		sb.append("job_orders jo, water_reading wr ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 3 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = wr.id and ");
		sb.append("TRUNCATE((wr.amount -  IFNULL(wr.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("wr.created_at desc) ");
		sb.append("UNION ");
		sb.append("(select jo.id as id, adj.user_id as userId, ");
		sb.append("concat('");
		sb.append(payeeName);
		sb.append("') as payeeName, ");
		sb.append("jo.request_id as requestId, jo.order_type_id as orderTypeId, ");
		sb.append("concat('penalty-',LPAD(ABS(jo.id),8,'0')) as reference, ");
		sb.append("concat('Penalty') as description, adj.posted_date as requestedDate, ");
		sb.append("DATE_FORMAT(adj.updated_at,'%m/%d/%Y') as postedDate, ");
		sb.append("TRUNCATE(adj.amount,2) as amount, TRUNCATE(adj.paid_amount,2) as paidAmount, ");
		sb.append("TRUNCATE((adj.amount - adj.paid_amount),2) as balance ");
		sb.append("from job_orders jo, adjustment adj ");
		sb.append("where ");
		sb.append("jo.user_id = '");
		sb.append(userId);
		sb.append("' and ");
		sb.append("jo.house_id = '");
		sb.append(houseId);
		sb.append("' and ");
		sb.append("jo.order_type_id = 6 and ");
		sb.append("jo.status in (0,1) and ");
		sb.append("jo.request_id = adj.id and ");
		sb.append("TRUNCATE((adj.amount -  IFNULL(adj.paid_amount,0.0)),2) > 0 ");
		sb.append("order by ");
		sb.append("adj.created_at desc) ");
		
		
		printSQL(sb.toString(), " admin getting the owner monthly bill");
		
		return sb.toString();
	}
	
	
	/*
	 * Gets the Users in the form Name - Address for creating request in admin page
	 * */
	public static String getRequestorSQL()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select h.id as id, ");
		sb.append("u.id as userId, ");
		sb.append("Concat (o.firstname, ' ', o.lastname, '-', h.addr_number, ' ', h.addr_street) as requestor ");
        sb.append("from users u, owner o, ");
        sb.append("house h ");
        sb.append("where o.user_id = u.id ");
        sb.append("and u.status = 'A' ");
        sb.append("and h.owner_id = o.id");
		
		/*sb.append("select Concat (h.id, '-', u.id) as id, ");
		sb.append("Concat (o.firstname, ' ', o.lastname, '-', h.addr_number, ' ', h.addr_street) ");
		sb.append("as requestor ");
		sb.append("from users u, owner o, house h "); 
		sb.append("where o.user_id = u.id ");
		sb.append("and u.status = 'A' ");
		sb.append("and h.owner_id = o.id ");*/
        
        printSQL(sb.toString(), "getting the Users when creating request in admin page");
		
		return sb.toString();
	}
	
	
	
	/*
	 * Checks if there is already an existing Amenity Request with same date and time
	 * */
	public static String checkIfExistingAmenityRequestSQL(int userId, int amenityId, String requestedDate, String startTime, String endTime)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select count(*) from amenities_request where user_id = '");
    	sb.append(userId);
    	sb.append("' and amenity_id = '");
    	sb.append(amenityId);
    	sb.append("' and requested_date = '");
    	sb.append(requestedDate);
    	sb.append("' and start_time = '");
    	sb.append(startTime);
    	sb.append("' and end_time = '");
    	sb.append(endTime);
    	sb.append("'");
    	
    	printSQL(sb.toString(), "checking if there is already an existing Amenity Request with same date and time");
		
		return sb.toString();
	}
	
	
	
	
	// ###################################################  SEARCH  ###################################################
	

	/**
	 * 	Search for the Home owner Amenity requests using the criteria "AMENITY" only 
	 * */
	public static String adminSearchAmenityRequestsByAmenityOnlySQL(int amenityId)	
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o ");
		sb.append("where a.id = ar.amenity_id ");
		sb.append("and o.user_id = ar.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and ar.house_id = h.id ");
		sb.append("and ar.amenity_id = ");
		sb.append(amenityId);
		sb.append(" and ar.STATUS in ('Reserved','Booked','New','Change Request') "); 
		sb.append("order by requestedDate asc ");
				
		printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria AMENITY only");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Amenity requests using the criteria "FROM DATE" only
	 * */
	public static String adminSearchAmenityRequestsByFromDateOnlySQL(String fromDate)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o "); 
		sb.append("where a.id = ar.amenity_id ");
		sb.append("and o.user_id = ar.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and ar.house_id = h.id ");
		sb.append("and ar.requested_date >= '");
		sb.append(fromDate);
		sb.append("' and ar.STATUS in ('Reserved','Booked','New','Change Request') "); 
		sb.append("order by requestedDate asc ");
				
		printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria FROM DATE only");
		
		return sb.toString();
	}
	
	
	
	/**
	 * Search for the Home owner Amenity requests using the criteria "FROM DATE" and "TO DATE"
	 * */
	public static String adminSearchAmenityRequestsByFromDateAndToDateSQL(String fromDate, String toDate)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o "); 
		sb.append("where a.id = ar.amenity_id ");
		sb.append("and o.user_id = ar.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and ar.house_id = h.id ");
		sb.append("and (ar.requested_date >= '");
		sb.append(fromDate);
		sb.append("' and ar.requested_date <= '");
		sb.append(toDate);
		sb.append("') and ar.STATUS in ('Reserved','Booked','New','Change Request') "); 
		sb.append("order by requestedDate asc ");
				
		printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria FROM DATE and TO DATE");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Amenity requests using the criteria "AMENITY" and "STATUS"
	 * */
	public static String adminSearchAmenityRequestsByAmenityAndStatusSQL(int amenityId, String status)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o "); 
		sb.append("where a.id = ar.amenity_id ");
		sb.append("and o.user_id = ar.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and ar.house_id = h.id ");
		sb.append("and ar.amenity_id = ");
		sb.append(amenityId);
		sb.append(" and ar.STATUS = '");
		sb.append(status);
		sb.append("' order by requestedDate asc ");
				
		printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria AMENITY and STATUS");
		
		return sb.toString();
	}
	
	
	

	/**
	 * Search for the Home owner Amenity requests using the criteria "STATUS" only
	 * */
	public static String adminSearchAmenityRequestsByStatusOnlySQL(String status)	// with lessee
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(select ar.id as id, a.id as amenityId, ar.house_id as houseId, ");
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("a.description as des, ar.requested_date as requestedDate, ");
		sb.append("ar.START_TIME as startTime, ar.END_TIME as endTime, a.reg_value as basic_cost, ");
		sb.append("ar.additional_cost as additional_cost, ar.total_cost as total_cost, a.uom as uom, ");
		sb.append("ar.STATUS, a.reg_value as regularPrice, a.excess_value as excessPrice, ar.quantity, ");
		sb.append("a.max_regular as maxRegular, ar.remarks as remarks, ar.other_charges as otherCharges, ");
		sb.append("ar.updated_At as updated, ar.user_id ");
		sb.append("from amenity a, amenities_request ar, house h, owner o ");
		sb.append("where a.id = ar.amenity_id and o.user_id = ar.user_id and h.owner_id = o.id and ar.house_id = h.id and ar.STATUS = '");
		sb.append(status);
		sb.append("') ");
		sb.append("union all "); 
		sb.append("(select ar.id as id, a.id as amenityId, ar.house_id as houseId, ");
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(l.firstname, ' ' , l.lastname) as requestor, a.description as des, ");
		sb.append("ar.requested_date as requestedDate, ar.START_TIME as startTime, ar.END_TIME as endTime, ");
		sb.append("a.reg_value as basic_cost, ar.additional_cost as additional_cost, ar.total_cost as total_cost, ");
		sb.append("a.uom as uom, ar.STATUS, a.reg_value as regularPrice, a.excess_value as excessPrice, ar.quantity, ");
		sb.append("a.max_regular as maxRegular, ar.remarks as remarks, ar.other_charges as otherCharges, ar.updated_At as updated, ar.user_id ");
		sb.append("from amenity a, amenities_request ar, house h, leesee l ");
		sb.append("where a.id = ar.amenity_id and l.user_id = ar.user_id and ar.house_id = l.house_id and l.house_id = h.id and ar.STATUS = '");
		sb.append(status);
		sb.append("') order by requestedDate asc ");
		
		printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria STATUS only");
		
		return sb.toString();
	}
	
	
	
	
	/*
	 * Search for the Home owner Amenity requests using the criteria "REQUESTOR" only
	 * */
	public static String adminSearchAmenityRequestsByRequestorOnlySQL(int userId, int houseId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o "); 
		sb.append("where ar.user_id = ");
		sb.append(userId);
		sb.append(" and ar.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and a.id = ar.amenity_id "); 
		// sb.append("and ar.STATUS in ('Reserved','Booked','New','Change Request') ");
		sb.append("order by requestedDate asc"); 

    	printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria REQUESTOR only");
		
		return sb.toString();
	}
	

	/**
	 * 	Search for the Home owner Service requests using the criteria "SERVICE" only 
	 * */
	public static String adminSearchServiceRequestsByServiceOnlySQL(int serviceId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where s.id = sr.service_id ");
		sb.append("and o.user_id = sr.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and sr.house_id = h.id ");
		sb.append("and sr.service_id = ");
		sb.append(serviceId);
		sb.append(" and sr.STATUS in ('Reserved','Booked','New','Change Request') "); 
		sb.append("order by preferredDate asc ");
				
		printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria SERVICE only");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "FROM DATE" only 
	 * */
	public static String adminSearchServiceRequestsByFromDateOnlySQL(String fromDate)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where s.id = sr.service_id ");
		sb.append("and o.user_id = sr.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and sr.house_id = h.id ");
		sb.append("and sr.preferred_date >= '");
		sb.append(fromDate);
		sb.append("' and sr.STATUS in ('Reserved','Booked','New','Change Request') "); 
		sb.append("order by preferredDate asc ");
		
		printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria FROM DATE only");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "FROM DATE" and "TO DATE"
	 * */
	public static String adminSearchServiceRequestsByFromDateAndToDateSQL(String fromDate, String toDate)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where s.id = sr.service_id ");
		sb.append("and o.user_id = sr.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and sr.house_id = h.id ");
		sb.append("and (sr.preferred_date >= '");
		sb.append(fromDate);
		sb.append("' and sr.preferred_date <= '");
		sb.append(toDate);
		sb.append("') and sr.STATUS in ('Reserved','Booked','New','Change Request') "); 
		sb.append("order by preferredDate asc ");
				
		printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria FROM DATE and TO DATE");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "SERVICE" and "STATUS"
	 * */
	public static String adminSearchServiceRequestsByServiceAndStatusSQL(int serviceId, String status)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where s.id = sr.service_id ");
		sb.append("and o.user_id = sr.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and sr.house_id = h.id ");
		sb.append("and sr.service_id = ");
		sb.append(serviceId);
		sb.append(" and sr.STATUS = '");
		sb.append(status);
		sb.append("' order by preferredDate asc ");
				
		printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria SERVICE and STATUS");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "STATUS" only
	 * */
	public static String adminSearchServiceRequestsByStatusOnlySQL(String status)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where s.id = sr.service_id ");
		sb.append("and o.user_id = sr.user_id ");
		sb.append("and h.owner_id = o.id ");
		sb.append("and sr.house_id = h.id ");
		sb.append(" and sr.STATUS = '");
		sb.append(status);
		sb.append("' order by preferredDate asc ");
		
		printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria STATUS only");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "REQUESTOR" and "SERVICE"
	 * */
	public static String adminSearchServiceRequestsByRequestorAndServiceSQL(int userId, int houseId, int serviceId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where sr.user_id = ");
		sb.append(userId);
		sb.append(" and sr.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and sr.service_id = ");
		sb.append(serviceId);
		sb.append(" and s.id = sr.service_id ");
		sb.append("and sr.STATUS in ('Reserved','Booked','New','Change Request') ");
		sb.append("order by preferredDate asc"); 

    	printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria REQUESTOR and SERVICE");
    	
    	return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "REQUESTOR" and "STATUS"
	 * */
	public static String adminSearchServiceRequestsByRequestorAndStatusSQL(int userId, int houseId, String status)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where sr.user_id = ");
		sb.append(userId);
		sb.append(" and sr.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and s.id = sr.service_id "); 
		sb.append("and sr.STATUS = '");
		sb.append(status);
		sb.append("' order by preferredDate asc"); 

    	printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria REQUESTOR and STATUS");
    	
    	return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "REQUESTOR", "SERVICE" and "STATUS"
	 * */
	public static String adminSearchServiceRequestsByRequestorServiceAndStatusSQL(int userId, int houseId, int serviceId, String status)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("\'pc\' as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");	// RACS
		sb.append("where sr.user_id = ");
		sb.append(userId);
		sb.append(" and sr.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and sr.service_id = ");
		sb.append(serviceId); 
		sb.append(" and s.id = sr.service_id ");
		sb.append("and sr.STATUS = '");
		sb.append(status);
		sb.append("' order by preferredDate asc"); 

    	printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria REQUESTOR, SERVICE and STATUS");
    	
    	return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Service requests using the criteria "REQUESTOR" only
	 * */
	public static String adminSearchServiceRequestsByRequestorOnlySQL(int userId, int houseId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select sr.id as id, s.id as serviceId, sr.house_id as houseId, ");
		sb.append("Concat(s.code,'-',LPAD(ABS(sr.id),12,'0')) as requestId, ");
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, ");
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, ");
		sb.append("s.description as des, sr.preferred_date as preferredDate, ");
		sb.append("sr.preferred_time as preferredTime, ");
		sb.append("sr.confirmed_date as confirmedDate, ");
		sb.append("sr.STATUS as status, ");
		sb.append("s.reg_value as basic_cost, ");
		sb.append("sr.additional_cost as additional_cost, "); 
		sb.append("sr.total_cost as total_cost, ");
		sb.append("s.uom as uom, ");
		sb.append("s.reg_value as regularPrice, "); 
		sb.append("s.excess_value as excessPrice, ");
		sb.append("sr.quantity, ");
		sb.append("s.max_regular as maxRegular, "); 
		sb.append("sr.remarks as remarks, ");
		sb.append("sr.other_charges as otherCharges, ");
		sb.append("sr.updated_At as updated, ");
		sb.append("sr.user_id ");
		sb.append("from service s, service_request sr, house h, owner o ");
		sb.append("where sr.user_id = ");
		sb.append(userId);
		sb.append(" and sr.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and s.id = sr.service_id ");
		sb.append("order by preferredDate asc ");

    	printSQL(sb.toString(), "searching for the Home owner Service requests using the criteria REQUESTOR only");
		
		return sb.toString();
	}
	
	
	/**
	 * Search for the Home owner Amenity requests using the criteria "REQUESTOR" and "AMENITY"
	 * */
	public static String adminSearchAmenityRequestsByRequestorAndAmenitySQL(int userId, int houseId, int amenityId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o ");
		sb.append("where ar.user_id = ");
		sb.append(userId);
		sb.append(" and ar.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and ar.amenity_id = ");
		sb.append(amenityId);
		sb.append(" and a.id = ar.amenity_id ");
		sb.append("and ar.STATUS in ('Reserved','Booked','New','Change Request') ");
		sb.append("order by requestedDate asc"); 

    	printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria REQUESTOR and AMENITY");
		
		return sb.toString();
	}
	
	

	/**
	 * Search for the Home owner Amenity requests using the criteria "REQUESTOR" and "STATUS"
	 * */
	public static String adminSearchAmenityRequestsByRequestorAndStatusSQL(int userId, int houseId, String status)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o ");
		sb.append("where ar.user_id = ");
		sb.append(userId);
		sb.append(" and ar.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and a.id = ar.amenity_id "); 
		sb.append("and ar.STATUS = '");
		sb.append(status);
		sb.append("' order by requestedDate asc"); 

    	printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria REQUESTOR and STATUS");
		
		return sb.toString();
	}
	

	/**
	 * Search for the Home owner Amenity requests using the criteria "REQUESTOR", "AMENITY" and "STATUS"
	 * */
	public static String adminSearchAmenityRequestsByRequestorAmenityAndStatusSQL(int userId, int houseId, int amenityId, String status)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ar.id as id, a.id as amenityId, ar.house_id as houseId, "); 
		sb.append("Concat(a.code,'-',LPAD(ABS(ar.id),12,'0')) as requestId, "); 
		sb.append("Concat(h.addr_number, ' ', h.addr_street) as address, "); 
		sb.append("Concat(o.firstname, ' ' , o.lastname) as requestor, "); 
		sb.append("a.description as des, ar.requested_date as requestedDate, ar.START_TIME as startTime, "); 
		sb.append("ar.END_TIME as endTime, "); 
		sb.append("a.reg_value as basic_cost, "); 
		sb.append("ar.additional_cost as additional_cost, ");  
		sb.append("ar.total_cost as total_cost, a.uom as uom, "); 
		sb.append("ar.STATUS, "); 
		sb.append("a.reg_value as regularPrice, ");  
		sb.append("a.excess_value as excessPrice, "); 
		sb.append("ar.quantity, "); 
		sb.append("a.max_regular as maxRegular, ");  
		sb.append("ar.remarks as remarks, "); 
		sb.append("ar.other_charges as otherCharges, ");  
		sb.append("ar.updated_At as updated, "); 
		sb.append("ar.user_id "); 
		sb.append("from amenity a, amenities_request ar, house h, owner o "); 	// ECY
		sb.append("where ar.user_id = ");
		sb.append(userId);
		sb.append(" and ar.house_id = ");
		sb.append(houseId);
		sb.append(" and o.user_id = ");
		sb.append(userId);
		sb.append(" and h.id = ");
		sb.append(houseId);
		sb.append(" and ar.amenity_id = ");
		sb.append(amenityId); 
		sb.append(" and a.id = ar.amenity_id ");
		sb.append("and ar.STATUS = '");
		sb.append(status);
		sb.append("' order by requestedDate asc"); 

    	printSQL(sb.toString(), "searching for the Home owner Amenity requests using the criteria REQUESTOR, AMENITY and STATUS");
		
		return sb.toString();
	}
	
	
	
	
	public static List<String> parseSQLScalarProperties(String scalarPropertyKey)
	{
		List<String> list = null;
		String[] properties = null;
		
		if(ValidationUtil.hasValue(scalarPropertyKey))
		{
			properties = scalarPropertyKey.split(FerndaleDefines.SCALAR_PROP_DELIMITER);
			
			if (properties != null && properties.length > FerndaleDefines.ZERO)
			{
				list = new ArrayList<String>();
				
				for (String prop : properties)
				{
					list.add(prop);
				}
			}
		}
		
		return list;
	}
	
	
	
	
	public static Query buildSQLQuery(Session session, List<String> scalarPropertyList, String sql)
	{
		SQLQuery query = null;
		
		if (session != null)
		{
			query = session.createSQLQuery(sql);
			
			if (scalarPropertyList != null && scalarPropertyList.size() > FerndaleDefines.ZERO)
			{
				for (String scalarProp : scalarPropertyList)
				{
					query = query.addScalar(scalarProp);
				}
			}
		}
	
		return query;
	}
	
	
	
	/*
	 * Gets the SQL to update AssociationDue billing_month
	 * */
	public static String getUpdateAssociationDueBillingMonthSQL(String billingMonth)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("update association_due set billing_month = '");
		sb.append(billingMonth);
		sb.append("', posted_date = now() ");
		sb.append("where billing_month is null");
		
    	printSQL(sb.toString(), "updating AssociationDue billing_month");
		
		return sb.toString();
	}
	
	
	
	/*
	 * Gets the SQL to update WaterReading billing_month
	 * */
	public static String getUpdateWaterReadingBillingMonthSQL(String billingMonth)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("update water_reading set billing_month = '");
		sb.append(billingMonth);
		sb.append("', posted_date = now() ");
		sb.append("where billing_month is null");
		
    	printSQL(sb.toString(), "updating WaterReading billing_month");
		
		return sb.toString();
	}

	
	/*
	 * Gets the SQL to update WaterReading billing_month
	 * */
	public static String getUserMonthlyStatementList(int userId, int houseId)
	{
		StringBuilder sb = new StringBuilder();
		
		// IMPORTANT!! to follow na yung sql na may house id
		
		sb.append("(select sh.id, Concat(o.firstname, o.lastname) as description, h.id as houseId, sh.report_name as reportName, ");
		sb.append(" sh.report_name as postedDate from owner o join users u on u.id = o.user_id ");
		sb.append(" join statement_history sh on sh.user_id = o.user_id join house h on h.owner_id = o.id ");
		sb.append(" where o.user_id = ");
		sb.append(userId);
		sb.append(" order by sh.report_date DESC Limit 6) ");
		sb.append(" UNION ");
		sb.append("(select sh.id, Concat(o.firstname, o.lastname) as description, h.id as houseId, sh.report_name as reportName, ");
		sb.append(" sh.report_name as postedDate from leesee o join users u on u.id = o.user_id ");
		sb.append(" join statement_history sh on sh.user_id = o.user_id join house h on h.id = o.house_id ");
		sb.append(" where o.user_id = ");
		sb.append(userId);
		sb.append(" order by sh.report_date DESC Limit 6)"); // last six months is displayed);
		
		printSQL(sb.toString(), "getting list of monthly statement");
		
		return sb.toString();
	}
	
	
	

	/**
	 *  Gets the SQL to update the amount paid for AssociationDue when admin is paying bills for a home owner
	 * */
	public static String getUpdateAssociationDuePaidAmountSQL(double paidAmount, int requestId, double amount)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("update association_due ad set ad.paid_amount = ");
		sb.append(paidAmount);
		sb.append(", ad.updated_at = now()");
		
		if(amount == 0)
		{
			sb.append(", ad.status = 'Paid' ");
		}
		
		sb.append(" where ad.id = ");
		sb.append(requestId);
		       
		printSQL(sb.toString(), "updating AssociationDue paid_amount");
		
		return sb.toString();
	}
	
	
	/**
	 * Gets the association due for a specific home owner
	 * */
	public static String getAssocDueByUserIdHouseIdSQL(int userId, int houseId)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select * from association_due ad ");
		sb.append("where ad.user_id = ");
		sb.append(userId);
		sb.append(" and ad.house_id = ");
		sb.append(houseId);
		
		printSQL(sb.toString(), "getting the association due for a specific home owner");
		
		return sb.toString();
	}
	
	/**
	 *  Gets the SQL to update the amount paid for WaterReading when admin is paying bills for a home owner
	 * */
	public static String getUpdateWaterReadingPaidAmountSQL(double paidAmount, int requestId, double amount)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("update water_reading wr set wr.paid_amount = ");
		sb.append(paidAmount);
		sb.append(", wr.updated_at = now()");
		
		if(amount == 0)
		{
			sb.append(", wr.status = 'Paid' ");
		}
		
		sb.append("where wr.id = ");
		sb.append(requestId);
		       
		printSQL(sb.toString(), "updating Water Reading paid_amount");
		
		return sb.toString();
	}
	
	
	/**
	 *  Computes the Total Amenity Fee via Ajax during creation of Amenity requests
	 *  
	 *  @param amenityId
	 *  @param hoursOrPcs
	 * */
	public static String getComputeAmenityFeeSQL(int amenityId, int hoursOrPcs)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select computeAmenityFee(");
		sb.append(amenityId);
		sb.append(",");
		sb.append(hoursOrPcs);
		sb.append(")");
		
		printSQL(sb.toString(), "computing for Amenity Fee");
		
		return sb.toString();
	}

	
	/**
	 *  Computes the Total Service Fee via Ajax during creation of Service requests
	 *  
	 *  @param serviceId
	 *  @param hoursOrPcs
	 * */
	public static String getComputeServiceFeeSQL(int serviceId, int hoursOrPcs)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select computeServiceFee(");
		sb.append(serviceId);
		sb.append(",");
		sb.append(hoursOrPcs);
		sb.append(")");
		
		printSQL(sb.toString(), "computing for Service Fee");
		
		return sb.toString();
	}
	
	
	/**
	 *  Checks if there is a Date or Time overlap when creating a new Amenity request
	 *  
	 *  @param amenityId
	 *  @param date
	 *  @param startTime
	 *  @param endTime
	 * */
	public static String getCheckAmenityRequestSQL(int amenityId, String date, String start_time, String end_time)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select checkAmenityRequest(");
		sb.append(amenityId);
		sb.append(",'");
		sb.append(date);
		sb.append("','");
		sb.append(start_time);
		sb.append("','");
		sb.append(end_time);
		sb.append("')");
		
		printSQL(sb.toString(), "checking for Date or Time overlap when creating a new amenity request");
		
		return sb.toString();
	}
	
	
	
	/**
	 *  Checks if there is a Service Request or Date overlap when creating a new Service request
	 *  
	 *  @param serviceReqId
	 *  @param date
	 * */
	public static String getCheckServiceRequestSQL(int serviceReqId, String date)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("select checkServiceRequest(");
		sb.append(serviceReqId);
		sb.append(",'");
		sb.append(date);
		sb.append("')");
		
		printSQL(sb.toString(), "checking for Service Request and Date overlap when creating a new Service Request");
		
		return sb.toString();
	}
	
	
	
	
	
	public static void printSQL(String sql, String label)
	{
		logger.info("\n===============================   SQL QUERY for " + label.toUpperCase() + "   ===================================== \n" + sql
				+ "\n============================================================================================================================================================= \n");
	}
	

}

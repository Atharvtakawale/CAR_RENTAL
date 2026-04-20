package com.epps.carrental.matsers.price.dto;

import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

public class PriceMasterQuerydto {

	private Integer price_id;

	private VEHICLE_TYPE vehicle_type;
	
	private Boolean is_active;
	
	private Integer location_id;
	
    private Integer pageNo;
    
    private Integer pageSize;

    private String sortBy;
    
    private String sortDirection;

	public Integer getPrice_id() {
		return price_id;
	}

	public void setPrice_id(Integer price_id) {
		this.price_id = price_id;
	}

	public VEHICLE_TYPE getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(VEHICLE_TYPE vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public Integer getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	} 
}

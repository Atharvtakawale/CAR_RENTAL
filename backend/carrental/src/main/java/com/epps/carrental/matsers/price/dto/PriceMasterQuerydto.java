package com.epps.carrental.matsers.price.dto;

import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

public class PriceMasterQuerydto {

	private Integer priceId;

	private VEHICLE_TYPE vehicleType;
	
	private Boolean isActive;
	
	private Integer locationId;
	
    private Integer pageNo;
    
    private Integer pageSize;

    private String sortBy;
    
    private String sortDirection;

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

	public Integer getPriceId() {
		return priceId;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	public VEHICLE_TYPE getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VEHICLE_TYPE vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	} 
}

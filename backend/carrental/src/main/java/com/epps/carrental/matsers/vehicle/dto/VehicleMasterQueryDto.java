package com.epps.carrental.matsers.vehicle.dto;

import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class VehicleMasterQueryDto {

	private Integer vehicleId;

	private String model;
	
	@Enumerated(EnumType.STRING)
	private VEHICLE_TYPE vehicleType;
	
    private Integer pageNo;
    
    private Integer pageSize;

    private String sortBy;
    
    private String sortDirection; 

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public VEHICLE_TYPE getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VEHICLE_TYPE vehicleType) {
		this.vehicleType = vehicleType;
	}
}

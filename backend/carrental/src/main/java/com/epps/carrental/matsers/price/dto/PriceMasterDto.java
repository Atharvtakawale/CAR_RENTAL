package com.epps.carrental.matsers.price.dto;

import java.time.LocalDate;

import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

public class PriceMasterDto {

	private Integer priceId;

	private VEHICLE_TYPE vehicleType;

	private Double basePricePerDay;

	private Double pricePerHour;

	private Double weekendPrice;

	private Double holidayPrice;

	private Double securityDeposit;

	private Boolean isActive;

	private LocalDate createdDate;

	private LocalDate updatedDate;
	
	private Integer locationId;

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

	public Double getBasePricePerDay() {
		return basePricePerDay;
	}

	public void setBasePricePerDay(Double basePricePerDay) {
		this.basePricePerDay = basePricePerDay;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getWeekendPrice() {
		return weekendPrice;
	}

	public void setWeekendPrice(Double weekendPrice) {
		this.weekendPrice = weekendPrice;
	}

	public Double getHolidayPrice() {
		return holidayPrice;
	}

	public void setHolidayPrice(Double holidayPrice) {
		this.holidayPrice = holidayPrice;
	}

	public Double getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(Double securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	} 
	
	
}

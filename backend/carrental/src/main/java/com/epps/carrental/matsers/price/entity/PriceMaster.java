package com.epps.carrental.matsers.price.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.carrental.matsers.location.entity.LocationMaster;
import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "price_mst")
public class PriceMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "price_id")
	private Integer priceId;

	@Column(name = "vehicle_type")
	private VEHICLE_TYPE vehicleType;

	@Column(name = "base_price_per_day")
	private Double basePricePerDay;

	@Column(name = "price_per_hour")
	private Double pricePerHour;

	@Column(name = "weekend_price")
	private Double weekendPrice;

	@Column(name = "holiday_price")
	private Double holidayPrice;

	@Column(name = "security_deposit")
	private Double securityDeposit;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "updated_date")
	private LocalDate updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private LocationMaster locationMaster;

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

	public LocationMaster getLocationMaster() {
		return locationMaster;
	}

	public void setLocationMaster(LocationMaster locationMaster) {
		this.locationMaster = locationMaster;
	}
		
	
}
	
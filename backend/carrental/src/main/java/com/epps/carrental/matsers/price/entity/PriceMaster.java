package com.epps.carrental.matsers.price.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.carrental.matsers.location.entity.LocationMaster;
import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

import jakarta.persistence.Entity;
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
	private Integer price_id;

	private VEHICLE_TYPE vehicle_type; 

	private Double base_price_per_day;

	private Double price_per_hour;

	private Double weekend_price;

	private Double holiday_price;

	private Double security_deposit;

	private Boolean is_active;
	
	private LocalDate created_date;

	private LocalDate updated_date;
	
	@ManyToOne	
    @JoinColumn(name = "location_id")
	private LocationMaster locationMaster;

	public Integer getPrice_id() {
		return price_id;
	}

	public void setPrice_id(Integer price_id) {
		this.price_id = price_id;
	}

	public Double getBase_price_per_day() {
		return base_price_per_day;
	}

	public void setBase_price_per_day(Double base_price_per_day) {
		this.base_price_per_day = base_price_per_day;
	}

	public Double getPrice_per_hour() {
		return price_per_hour;
	}

	public void setPrice_per_hour(Double price_per_hour) {
		this.price_per_hour = price_per_hour;
	}

	public Double getWeekend_price() {
		return weekend_price;
	}

	public void setWeekend_price(Double weekend_price) {
		this.weekend_price = weekend_price;
	}

	public Double getHoliday_price() {
		return holiday_price;
	}

	public void setHoliday_price(Double holiday_price) {
		this.holiday_price = holiday_price;
	}

	public Double getSecurity_deposit() {
		return security_deposit;
	}

	public void setSecurity_deposit(Double security_deposit) {
		this.security_deposit = security_deposit;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public LocalDate getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}

	public LocalDate getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(LocalDate updated_date) {
		this.updated_date = updated_date;
	}

	public LocationMaster getLocationMaster() {
		return locationMaster;
	}

	public void setLocationMaster(LocationMaster locationMaster) {
		this.locationMaster = locationMaster;
	}

	public VEHICLE_TYPE getVehicle_type() {
		return vehicle_type;
	}
	
	public void setVehicle_type(VEHICLE_TYPE vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
}
	
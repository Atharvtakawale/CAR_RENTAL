package com.epps.carrental.matsers.vehicle.dto;

import java.time.LocalDate;

import com.epps.carrental.matsers.vehicle.entity.FUEL_TYPE;
import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

public class VehicleMasterDto {

	private Integer vehicle_id;

	private String model;

	private VEHICLE_TYPE type;

	private FUEL_TYPE fuel_TYPE;
	
	private Integer seating_capacity;
	
	private LocalDate created_date;
	
	private LocalDate updated_date;

	public Integer getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Integer vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public VEHICLE_TYPE getType() {
		return type;
	}

	public void setType(VEHICLE_TYPE type) {
		this.type = type;
	}

	public FUEL_TYPE getFuel_TYPE() {
		return fuel_TYPE;
	}

	public void setFuel_TYPE(FUEL_TYPE fuel_TYPE) {
		this.fuel_TYPE = fuel_TYPE;
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

	public Integer getSeating_capacity() {
		return seating_capacity;
	}

	public void setSeating_capacity(Integer seating_capacity) {
		this.seating_capacity = seating_capacity;
	}

}

package com.epps.carrental.matsers.vehicle.dto;

import java.time.LocalDate;

import com.epps.carrental.matsers.vehicle.entity.FUEL_TYPE;
import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class VehicleMasterDto {
	
	private Integer vehicleId;

	private String model;

	@Enumerated(EnumType.STRING)
	private VEHICLE_TYPE vehicleType;

	@Enumerated(EnumType.STRING)
	private FUEL_TYPE fuelType;

	private Integer seatingCapacity;

	private LocalDate createdDate;

	private LocalDate updatedDate;

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public VEHICLE_TYPE getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VEHICLE_TYPE vehicleType) {
		this.vehicleType = vehicleType;
	}

	public FUEL_TYPE getFuelType() {
		return fuelType;
	}

	public void setFuelType(FUEL_TYPE fuelType) {
		this.fuelType = fuelType;
	}

	public Integer getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(Integer seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
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

}

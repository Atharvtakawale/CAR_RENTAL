package com.epps.carrental.matsers.vehicle.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "vehicle_mst")
public class VehicleMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private Integer vehicleId;

	@Column(name = "model")
	private String model;

	@Column(name = "vehicle_type")
	@Enumerated(EnumType.STRING)
	private VEHICLE_TYPE vehicleType;

	@Column(name = "fuel_type")
	@Enumerated(EnumType.STRING)
	private FUEL_TYPE fuelType;

	@Column(name = "seating_capacity")
	private Integer seatingCapacity;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "updated_date")
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

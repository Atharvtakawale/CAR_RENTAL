package com.epps.carrental.matsers.price.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epps.carrental.matsers.location.entity.LocationMaster;
import com.epps.carrental.matsers.price.entity.PriceMaster;
import com.epps.carrental.matsers.vehicle.entity.VEHICLE_TYPE;

@Repository
public interface PriceMasterDao extends JpaRepository<PriceMaster, Integer>{

	PriceMaster findByVehicleTypeAndLocationMaster(VEHICLE_TYPE vehicleType, LocationMaster pickup);

	
}

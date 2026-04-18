package com.epps.carrental.matsers.vehicle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epps.carrental.matsers.vehicle.entity.VehicleMaster;

@Repository
public interface VehicleMasterDao extends JpaRepository<VehicleMaster, Integer>{

}

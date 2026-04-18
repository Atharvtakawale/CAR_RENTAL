package com.epps.carrental.matsers.location.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epps.carrental.matsers.location.entity.LocationMaster;

@Repository
public interface LocationMasterDao extends JpaRepository<LocationMaster, Integer>{

	
}

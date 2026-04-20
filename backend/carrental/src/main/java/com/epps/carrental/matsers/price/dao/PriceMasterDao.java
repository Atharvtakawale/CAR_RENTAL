package com.epps.carrental.matsers.price.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epps.carrental.matsers.price.entity.PriceMaster;

@Repository
public interface PriceMasterDao extends JpaRepository<PriceMaster, Integer>{

	
}

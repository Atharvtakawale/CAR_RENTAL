package com.epps.carrental.matsers.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epps.carrental.matsers.user.entity.UserMaster;

@Repository
public interface UserMasterDao extends JpaRepository<UserMaster, Integer>{

	Optional<UserMaster> findByEmail(String email);

}

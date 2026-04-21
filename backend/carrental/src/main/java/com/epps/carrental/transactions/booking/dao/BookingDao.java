package com.epps.carrental.transactions.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epps.carrental.transactions.booking.entity.Booking;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer>{

}

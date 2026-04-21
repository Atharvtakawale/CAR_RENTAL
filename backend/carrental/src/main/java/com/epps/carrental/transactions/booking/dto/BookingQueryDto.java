package com.epps.carrental.transactions.booking.dto;

import java.time.LocalDate;

import com.epps.carrental.transactions.booking.entity.BOOKING_STATUS;

public class BookingQueryDto {

	private Integer bookingId;
	private Integer userId;
	private Integer vehicleId;
	private Integer pickupLocationId;
	private Integer dropLocationId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private BOOKING_STATUS bookingStatus;
	private Integer pageNo;
	private Integer pageSize;
	private String sortBy;
	private String sortDirection;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getPickupLocationId() {
		return pickupLocationId;
	}

	public void setPickupLocationId(Integer pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}

	public Integer getDropLocationId() {
		return dropLocationId;
	}

	public void setDropLocationId(Integer dropLocationId) {
		this.dropLocationId = dropLocationId;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public BOOKING_STATUS getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BOOKING_STATUS bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

}

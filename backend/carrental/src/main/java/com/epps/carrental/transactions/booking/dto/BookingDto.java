package com.epps.carrental.transactions.booking.dto;

import java.time.LocalDate;

import com.epps.carrental.transactions.booking.entity.BOOKING_STATUS;
import com.epps.carrental.transactions.booking.entity.PAYMENT_STATUS;

public class BookingDto {

	    private Integer bookingId;
	    private Integer userId;
	    private Integer vehicleId;
	    private Integer pickupLocationId;
	    private Integer dropLocationId;
	    private LocalDate pickupDate;
	    private LocalDate dropDate;
	    private Integer totalDays;
	    private Double baseAmount;
	    private Double extraCharges;
	    private Double discountAmount;
	    private Double totalAmount;
	    private BOOKING_STATUS bookingStatus;
	    private PAYMENT_STATUS paymentStatus;
	    private LocalDate createdDate;
	    private LocalDate updatedDate;
	    
	    
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
		public LocalDate getPickupDate() {
			return pickupDate;
		}
		public void setPickupDate(LocalDate pickupDate) {
			this.pickupDate = pickupDate;
		}
		public LocalDate getDropDate() {
			return dropDate;
		}
		public void setDropDate(LocalDate dropDate) {
			this.dropDate = dropDate;
		}
		public Integer getTotalDays() {
			return totalDays;
		}
		public void setTotalDays(Integer totalDays) {
			this.totalDays = totalDays;
		}
		public Double getBaseAmount() {
			return baseAmount;
		}
		public void setBaseAmount(Double baseAmount) {
			this.baseAmount = baseAmount;
		}
		public Double getExtraCharges() {
			return extraCharges;
		}
		public void setExtraCharges(Double extraCharges) {
			this.extraCharges = extraCharges;
		}
		public Double getDiscountAmount() {
			return discountAmount;
		}
		public void setDiscountAmount(Double discountAmount) {
			this.discountAmount = discountAmount;
		}
		public Double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public BOOKING_STATUS getBookingStatus() {
			return bookingStatus;
		}
		public void setBookingStatus(BOOKING_STATUS bookingStatus) {
			this.bookingStatus = bookingStatus;
		}
		public PAYMENT_STATUS getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(PAYMENT_STATUS paymentStatus) {
			this.paymentStatus = paymentStatus;
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

package com.epps.carrental.transactions.booking.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.carrental.matsers.location.entity.LocationMaster;
import com.epps.carrental.matsers.user.entity.UserMaster;
import com.epps.carrental.matsers.vehicle.entity.VehicleMaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "booking_transaction")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne(fetch = FetchType.LAZY)	
    @JoinColumn(name = "user_id", nullable = false)
    private UserMaster user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleMaster vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_location_id", nullable = false)
    private LocationMaster pickupLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drop_location_id", nullable = false)
    private LocationMaster dropLocation;

    @Column(nullable = false , name = "pickup_date")
    private LocalDate pickupDate;

    @Column(nullable = false, name = "drop_date")
    private LocalDate dropDate;

    @Column(name = "total_days")
    private Integer totalDays;
    
    @Column(name = "base_amount")
    private Double baseAmount;
    
    @Column(name = "extra_charges")
    private Double extraCharges;
    
    @Column(name = "discount_amount")
    private Double discountAmount;
    
    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BOOKING_STATUS bookingStatus;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PAYMENT_STATUS paymentStatus;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    public Booking() {
    }

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public UserMaster getUser() {
		return user;
	}

	public void setUser(UserMaster user) {
		this.user = user;
	}

	public VehicleMaster getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleMaster vehicle) {
		this.vehicle = vehicle;
	}

	public LocationMaster getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(LocationMaster pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public LocationMaster getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(LocationMaster dropLocation) {
		this.dropLocation = dropLocation;
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

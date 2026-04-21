package com.epps.carrental.matsers.location.dto;

import java.time.LocalDate;
import java.util.List;

import com.epps.carrental.matsers.price.dto.PriceMasterDto;
	
public class LocationMasterDto {

	private Integer locationId;

	private String locationName;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String state;

	private String country;

	private String pincode;

	private Integer isActive;

	private LocalDate createdDate;

	private LocalDate updatedDate;

	private List<PriceMasterDto> priceMastersDtos;

	public List<PriceMasterDto> getPriceMastersDtos() {
		return priceMastersDtos;
	}

	public void setPriceMastersDtos(List<PriceMasterDto> priceMastersDtos) {
		this.priceMastersDtos = priceMastersDtos;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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

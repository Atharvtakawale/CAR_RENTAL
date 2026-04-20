package com.epps.carrental.matsers.location.dto;

import java.time.LocalDate;
import java.util.List;

import com.epps.carrental.matsers.price.dto.PriceMasterDto;
import com.epps.carrental.matsers.price.entity.PriceMaster;

public class LocationMasterDto {

	private Integer location_id;
	
	private String location_name;
	
	private String address_line1;
	
	private String address_line2;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String pincode;
	
	private Integer is_active;
	
	private LocalDate created_date;
	
	private LocalDate updated_date;
	
	private List<PriceMasterDto> priceMastersDtos;

	public List<PriceMasterDto> getPriceMastersDtos() {
		return priceMastersDtos;
	}

	public void setPriceMastersDtos(List<PriceMasterDto> priceMastersDtos) {
		this.priceMastersDtos = priceMastersDtos;
	}

	public Integer getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
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

	public Integer getIs_active() {
		return is_active;
	}

	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
	}

	public LocalDate getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}

	public LocalDate getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(LocalDate updated_date) {
		this.updated_date = updated_date;
	}
}

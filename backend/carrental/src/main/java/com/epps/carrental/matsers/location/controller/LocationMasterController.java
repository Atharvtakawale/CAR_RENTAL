package com.epps.carrental.matsers.location.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epps.carrental.matsers.location.dto.LocationMasterDto;
import com.epps.carrental.matsers.location.dto.LocationMasterQueryDto;
import com.epps.carrental.matsers.location.service.LocationMasterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Location Master API", description = "Operations related to Location Management")
@Validated
public class LocationMasterController {

	@Autowired
	private LocationMasterService locationMasterService;
	
	@PostMapping("/save")
	@Operation(summary = "Save Location Master", description = "This API is used to save list of location into the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Locations saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<LocationMasterDto>> saveUserMaster(@Valid @RequestBody List<LocationMasterDto> locationMasterDtos) {

		List<LocationMasterDto> dtos = locationMasterService.saveLocationMaster(locationMasterDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/fetch")
	@Operation(summary = "Fetch Location Master List", description = "Returns list of locations based on query")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Successfully fetched location list"),

			@ApiResponse(responseCode = "400", description = "Invalid request data"),

			@ApiResponse(responseCode = "404", description = "No locations found"),

			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<LocationMasterDto>> fetchLocationMaster(@Valid @RequestBody LocationMasterQueryDto masterQueryDto) {

		List<LocationMasterDto> locationMasterDtos = new ArrayList<LocationMasterDto>();
		if (masterQueryDto != null) {
			long count = locationMasterService.getLocationMasterDataCount(masterQueryDto);

			if (count != 0) {
				locationMasterDtos = locationMasterService.getLocationMasterDataList(masterQueryDto);
			}
		}
		return new ResponseEntity<>(locationMasterDtos, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/update")
	@Operation(summary = "Update Location Master", description = "This API is used to update list of locations into the system")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "202", description = "Locations updated successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input data"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public ResponseEntity<List<LocationMasterDto>> updateLocationMaster(@Valid @RequestBody List<LocationMasterDto> locationMasterDtos)
	{
		List<LocationMasterDto> dtos = locationMasterService.updateLocationMaster(locationMasterDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
}

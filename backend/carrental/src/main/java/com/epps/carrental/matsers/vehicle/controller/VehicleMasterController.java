package com.epps.carrental.matsers.vehicle.controller;

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

import com.epps.carrental.matsers.vehicle.dto.VehicleMasterDto;
import com.epps.carrental.matsers.vehicle.dto.VehicleMasterQueryDto;
import com.epps.carrental.matsers.vehicle.service.VehicleMasterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehicle")
@Tag(name = "Vehicle Master API", description = "Operations related to Vehicle Management")
@Validated
public class VehicleMasterController {
	
	@Autowired
	private VehicleMasterService vehicleMasterService;

	@PostMapping("/save")
	@Operation(summary = "Save Vehicle Master", description = "This API is used to save list of vehicle into the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Vehicle saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<VehicleMasterDto>> saveVehicleMaster(@Valid @RequestBody List<VehicleMasterDto> vehicleMasterDtos) {

		List<VehicleMasterDto> dtos = vehicleMasterService.saveVehicleMaster(vehicleMasterDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}

	@PostMapping("/fetch")
	@Operation(summary = "Fetch User Master List", description = "Returns list of users based on query")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Successfully fetched user list"),

			@ApiResponse(responseCode = "400", description = "Invalid request data"),

			@ApiResponse(responseCode = "404", description = "No users found"),

			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<VehicleMasterDto>> fetchUserMaster(@Valid @RequestBody VehicleMasterQueryDto masterQueryDto) {

		List<VehicleMasterDto> vehicleMasterDtos = new ArrayList<VehicleMasterDto>();
		if(masterQueryDto != null)
		{
			long count = vehicleMasterService.getVehicleMasterDataCount(masterQueryDto);
			
			if(count != 0)
			{
				vehicleMasterDtos =  vehicleMasterService.getVehicleMasterDataList(masterQueryDto);
			}
		}
		return new ResponseEntity<>(vehicleMasterDtos, HttpStatus.ACCEPTED);

	}

	@PostMapping("/update")
	@Operation(summary = "Update User Master", description = "This API is used to update list of users into the system")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "202", description = "Users updated successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input data"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public ResponseEntity<List<VehicleMasterDto>> updateUserMaster(@Valid @RequestBody List<VehicleMasterDto> vehicleMasterDtos)
	{
		List<VehicleMasterDto> dtos = vehicleMasterService.updateVehicleMaster(vehicleMasterDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
}

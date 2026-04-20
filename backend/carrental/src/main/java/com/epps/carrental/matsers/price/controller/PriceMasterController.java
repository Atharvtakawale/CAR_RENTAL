package com.epps.carrental.matsers.price.controller;

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

import com.epps.carrental.matsers.price.dto.PriceMasterDto;
import com.epps.carrental.matsers.price.dto.PriceMasterQuerydto;
import com.epps.carrental.matsers.price.service.PriceMasterService;
import com.epps.carrental.matsers.user.dto.UserMasterDto;
import com.epps.carrental.matsers.user.dto.UserMasterQueryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/price")
@Tag(name = "Price Master API", description = "Operations related to Price Management")
@Validated
public class PriceMasterController {

	@Autowired
	private PriceMasterService priceMasterService;
	
	@PostMapping("/save")
	@Operation(summary = "Save Price Master", description = "This API is used to save list of price into the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "price saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<PriceMasterDto>> saveUserMaster(@Valid @RequestBody List<PriceMasterDto> PriceMasterDto) {

		List<PriceMasterDto> dtos = priceMasterService.savePriceMaster(PriceMasterDto);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/fetch")
	@Operation(summary = "Fetch Price Master List", description = "Returns list of Price based on query")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Successfully fetched price list"),

			@ApiResponse(responseCode = "400", description = "Invalid request data"),

			@ApiResponse(responseCode = "404", description = "No users found"),

			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<PriceMasterDto>> fetchUserMaster(@Valid @RequestBody PriceMasterQuerydto masterQueryDto) {

		List<PriceMasterDto> priceMasterDtos = new ArrayList<PriceMasterDto>();
		if (masterQueryDto != null) {
			long count = priceMasterService.getPriceMasterDataCount(masterQueryDto);

			if (count != 0) {
				priceMasterDtos = priceMasterService.getPriceMasterDataList(masterQueryDto);
			}
		}
		return new ResponseEntity<>(priceMasterDtos, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/update")
	@Operation(summary = "Update Price Master", description = "This API is used to update list of price into the system")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "202", description = "Price updated successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input data"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public ResponseEntity<List<PriceMasterDto>> updateUserMaster(@Valid @RequestBody List<PriceMasterDto> priceMasterDtos)
	{
		List<PriceMasterDto> dtos = priceMasterService.updatePriceMaster(priceMasterDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
}

package com.epps.carrental.matsers.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epps.carrental.matsers.user.dto.UserMasterDto;
import com.epps.carrental.matsers.user.dto.UserMasterQueryDto;
import com.epps.carrental.matsers.user.service.UserMasterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Master API", description = "Operations related to User Management")
@Validated
public class UserMasterRestController {

	private final UserMasterService userMasterService;

	public UserMasterRestController(UserMasterService userMasterService) {
		this.userMasterService = userMasterService;
	}

	@PostMapping("/save")
	@Operation(summary = "Save User Master", description = "This API is used to save list of users into the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Users saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<UserMasterDto>> saveUserMaster(@Valid @RequestBody List<UserMasterDto> userMasterDtos) {

		List<UserMasterDto> dtos = userMasterService.saveUserMaster(userMasterDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}

	@PostMapping("/fetch")
	@Operation(summary = "Fetch User Master List", description = "Returns list of users based on query")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Successfully fetched user list"),

			@ApiResponse(responseCode = "400", description = "Invalid request data"),

			@ApiResponse(responseCode = "404", description = "No users found"),

			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<UserMasterDto>> fetchUserMaster(@Valid @RequestBody UserMasterQueryDto masterQueryDto) {

		List<UserMasterDto> userMasterDtos = new ArrayList<UserMasterDto>();
		if (masterQueryDto != null) {
			long count = userMasterService.getUserMasterDataCount(masterQueryDto);

			if (count != 0) {
				userMasterDtos = userMasterService.getUserMasterDataList(masterQueryDto);
			}
		}
		return new ResponseEntity<>(userMasterDtos, HttpStatus.ACCEPTED);

	}

	@PostMapping("/update")
	@Operation(summary = "Update User Master", description = "This API is used to update list of users into the system")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "202", description = "Users updated successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input data"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public ResponseEntity<List<UserMasterDto>> updateUserMaster(@Valid @RequestBody List<UserMasterDto> userMasterDtos)
	{
		List<UserMasterDto> dtos = userMasterService.updateUserMaster(userMasterDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
}
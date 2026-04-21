package com.epps.carrental.matsers.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epps.carrental.matsers.auth.service.AuthService;
import com.epps.carrental.matsers.user.dto.UserMasterDto;
import com.epps.carrental.matsers.user.entity.UserMaster;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Master API", description = "Operations related to Auth Management")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("/register")
	@Operation(summary = "Save User Master", description = "This API is used to save list of users into the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Users saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public UserMasterDto register(@Valid @RequestBody UserMasterDto user) {
		return service.register(user);
	}

	// 🔹 LOGIN API
	@Operation(summary = "User login", description = "Validates user credentials and returns user details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login successful"),
			@ApiResponse(responseCode = "401", description = "Invalid email or password") })
	@PostMapping("/login")
	public UserMaster login(@RequestBody UserMaster user) {
		return service.login(user.getEmail(), user.getPassword());
	}
}

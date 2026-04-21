package com.epps.carrental.transactions.booking.controller;

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

import com.epps.carrental.transactions.booking.dto.BookingDto;
import com.epps.carrental.transactions.booking.dto.BookingQueryDto;
import com.epps.carrental.transactions.booking.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/booking")
@Tag(name = "Booking Master API", description = "Operations related to Booking Management")
@Validated
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/save")
	@Operation(summary = "Save Booking Master", description = "This API is used to save list of booking into the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Booking saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<BookingDto>> saveBookingMaster(@Valid @RequestBody List<BookingDto> bookingDtos) {

		List<BookingDto> dtos = bookingService.saveBookingMaster(bookingDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/fetch")
	@Operation(summary = "Fetch Booking Master List", description = "Returns list of booking based on query")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Successfully fetched booking list"),

			@ApiResponse(responseCode = "400", description = "Invalid request data"),

			@ApiResponse(responseCode = "404", description = "No booking found"),

			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<BookingDto>> fetchBookingMaster(@Valid @RequestBody BookingQueryDto masterQueryDto) {

		List<BookingDto> bookingDtos = new ArrayList<BookingDto>();
		if (masterQueryDto != null) {
			long count = bookingService.getBookingMasterDataCount(masterQueryDto);

			if (count != 0) {
				bookingDtos = bookingService.getBookingMasterDataList(masterQueryDto);
			}
		}
		return new ResponseEntity<>(bookingDtos, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/update")
	@Operation(summary = "Update Booking Master", description = "This API is used to update list of booking into the system")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "202", description = "Booking updated successfully"),
	        @ApiResponse(responseCode = "400", description = "Invalid input data"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public ResponseEntity<List<BookingDto>> updateBookingMaster(@Valid @RequestBody List<BookingDto> bookingDtos)
	{
		List<BookingDto> dtos = bookingService.updateBookingMaster(bookingDtos);
		return new ResponseEntity<>(dtos, HttpStatus.ACCEPTED);
	}
}

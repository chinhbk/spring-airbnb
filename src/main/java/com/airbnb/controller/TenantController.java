package com.airbnb.controller;

import com.airbnb.domain.Booking;
import com.airbnb.dto.BookingResponse;
import com.airbnb.dto.NewBookingDTO;
import com.airbnb.dto.SearchDTO;
import com.airbnb.dto.SearchResultDTO;
import com.airbnb.service.BookingService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import java.awt.print.Pageable;
import java.util.List;

/*import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;*/

@Slf4j
@RestController
@RequestMapping(value = "/api/tenant")
@Api(value = "API use for tenant",
        description = "API use for tenant", produces = "application/json")
public class TenantController {

    @Autowired
    private BookingService bookingService;

    @ApiOperation(value = "Search property", produces = "application/json")
    @PostMapping("/property/search")
    public ResponseEntity<Page<SearchResultDTO>> search(Pageable pageable,
                                                        @RequestBody SearchDTO searchDTO) {
        return null; //TODO
    }

    @ApiOperation(value = "Get detail 1 property", produces = "application/json")
    @GetMapping("/property/get")
    public ResponseEntity<Page<Object>> getOne(@RequestParam String propertyId) {
        return null; //TODO
    }

    @ApiOperation(value = "Booking property API", produces = "application/json")
    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody NewBookingDTO newBookingDTO) throws Exception {
        log.debug("START process booking");
        Booking booking = bookingService.createBooking(newBookingDTO);
        BookingResponse response = new BookingResponse("Booking successfully created", booking.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Cancel a booking", produces = "application/json")
    @PutMapping("/booking/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        Booking cancel = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(cancel);
    }

    // Get all reservations for a guest
    @GetMapping("/booking/{userId}")
    public ResponseEntity<List<Booking>> getGuestBookings(@PathVariable Long userId) {
        List<Booking> reservations = bookingService.getBookingsByGuest(userId);
        return ResponseEntity.ok(reservations);
    }

    @ApiOperation(value = "Check-in API", produces = "application/json")
    @PutMapping("/booking/{bookingId}/check-in")
    public ResponseEntity<Boolean> checkIn(@PathVariable Long bookingId) {
        bookingService.checkIn(bookingId);
        return ResponseEntity.ok(true);
    }

    @ApiOperation(value = "Check-out API", produces = "application/json")
    @PutMapping("/booking/{bookingId}/check-out")
    public ResponseEntity<Boolean> checkOut(@PathVariable Long bookingId) {
        bookingService.checkOut(bookingId);
        return ResponseEntity.ok(true);
    }

    //TODO only host or admin reviews and confirms
    @PutMapping("/booking/{bookingId}/confirm")
    public ResponseEntity<Boolean> confirmBooking(@PathVariable Long bookingId) {
        bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok(true);
    }
}

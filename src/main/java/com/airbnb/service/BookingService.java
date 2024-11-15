package com.airbnb.service;

import com.airbnb.domain.Booking;
import com.airbnb.domain.Property;
import com.airbnb.domain.User;
import com.airbnb.dto.NewBookingDTO;
import com.airbnb.exception.BookingException;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.UserRepository;
import lombok.Data;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public Booking createBooking(NewBookingDTO newBookingDTO) {
        log.debug("Start process booking {}", newBookingDTO);
        //validate request data
        userRepository.findById(newBookingDTO.getUserId())
                .orElseThrow(() -> new BookingException("User with ID " + newBookingDTO.getUserId() + " not found"));

        Property property = propertyRepository.findById(newBookingDTO.getPropertyId())
                .orElseThrow(() -> new BookingException("Property " + newBookingDTO.getPropertyId() + "not found"));

        boolean alreadyBooked = bookingRepository.bookingExistsAtInterval(newBookingDTO.getStartDate(), newBookingDTO.getEndDate(), newBookingDTO.getPropertyId());

        if (alreadyBooked) {
            throw new BookingException("One booking already exists because the property is already booked for the selected dates");
        }

        //process data
        long numberOfNights = ChronoUnit.DAYS.between(newBookingDTO.getStartDate(), newBookingDTO.getEndDate());
        Booking booking = new Booking();
        booking.setUserId(newBookingDTO.getUserId());
        booking.setPropertyId(newBookingDTO.getPropertyId());
        booking.setStartDate(newBookingDTO.getStartDate());
        booking.setEndDate(newBookingDTO.getEndDate());
        booking.setNumberOfAdults(newBookingDTO.getNumberOfAdults());
        booking.setNumberOfChildren(newBookingDTO.getNumberOfChildren());
        booking.setNumberOfInfants(newBookingDTO.getNumberOfInfants());
        booking.setNumberOfPets(newBookingDTO.getNumberOfPets());
        booking.setTotalPrice(property.getPrice().multiply(BigDecimal.valueOf(numberOfNights)));
        booking.setStatus(Booking.ReservationStatus.PENDING);
        return bookingRepository.save(booking);
    }

    public Booking confirmBooking(Long reservationId) {
        Booking booking = bookingRepository.findById(reservationId)
                .orElseThrow(() -> new BookingException("Booking not found"));

        if (booking.getStatus() != Booking.ReservationStatus.PENDING) {
            throw new BookingException("Booking is not in PENDING status.");
        }

        booking.setStatus(Booking.ReservationStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking checkIn(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingException("Booking is not found"));

        if (booking.getStatus() != Booking.ReservationStatus.CONFIRMED) {
            throw new BookingException("Booking must be confirmed to check in.");
        }

        // Check if the check-in date has passed
        if (LocalDate.now().isBefore(booking.getStartDate())) {
            throw new BookingException("Check-in date has not arrived yet.");
        }

        booking.setStatus(Booking.ReservationStatus.CHECKED_IN);
        return bookingRepository.save(booking);
    }

    public Booking checkOut(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingException("Booking not found"));

        if (booking.getStatus() != Booking.ReservationStatus.CHECKED_IN) {
            throw new BookingException("Booking must be checked in to check out.");
        }

        // Check if the check-out date has passed
        if (LocalDate.now().isBefore(booking.getEndDate())) {
            throw new BookingException("Check-out date has not arrived yet.");
        }

        booking.setStatus(Booking.ReservationStatus.CHECKED_OUT);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Only allow cancellations for pending bookings
        if (booking.getStatus() != Booking.ReservationStatus.PENDING) {
            throw new RuntimeException("Cannot cancel a confirmed or already canceled booking");
        }

        booking.setStatus(Booking.ReservationStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByGuest(Long userId) {
        //TODO
        return null;
    }
}

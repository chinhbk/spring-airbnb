package com.airbnb.repository;

import com.airbnb.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT case when count(booking) > 0 then true else false end" +
            " from Booking  booking WHERE NOT (booking.endDate <= :startDate or booking.startDate >= :endDate)" +
            " AND booking.propertyId = :propertyId")
    boolean bookingExistsAtInterval(LocalDate startDate, LocalDate endDate, Long propertyId);
}

package com.airbnb.domain;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_id", nullable = false)
    private long propertyId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "no_of_adults", nullable = false)
    private int numberOfAdults;

    @Column(name = "no_of_children", nullable = false)
    private int numberOfChildren;

    @Column(name = "no_of_infants", nullable = false)
    private int numberOfInfants;

    @Column(name = "no_of_pets", nullable = false)
    private int numberOfPets;

    public static enum ReservationStatus {
        PENDING,
        CONFIRMED,
        CHECKED_IN,
        CHECKED_OUT,
        CANCELLED
    }
}

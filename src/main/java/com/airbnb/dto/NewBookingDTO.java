package com.airbnb.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class NewBookingDTO {

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotBlank
    private Long propertyId;
    @NotNull
    private Long userId;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfInfants;
    private int numberOfPets;
}

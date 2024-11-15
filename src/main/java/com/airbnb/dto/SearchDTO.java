package com.airbnb.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SearchDTO {
    private String location;
    private Date checkinDate;
    private Date checkoutDate;
}

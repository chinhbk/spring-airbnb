package com.airbnb.domain;

import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String location;

    @Column(nullable = false)
    private boolean available = true;
}

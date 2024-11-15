package com.airbnb.repository;

import com.airbnb.domain.Booking;
import com.airbnb.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  PropertyRepository  extends JpaRepository<Property, Long> {
    List<Property> findByLocationAndAvailableTrue(String location);
}

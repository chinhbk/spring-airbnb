package com.airbnb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAirbnbApplication {
	private final static Logger logger = LoggerFactory.getLogger(SpringAirbnbApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringAirbnbApplication.class, args);
	}
}

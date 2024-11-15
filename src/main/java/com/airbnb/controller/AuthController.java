package com.airbnb.controller;

import com.airbnb.dto.LoginDTO;
import com.airbnb.dto.SignupDTO;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@Api(value = "API for authentication",
        description = "API for authentication", produces = "application/json")
public class AuthController {

    @ApiOperation(value = "Sign in API", produces = "application/json")
    @PostMapping("/sign-in")
    public ResponseEntity<Object> login(
            @RequestBody LoginDTO loginDTO
            ) {
       return null; //TODO
    }

    @ApiOperation(value = "Sign up API", produces = "application/json")
    @PostMapping("/sign-up")
    public ResponseEntity<Object> register(
            @RequestBody SignupDTO signupDTO
    ) {
        return null; //TODO
    }
}

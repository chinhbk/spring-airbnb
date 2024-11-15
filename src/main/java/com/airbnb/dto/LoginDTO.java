package com.airbnb.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

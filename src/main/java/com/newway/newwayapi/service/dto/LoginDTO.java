package com.newway.newwayapi.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @NotNull
    @Size(min = 1, max = 255)
    private String login;
    @NotNull
    @Size(min = 1, max = 34)
    private String password;
    private boolean rememberMe;
}

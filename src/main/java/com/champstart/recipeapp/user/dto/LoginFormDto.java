package com.champstart.recipeapp.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginFormDto {
    @NotBlank(message = "Email address should not be blank.")
    @Email
    private String email;
    @NotBlank
    private String password;
}

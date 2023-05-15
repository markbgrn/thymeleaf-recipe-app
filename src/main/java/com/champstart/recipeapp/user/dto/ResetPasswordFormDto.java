package com.champstart.recipeapp.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ResetPasswordFormDto {
    @NotBlank
    @Email
    private String email;
}

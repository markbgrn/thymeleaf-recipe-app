package com.champstart.recipeapp.user.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class NewPasswordFormDto {
    @NotBlank(message = "This field should not be blank")
    @Length(min = 8, max = 64, message = "Password length should be between 8 and 64 characters")
    private String password;
    @NotBlank(message = "This field should not be blank")
    private String confirmPassword;

    public boolean isPasswordNotEqualToConfirmPassword() {
        return !password.equals(confirmPassword);
    }
}

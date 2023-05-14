package com.champstart.recipeapp.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
@Data
@Builder
public class UserDto {

    @NotBlank(message = "This field should not be blank")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "This field should not be blank")
    @Length(min = 8, max = 64, message = "Password length should be between 8 and 64 characters")
    private String password;
    @NotBlank(message = "This field should not be blank")
    private String confirmPassword;
    @NotBlank(message = "This field should not be blank")
    private String firstName;
    @NotBlank(message = "This field should not be blank")
    private String lastName;
    private String verificationId;
    private Boolean isVerified = false;

    public boolean isPasswordNotEqualToConfirmPassword() {
        return !password.equals(confirmPassword);
    }

}

package com.champstart.recipeapp.user.dto.dtoMapper;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserMapper {
    public static UserDto mapToUserDto(UserModel userModel) {
        return UserDto.builder()
                .id(userModel.getId())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .confirmPassword(userModel.getPassword())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .verificationId(userModel.getVerificationId())
                .isVerified(userModel.getIsVerified())
                .createdOn(userModel.getCreatedOn())
                .updatedOn(userModel.getUpdatedOn())
                .build();
    }
    public static UserModel mapToUser(UserDto userDto) {
        UserModel user = new UserModel();

        return UserModel.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .verificationId(userDto.getVerificationId())
                .isVerified(userDto.getIsVerified())
                .createdOn(userDto.getCreatedOn())
                .updatedOn(userDto.getUpdatedOn())
                .build();
    }
}

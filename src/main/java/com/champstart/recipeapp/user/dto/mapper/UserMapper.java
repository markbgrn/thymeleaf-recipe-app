package com.champstart.recipeapp.user.dto.mapper;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;

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
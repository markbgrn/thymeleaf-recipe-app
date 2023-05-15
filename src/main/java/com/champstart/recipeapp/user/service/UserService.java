package com.champstart.recipeapp.user.service;

import com.champstart.recipeapp.user.dto.LoginFormDto;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModel saveUser(UserDto userDto);
    UserModel updateUser(UserDto userDto);
    void setUserVerified(String verificationId);
    UserModel findByEmail(String email);
    UserModel mapToUser(UserDto userDto);
    UserModel findByVerificationId(String verificationId);
    void updatePassword(UserDto userDto, String newPassword);

}

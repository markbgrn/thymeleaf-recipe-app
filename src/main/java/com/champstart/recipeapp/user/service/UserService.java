package com.champstart.recipeapp.user.service;

import com.champstart.recipeapp.user.dto.LoginFormDto;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModel saveUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void setUserVerified(String verificationId);

    UserModel loginUser(LoginFormDto loginFormDto);

    UserModel findByEmail(String email);




}

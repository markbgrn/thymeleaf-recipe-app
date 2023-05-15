package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.ResetPasswordFormDto;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.service.ResetPasswordService;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService{
    private UserService userService;

    @Autowired
    ResetPasswordServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validateResetPasswordForm(ResetPasswordFormDto resetPasswordFormDto, BindingResult result) {
        UserModel userModel = userService.findByEmail(resetPasswordFormDto.getEmail());
        if(userModel == null) {
            result.addError(new FieldError("user", "email", "There is no account associated with this email"));
        }
    }

    public UserService getUserService() {
        return userService;
    }
}

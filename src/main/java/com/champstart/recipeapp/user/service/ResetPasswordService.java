package com.champstart.recipeapp.user.service;

import com.champstart.recipeapp.user.dto.ResetPasswordFormDto;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import org.springframework.validation.BindingResult;

public interface ResetPasswordService {
    void validateResetPasswordForm(ResetPasswordFormDto resetPasswordFormDto, BindingResult result);
}

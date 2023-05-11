package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.service.EmailService;
import com.champstart.recipeapp.user.service.RegistrationService;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    public void validateRegistrationForm(UserDto userDto, BindingResult result) {
        if(userDto.isPasswordNotEqualToConfirmPassword()) {
            result.addError( new FieldError("user", "confirmPassword", "Passwords do not match"));
        }

        if(emailExists(userDto.getEmail())){
            result.addError( new FieldError("user", "email", "Email is already registered"));
        }
    }

    public boolean emailExists(String email) {
        boolean result = false;
        UserModel user = userService.findByEmail(email);

        if(user != null) {
            result = true;
        }

        return result;
    }

    public void register(UserDto userDto) {
        userService.saveUser(userDto);
        emailService.sendEmail(userDto.getEmail(), "Verify your RecipeHub Account", emailService.contructVerificationHtml(userDto.getVerificationId()));
    }
}

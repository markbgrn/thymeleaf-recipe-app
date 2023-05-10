package com.champstart.recipeapp.user.service;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class RegistrationService {
    @Autowired
    UserRepository userRepository;

    public void validateRegistrationForm(UserDto userDto, BindingResult result) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.addError( new FieldError("user", "confirmPassword", "Passwords do not match"));
        }

        UserModel user = userRepository.findByEmail(userDto.getEmail());
//        int size = users.size();

        if(user != null){
            result.addError( new FieldError("user", "email", "Email already registered"));
        }
    }


}

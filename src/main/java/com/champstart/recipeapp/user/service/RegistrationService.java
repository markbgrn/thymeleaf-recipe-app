package com.champstart.recipeapp.user.service;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public interface RegistrationService {
    public void validateRegistrationForm(UserDto userDto, BindingResult result);

    public boolean emailExists(String email);

    public void register(UserDto userDto);
}

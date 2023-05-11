package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.service.EmailService;
import com.champstart.recipeapp.user.service.RegistrationService;
import com.champstart.recipeapp.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;

    @Mock
    BindingResult bindingResult;


    @InjectMocks
    RegistrationServiceImpl registrationService;

    UserDto userDto;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        userDto = UserDto.builder()
                .email("test@gmail.com")
                .password("")
                .confirmPassword("password")
                .firstName("Jane")
                .lastName("Doe")
                .verificationId("")
                .build();
    }

    @Test
    void validateRegistrationForm() {
        registrationService.validateRegistrationForm(userDto, bindingResult);
//        doThrow(new Exception()).when(bindingResult).addError(Mockito.mock(FieldError.class));
    }

    @Test
    void emailExists() {
        registrationService.register(userDto);
        when(userService.findByEmail(userDto.getEmail())).thenReturn(Mockito.mock(UserModel.class));

        boolean emailExists = registrationService.emailExists(userDto.getEmail());

        assertTrue(emailExists);
    }

    @Test
    void register() {
        registrationService.register(userDto);
    }
}
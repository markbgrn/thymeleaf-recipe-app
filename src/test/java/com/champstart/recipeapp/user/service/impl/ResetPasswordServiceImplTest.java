package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.ResetPasswordFormDto;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.service.EmailService;
import com.champstart.recipeapp.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResetPasswordServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    BindingResult bindingResult;


    @InjectMocks
    ResetPasswordServiceImpl resetPasswordService;

    ResetPasswordFormDto resetPasswordFormDto;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        resetPasswordFormDto = ResetPasswordFormDto.builder()
                .email("test@gmail.com")
                .build();
    }

    @Test
    void checkIfEmailAlreadyRegistered() {
        UserModel mockUser = mock(UserModel.class);
        when(userService.findByEmail(resetPasswordFormDto.getEmail())).thenReturn(mockUser);

        UserModel userModel = resetPasswordService.checkIfEmailAlreadyRegistered(resetPasswordFormDto, bindingResult);
        assertNotNull(userModel);
    }
}
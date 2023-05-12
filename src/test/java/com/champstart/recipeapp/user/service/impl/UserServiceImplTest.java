package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.RoleRepository;
import com.champstart.recipeapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    UserServiceImpl userService;

    UserDto userDto;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        userDto = UserDto.builder()
                .email("test@gmail.com")
                .password("password")
                .confirmPassword("password")
                .firstName("Jane")
                .lastName("Doe")
                .verificationId("test")
                .build();
    }

    @Test
    void saveUser() {
        UserModel mockUser = mock(UserModel.class);
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(userService.mapToUser(userDto)).thenReturn(mockUser);
        UserModel userModel = userService.saveUser(userDto);

        assertNotNull(userModel);
    }

    @Test
    void setUserVerified() {
        when(userRepository.findByVerificationId(anyString())).thenReturn(mock(UserModel.class));
        userService.saveUser(userDto);
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(mock(UserModel.class));
        UserModel userModel = userService.findByEmail(userDto.getEmail());
        assertNotNull(userModel);
    }
}
package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.dto.mapper.UserMapper;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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
        UserModel userModel = null;

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.mapToUser(userDto)).thenReturn(mockUser);
            userModel = userService.saveUser(userDto);
        }
        assertNotNull(userModel);
    }

    @Test
    void setUserVerified() {
        UserModel mockUser = mock(UserModel.class);
        when(userRepository.findByVerificationId(anyString())).thenReturn(mockUser);
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        UserModel userModel = null;
        userModel = userService.setUserVerified(userDto.getVerificationId());

        assertNotNull(userModel);
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(mock(UserModel.class));
        UserModel userModel = userService.findByEmail(userDto.getEmail());
        assertNotNull(userModel);
    }
}
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

    @Test
    void updatePassword() {
        UserDto mockUserDto = mock(UserDto.class);
        UserModel mockUserModel = mock(UserModel.class);
        String newPassword = "newpassword";
        UserModel resultUserModel = null;

        when(passwordEncoder.encode(anyString())).thenReturn(newPassword);
        when(userRepository.save(mockUserModel)).thenReturn(mockUserModel);

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.mapToUser(mockUserDto)).thenReturn(mockUserModel);
            resultUserModel = userService.updatePassword(mockUserDto, newPassword);
        }

        assertNotNull(resultUserModel);
        verify(mockUserDto, times(1)).setPassword(newPassword);
    }

    @Test
    void updateProfile() {
        UserModel userModel = mock(UserModel.class);

        when(userRepository.save(userModel)).thenReturn(userModel);

        UserModel resultUserModel = userService.updateProfile(userModel, "Updated First Name", "Updated Last Name", "Updated Photo Path");

        verify(userModel, times(1)).setFirstName("Updated First Name");
        verify(userModel, times(1)).setLastName("Updated Last Name");
        verify(userModel, times(1)).setPhotoPath("Updated Photo Path");

        assertNotNull(resultUserModel);
    }
}
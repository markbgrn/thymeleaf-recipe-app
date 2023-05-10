package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.RoleRepository;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setVerificationId(passwordEncoder.encode(userDto.getEmail()));
        userDto.setIsVerified(false);
        return userRepository.save(mapToUser(userDto));
    }

    @Override
    public void updateUser(UserDto userDto) {
        UserModel user = mapToUser(userDto);
        userRepository.save(user);
    }

    @Override
    public void setUserVerified(String verificationId) {
        UserModel user = userRepository.findByVerificationId(verificationId);
        user.setIsVerified(true);
        userRepository.save(user);
    }

    @Override
    public UserModel loginUser(LoginFormDto loginFormDto) {
        return null;
    }

    @Override
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserModel mapToUser(UserDto userDto) {
        UserModel user = new UserModel();

        return UserModel.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .verificationId(userDto.getVerificationId())
                .isVerified(userDto.getIsVerified())
                .build();
    }
}

package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.dto.mapper.UserMapper;
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
        UserModel userModel = UserMapper.mapToUser(userDto);
        return userRepository.save(userModel);
    }


    @Override
    public UserModel setUserVerified(String verificationId) {
        UserModel user = userRepository.findByVerificationId(verificationId);
        user.setIsVerified(true);
        return userRepository.save(user);
    }

    @Override
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

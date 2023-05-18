package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.dto.mapper.UserMapper;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel saveUser(UserDto userDto) {

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

    @Override
    public UserModel findByVerificationId(String verificationId) {
        return userRepository.findByVerificationId(verificationId);
    }

    @Override
    public UserModel updatePassword(UserDto userDto, String newPassword) {
        userDto.setPassword(passwordEncoder.encode(newPassword));
        UserModel userModel = UserMapper.mapToUser(userDto);
        return userRepository.save(userModel);
    }

    @Override
    public UserModel updateProfile(UserModel userModel, String firstName, String lastName, String photoPath) {
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setPhotoPath(photoPath);

        return userRepository.save(userModel);
    }
}

package com.champstart.recipeapp.user.controller;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.service.EmailService;
import com.champstart.recipeapp.user.service.RegistrationService;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, RegistrationService registrationService, EmailService emailService) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.emailService = emailService;
    }
    @GetMapping("/register")
    public String createUserForm(Model model) {
        UserDto user = UserDto.builder().build();
        model.addAttribute("user", user);
        return "user-registration";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        registrationService.validateRegistrationForm(userDto, result);

        if (result.hasErrors()){
            model.addAttribute("user", userDto);
            return "user-registration";
        }

        registrationService.register(userDto);
        return "user-registration-verification";
    }

    @GetMapping("/verify")
    public String verifyAccount(HttpServletRequest request) {
        String verificationId = request.getParameter("verificationId");
        userService.setUserVerified(verificationId);

        return "user-verification-success";
    }
}

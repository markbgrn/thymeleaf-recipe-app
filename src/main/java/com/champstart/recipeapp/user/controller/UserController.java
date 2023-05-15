package com.champstart.recipeapp.user.controller;

import com.champstart.recipeapp.user.dto.NewPasswordFormDto;
import com.champstart.recipeapp.user.dto.ResetPasswordFormDto;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.dto.mapper.UserMapper;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.service.EmailService;
import com.champstart.recipeapp.user.service.RegistrationService;
import com.champstart.recipeapp.user.service.ResetPasswordService;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final EmailService emailService;
    private final ResetPasswordService resetPasswordService;

    @Autowired
    public UserController(UserService userService, RegistrationService registrationService, EmailService emailService, ResetPasswordService resetPasswordService) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.emailService = emailService;
        this.resetPasswordService = resetPasswordService;
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

    @GetMapping("/reset-password")
    public String resetPasswordForm(Model model) {
        ResetPasswordFormDto resetPasswordFormDto = ResetPasswordFormDto.builder().build();
        model.addAttribute("resetPasswordFormDto", resetPasswordFormDto);
        return "user-reset-password";
    }

    @PostMapping("/reset-password")
    public ModelAndView resetPassword(@Valid @ModelAttribute("resetPasswordFormDto") ResetPasswordFormDto resetPasswordFormDto, BindingResult result) {
        UserModel userModel = resetPasswordService.checkIfEmailAlreadyRegistered(resetPasswordFormDto, result);
        if (result.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("user-reset-password");
            modelAndView.addObject("resetPasswordFormDto", resetPasswordFormDto);
            return modelAndView;
        }

        emailService.sendEmail(userModel.getEmail(), "Reset your RecipeHub password", emailService.contructResetPasswordHtml(userModel.getVerificationId()));

        return new ModelAndView("user-reset-password-email");
    }

    @GetMapping("/new-password")
    public String newPasswordForm(@RequestParam Map<String, String> parameters, Model model)
    {
        NewPasswordFormDto newPasswordFormDto = NewPasswordFormDto.builder().build();
        model.addAttribute("newPasswordFormDto", newPasswordFormDto);
        model.addAttribute("verificationId", parameters.get("verificationId"));

        return "user-new-password";
    }

    @PostMapping("/new-password")
    public String newPassword(@RequestParam Map<String, String> parameters,
                                @Valid @ModelAttribute("newPasswordFormDto") NewPasswordFormDto newPasswordFormDto,
                              BindingResult result,
                              Model model)
    {

        if(newPasswordFormDto.isPasswordNotEqualToConfirmPassword()) {
            result.addError( new FieldError("user", "confirmPassword", "Passwords do not match"));
        }

        if (result.hasErrors()){
            model.addAttribute("newPasswordFormDto", newPasswordFormDto);
            return "user-new-password";
        }

        UserModel userModel = userService.findByVerificationId(parameters.get("verificationId"));
        userService.updatePassword(UserMapper.mapToUserDto(userModel), newPasswordFormDto.getPassword());

        return "user-new-password-success";
    }
}

package com.champstart.recipeapp.user.controller;

import com.champstart.recipeapp.user.dto.EditProfileDto;
import com.champstart.recipeapp.user.dto.NewPasswordFormDto;
import com.champstart.recipeapp.user.dto.ResetPasswordFormDto;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.dto.mapper.UserMapper;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.security.SecurityUtil;
import com.champstart.recipeapp.user.service.EmailService;
import com.champstart.recipeapp.user.service.RegistrationService;
import com.champstart.recipeapp.user.service.ResetPasswordService;
import com.champstart.recipeapp.user.service.UserService;
import com.champstart.recipeapp.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


@Controller
public class UserController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final EmailService emailService;
    private final ResetPasswordService resetPasswordService;
    private final SecurityUtil securityUtil;

    private final ServletContext servletContext;


    @Autowired
    public UserController(UserService userService, RegistrationService registrationService, EmailService emailService, ResetPasswordService resetPasswordService, SecurityUtil securityUtil, ServletContext servletContext) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.emailService = emailService;
        this.resetPasswordService = resetPasswordService;
        this.securityUtil = securityUtil;
        this.servletContext = servletContext;
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

    @GetMapping("/edit-profile")
    public String editProfileForm(Model model) {
        String email = SecurityUtil.getSessionUser();
        UserModel userModel = userService.findByEmail(email);
        EditProfileDto editProfileDto = EditProfileDto.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .build();
        model.addAttribute("editProfileDto", editProfileDto);
        return "user-edit-profile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(@Valid @ModelAttribute("editProfileDto") EditProfileDto editProfileDto,
                              BindingResult result,
                              @RequestParam MultipartFile photo,
                              Model model) {

        if (result.hasErrors()){
            model.addAttribute("editProfileDto", editProfileDto);
            return "user-edit-profile";
        }

        String email = SecurityUtil.getSessionUser();
        UserModel userModel = userService.findByEmail(email);
        String fileName = "";

        if(!FileUtil.isMultipartFileEmpty(photo)) {
            fileName = userModel.getId() + "_photo." + FileUtil.getMultipartFileExtention(photo);
            boolean isFileSaved = FileUtil.saveFile(FileUtil.PROFILE_PHOTOS_PATH, fileName, photo);
            if(!isFileSaved) {
                fileName = "";
            }
        }

        userService.updateProfile(userModel, editProfileDto.getFirstName(), editProfileDto.getLastName(), fileName);

        return "redirect:/view-profile";
    }

    @GetMapping("/view-profile")
    public String viewProfile(Model model) {
        String email = SecurityUtil.getSessionUser();
        UserModel userProfile = userService.findByEmail(email);

        model.addAttribute("userProfile", userProfile);
        return "user-view-profile";
    }

    @GetMapping("/images/profile/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = FileUtil.getFileFromFileSystem(FileUtil.PROFILE_PHOTOS_PATH, filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}

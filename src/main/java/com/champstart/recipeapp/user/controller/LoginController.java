package com.champstart.recipeapp.user.controller;

import com.champstart.recipeapp.user.dto.LoginFormDto;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller

public class LoginController {

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", LoginFormDto.builder().build());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("loginForm") LoginFormDto loginForm, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("loginForm", loginForm);
            return "login";
        }
        return "redirect:/recipes";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        String user = SecurityUtil.getSessionUser();
        model.addAttribute("user", user);
        model.addAttribute("firstName", securityUtil.getUserModel().getFirstName());
        model.addAttribute("lastName", securityUtil.getUserModel().getLastName());
        return "home";
    }
}

package com.champstart.recipeapp.recipe.controller;

import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.service.RecipeService;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.security.SecurityUtil;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model){
        UserModel user = new UserModel();
        List<RecipeDTO> recipes = recipeService.findAllRecipes();
        String firstName = SecurityUtil.getSessionUser();
        model.addAttribute("recipes", recipes);
        return "view/recipe/recipe-list";
    }
}

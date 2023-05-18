package com.champstart.recipeapp.recipe.controller;

import com.champstart.recipeapp.category.dto.CategoryDTO;
import com.champstart.recipeapp.category.service.CategoryService;
import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.service.RecipeService;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.security.SecurityUtil;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.categoryService = categoryService;
    }
    @GetMapping("/recipes")
    public String listRecipes(Model model){
        UserModel user = new UserModel();
        List<RecipeDTO> recipes = recipeService.findAllRecipes();
        String firstName = SecurityUtil.getSessionUser();
        if (firstName != null){
            user = userService.findByEmail(firstName);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("recipes", recipes);
        return "view/recipe/recipe-list";
    }
    @GetMapping("/recipes/{id}")
    public String recipeDetail(@PathVariable("id") Long id, Model model){
        RecipeDTO recipeDTO = recipeService.getRecipeById(id);
        UserModel user = new UserModel();
        String email = SecurityUtil.getSessionUser();
        user = userService.findByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("recipe", recipeDTO);
        return "view/recipe/recipe-detail";
    }
    @GetMapping("/recipes/new")
    public String createRecipe(Model model){
        Recipe recipe = new Recipe();
        List<CategoryDTO> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("recipe", recipe);
        return "view/recipe/recipe-create";
    }
    @PostMapping("/recipes/new")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeDTO recipeDTO, @ModelAttribute("categories") CategoryDTO categoryDTO, BindingResult bindingResult, Model model){
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println(recipeDTO);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        if (bindingResult.hasErrors()){
            List<CategoryDTO> categories = categoryService.findAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("recipe", recipeDTO);
            return "view/recipe/recipe-create";
        }

        recipeService.createRecipe(recipeDTO.getCategory().getId(), recipeDTO);
        return "redirect:/recipes";
    }
}

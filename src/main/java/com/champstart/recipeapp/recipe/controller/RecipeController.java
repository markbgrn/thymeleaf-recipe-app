package com.champstart.recipeapp.recipe.controller;

import com.champstart.recipeapp.category.dto.CategoryDTO;
import com.champstart.recipeapp.category.service.CategoryService;
import com.champstart.recipeapp.ingredient.dto.mapper.IngredientMapper;
import com.champstart.recipeapp.ingredient.service.IngredientService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService, CategoryService categoryService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
    }
    @GetMapping("/home")
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
        return "home";
    }
    @GetMapping("/my-recipes")
    public String listMyRecipes(Model model) {
        UserModel user = new UserModel();
        String email = SecurityUtil.getSessionUser();
        user = userService.findByEmail(email);
        List<RecipeDTO> myRecipes = recipeService.findByUserId(user.getId());

        model.addAttribute("recipes", myRecipes);
        return "view/recipe/my-recipes";
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

        recipeService.createRecipe(recipeDTO);
        return "redirect:/my-recipes";
    }

    @GetMapping("/recipes/search")
    public String searchRecipes(@RequestParam("q") String recipeName, Model model) {
        List<Recipe> recipes = recipeService.searchRecipes(recipeName);
        model.addAttribute("recipes", recipes);
        model.addAttribute("recipeName", recipeName);
        return "view/recipe/recipe-search";
    }

    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("recipeName", "");
        return "view/search/search";
    }
}
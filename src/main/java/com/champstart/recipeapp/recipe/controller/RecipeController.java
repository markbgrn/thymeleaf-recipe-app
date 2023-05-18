package com.champstart.recipeapp.recipe.controller;

import com.champstart.recipeapp.category.dto.CategoryDTO;
import com.champstart.recipeapp.category.service.CategoryService;
import com.champstart.recipeapp.ingredient.dto.mapper.IngredientMapper;
import com.champstart.recipeapp.ingredient.service.IngredientService;
import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.service.RecipeService;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.security.SecurityUtil;
import com.champstart.recipeapp.user.service.UserService;
import com.champstart.recipeapp.util.FileUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/images/recipe/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = FileUtil.getFileFromFileSystem(FileUtil.RECIPE_PHOTOS_PATH, filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
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
        model.addAttribute("recipe", recipeDTO);
        return "view/recipe/recipe-detail";
    }
    @GetMapping("/recipes/new")
    public String createRecipe(Model model){
        Recipe recipe = new Recipe();
        RecipeDTO recipeDTO = new RecipeDTO();
        List<CategoryDTO> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("recipe", RecipeMapper.mapToRecipeDTO(recipe));
        return "view/recipe/recipe-create";
    }
    @PostMapping("/recipes/new")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeDTO recipeDTO,
                             @RequestParam MultipartFile photo,
                             BindingResult bindingResult,
                             Model model){
        if (bindingResult.hasErrors()){
            List<CategoryDTO> categories = categoryService.findAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("recipe", recipeDTO);
            return "view/recipe/recipe-create";
        }
        String fileName = "";
        if(!FileUtil.isMultipartFileEmpty(photo)) {
            fileName = photo.getOriginalFilename();
            boolean isFileSaved = FileUtil.saveFile(FileUtil.RECIPE_PHOTOS_PATH, fileName, photo);
            if(!isFileSaved) {
                fileName = "";
            }
        }
        recipeDTO.setPhotoPath(fileName);
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
    @GetMapping("/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable("id")Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/my-recipes";
    }
    @GetMapping("/recipes/{id}/edit")
    public String updateRecipeForm(@PathVariable("id") Long id, Model model) {
        RecipeDTO recipeDto = recipeService.getRecipeById(id);
        List<CategoryDTO> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("recipe", recipeDto);
        return "view/recipe/recipe-edit";
    }
    @PostMapping("/recipes/{id}/edit")
    public String updateRecipe(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("recipe") RecipeDTO recipeDto,
                               BindingResult result,
                               @RequestParam MultipartFile photo,
                               Model model) {
        if(result.hasErrors()) {
            List<CategoryDTO> categories = categoryService.findAllCategories();
            model.addAttribute("categories", categories);
            return "view/recipe/recipe-edit";
        }
        String fileName = "";
        if(!FileUtil.isMultipartFileEmpty(photo)) {
            fileName = photo.getOriginalFilename();
            boolean isFileSaved = FileUtil.saveFile(FileUtil.RECIPE_PHOTOS_PATH, fileName, photo);
            if(!isFileSaved) {
                fileName = "";
            }
        }
        recipeDto.setId(id);
        recipeDto.setPhotoPath(fileName);
        recipeService.updateRecipe(recipeDto);
        return "redirect:/my-recipes";
    }

}
package com.champstart.recipeapp.recipe.service;

import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.model.Recipe;

import java.util.List;

public interface RecipeService {
    RecipeDTO getRecipeById(Long id);
    Recipe createRecipe(RecipeDTO recipeDTO);
    List<RecipeDTO> findAllRecipes();
    void updateRecipe(RecipeDTO recipeDTO);

    void deleteRecipe(Long id);
}

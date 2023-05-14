package com.champstart.recipeapp.ingredient.service;

import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.ingredient.model.Ingredient;

public interface IngredientService {
    void createIngredient(Long id, IngredientDTO ingredientDTO);
}

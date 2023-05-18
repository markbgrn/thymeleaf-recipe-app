package com.champstart.recipeapp.ingredient.dto;

import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDTO{
    private Long id;
    private String ingredients;
    private Double quantity;
    private String unit;
    private Recipe recipe;
}

package com.champstart.recipeapp.ingredient.dto;

import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDTO{
    private Long id;
    @NotBlank(message = "Ingredient should not be empty")
    private String ingredients;
    @NotBlank(message = "Quantity should not be empty")
    private Double quantity;
    @NotBlank(message = "Unit should not be empty")
    private String unit;
    private Recipe recipe;
}

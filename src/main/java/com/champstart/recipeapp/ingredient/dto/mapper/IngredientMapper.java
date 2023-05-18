package com.champstart.recipeapp.ingredient.dto.mapper;

import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.ingredient.model.Ingredient;
import com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper;
import com.champstart.recipeapp.recipe.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientMapper {
    public static Ingredient mapToIngredientEntity(IngredientDTO ingredientDTO) {
        return Ingredient.builder()
                .id(ingredientDTO.getId())
                .ingredients(ingredientDTO.getIngredients())
                .quantity(ingredientDTO.getQuantity())
                .unit(ingredientDTO.getUnit())
                .recipe(ingredientDTO.getRecipe())
                .build();
    }

    public static IngredientDTO mapToIngredientDTO(Ingredient ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .ingredients(ingredient.getIngredients())
                .quantity(ingredient.getQuantity())
                .unit(ingredient.getUnit())
                .recipe(ingredient.getRecipe())
                .build();
    }
}

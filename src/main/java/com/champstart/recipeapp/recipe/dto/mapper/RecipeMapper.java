package com.champstart.recipeapp.recipe.dto.mapper;

import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.ingredient.dto.mapper.IngredientMapper;
import com.champstart.recipeapp.ingredient.model.Ingredient;
import com.champstart.recipeapp.procedure.dto.ProcedureDTO;
import com.champstart.recipeapp.procedure.dto.mapper.ProcedureMapper;
import com.champstart.recipeapp.procedure.model.Procedure;
import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.champstart.recipeapp.ingredient.dto.mapper.IngredientMapper.mapToIngredientDTO;
import static com.champstart.recipeapp.procedure.dto.mapper.ProcedureMapper.mapToProcedureDTO;

public class RecipeMapper {
    public static Recipe mapToRecipeEntity(RecipeDTO recipeDTO){
        return Recipe.builder()
                .id(recipeDTO.getId())
                .recipeTitle(recipeDTO.getRecipeTitle())
                .recipeDescription(recipeDTO.getRecipeDescription())
                .build();
    }
    public static RecipeDTO mapToRecipeDTO(Recipe recipe){
        return RecipeDTO.builder()
                .id(recipe.getId())
                .recipeTitle(recipe.getRecipeTitle())
                .recipeDescription(recipe.getRecipeDescription())
                .ingredients(recipe.getIngredients()
                        .stream()
                        .map(IngredientMapper::mapToIngredientDTO)
                        .collect(Collectors.toList()))
                .procedures(recipe.getProcedures().stream()
                        .map(ProcedureMapper::mapToProcedureDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}

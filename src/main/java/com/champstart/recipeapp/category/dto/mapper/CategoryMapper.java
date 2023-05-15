package com.champstart.recipeapp.category.dto.mapper;

import com.champstart.recipeapp.category.dto.CategoryDTO;
import com.champstart.recipeapp.category.model.Category;
import com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper;

import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category mapToCategoryEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .categoryName(categoryDTO.getCategoryName())
                .build();
    }
    public static CategoryDTO mapToCategoryDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .recipes(category.getRecipes()
                        .stream()
                        .map(RecipeMapper::mapToRecipeDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}

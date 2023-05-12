package com.champstart.recipeapp.category.dto.mapper;

import com.champstart.recipeapp.category.dto.CategoryDto;
import com.champstart.recipeapp.category.model.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}

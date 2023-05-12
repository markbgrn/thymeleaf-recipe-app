package com.champstart.recipeapp.category.service;

import com.champstart.recipeapp.category.dto.CategoryDto;
import com.champstart.recipeapp.category.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();
    Category saveCategory(Category category);
    CategoryDto findCategoryById(Long id);
    boolean deleteById(Long id);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
}

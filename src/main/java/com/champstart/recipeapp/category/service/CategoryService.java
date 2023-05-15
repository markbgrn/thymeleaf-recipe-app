package com.champstart.recipeapp.category.service;

import com.champstart.recipeapp.category.dto.CategoryDTO;
import com.champstart.recipeapp.category.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAllCategories();
    Category saveCategory(Category category);
    CategoryDTO findCategoryById(Long id);
    boolean deleteById(Long id);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDto);
}

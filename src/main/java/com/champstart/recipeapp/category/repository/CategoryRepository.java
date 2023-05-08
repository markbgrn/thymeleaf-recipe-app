package com.champstart.recipeapp.category.repository;

import com.champstart.recipeapp.category.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
}

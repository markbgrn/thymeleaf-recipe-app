package com.champstart.recipeapp.ingredient.repository;

import com.champstart.recipeapp.ingredient.model.IngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientModel, Long> {
}

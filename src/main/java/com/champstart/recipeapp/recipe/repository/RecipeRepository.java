package com.champstart.recipeapp.recipe.repository;

import com.champstart.recipeapp.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

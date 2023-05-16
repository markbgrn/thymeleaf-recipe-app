package com.champstart.recipeapp.recipe.repository;

import com.champstart.recipeapp.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByRecipeTitleContainingIgnoreCase(String recipeName);
}

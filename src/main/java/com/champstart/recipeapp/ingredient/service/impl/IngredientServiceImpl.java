package com.champstart.recipeapp.ingredient.service.impl;

import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.ingredient.dto.mapper.IngredientMapper;
import com.champstart.recipeapp.ingredient.model.Ingredient;
import com.champstart.recipeapp.ingredient.repository.IngredientRepository;
import com.champstart.recipeapp.ingredient.service.IngredientService;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.champstart.recipeapp.ingredient.dto.mapper.IngredientMapper.mapToIngredientEntity;

@Service
@NoArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void createIngredient(Long id, IngredientDTO ingredientDTO) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        Ingredient ingredient = mapToIngredientEntity(ingredientDTO);
        recipe.ifPresent(ingredient::setRecipe);
        ingredientRepository.save(ingredient);
    }
}

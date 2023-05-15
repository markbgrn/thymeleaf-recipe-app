package com.champstart.recipeapp.recipe.service.impl;

import com.champstart.recipeapp.category.repository.CategoryRepository;
import com.champstart.recipeapp.exception.NotFoundException;
import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.ingredient.repository.IngredientRepository;
import com.champstart.recipeapp.ingredient.service.IngredientService;
import com.champstart.recipeapp.procedure.dto.ProcedureDTO;
import com.champstart.recipeapp.procedure.service.ProcedureService;
import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import com.champstart.recipeapp.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper.mapToRecipeDTO;
import static com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper.mapToRecipeEntity;

@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;

    @Override
    public RecipeDTO getRecipeById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            return mapToRecipeDTO(recipe);
        }
        throw new NotFoundException("Recipe not found with ID: " + id);
    }

    @Override
    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = mapToRecipeEntity(recipeDTO);
        return recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeDTO> findAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(RecipeMapper::mapToRecipeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateRecipe(RecipeDTO recipeDTO) {

    }


}

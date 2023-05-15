package com.champstart.recipeapp.recipe.service.impl;

import com.champstart.recipeapp.category.model.Category;
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
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper.mapToRecipeDTO;
import static com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper.mapToRecipeEntity;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository,
                             CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

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
    public Recipe createRecipe(Long id,RecipeDTO recipeDTO) {
        Category category = categoryRepository.findById(id).get();
        String email = SecurityUtil.getSessionUser();
        UserModel userModel = userRepository.findByEmail(email);
        Recipe recipe = mapToRecipeEntity(recipeDTO);
        recipe.setUser(userModel);
        recipe.setCategory(category);
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
        String email = SecurityUtil.getSessionUser();
        UserModel userModel = userRepository.findByEmail(email);
        Recipe recipe = mapToRecipeEntity(recipeDTO);
        recipe.setUser(userModel);
        recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }


}

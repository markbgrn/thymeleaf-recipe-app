package com.champstart.recipeapp.recipe.service.impl;

import com.champstart.recipeapp.category.repository.CategoryRepository;
import com.champstart.recipeapp.exception.NotFoundException;
import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.ingredient.dto.mapper.IngredientMapper;
import com.champstart.recipeapp.ingredient.model.Ingredient;
import com.champstart.recipeapp.procedure.dto.mapper.ProcedureMapper;
import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.dto.mapper.RecipeMapper;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import com.champstart.recipeapp.recipe.service.RecipeService;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final SecurityUtil securityUtil;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository,
                             CategoryRepository categoryRepository, SecurityUtil securityUtil) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.securityUtil = securityUtil;
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
    public void createRecipe(RecipeDTO recipeDTO) {
        Long user = securityUtil.getUserModel().getId();
        UserModel userId = userRepository.findById(user).get();
        Ingredient ingredient = new Ingredient();
        Recipe recipe = mapToRecipeEntity(recipeDTO);
        recipeDTO.getIngredients().forEach(ingredientDTO -> {
            ingredientDTO.setRecipe(recipe);
        });
        recipeDTO.getProcedures().forEach(procedureDTO -> {
            procedureDTO.setRecipe(recipe);
        });
        recipe.setUser(userId);
        recipe.setIngredients(recipeDTO.getIngredients()
                .stream()
                .map(IngredientMapper::mapToIngredientEntity)
                .collect(Collectors.toList()));
        recipe.setProcedures(recipeDTO.getProcedures()
                .stream()
                .map(ProcedureMapper::mapToProcedureEntity)
                .collect(Collectors.toList()));
        recipeRepository.save(recipe);
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

    @Override
    public List<Recipe> searchRecipes(String recipeName) {
        return recipeRepository.findByRecipeTitleContainingIgnoreCase(recipeName);
    }

    @Override
    public List<RecipeDTO> findByUserId(Long id) {
        List<Recipe> recipes = recipeRepository.findByUserId(id);
        return recipes.stream()
                .map(RecipeMapper::mapToRecipeDTO)
                .collect(Collectors.toList());
    }


}

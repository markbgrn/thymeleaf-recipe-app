//package com.champstart.recipeapp.recipe.service.impl;
//
//import com.champstart.recipeapp.ingredient.model.Ingredient;
//import com.champstart.recipeapp.ingredient.repository.IngredientRepository;
//import com.champstart.recipeapp.procedure.model.Procedure;
//import com.champstart.recipeapp.procedure.repository.ProcedureRepository;
//import com.champstart.recipeapp.recipe.dto.RecipeDTO;
//import com.champstart.recipeapp.recipe.model.Recipe;
//import com.champstart.recipeapp.recipe.repository.RecipeRepository;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//class RecipeServiceImplTest {
//    @Mock
//    private RecipeRepository recipeRepository;
//    @Mock
//    private IngredientRepository ingredientRepository;
//    @Mock
//    private ProcedureRepository procedureRepository;
//    @InjectMocks
//    private RecipeServiceImpl recipeService;
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    void testSaveRecipeWithIngredientsAndProcedures() {
//        // Mocking data
//        Recipe recipe = new Recipe();
//        recipe.setId(1L);
//        recipe.setRecipeTitle("Test Recipe");
//        recipe.setRecipeDescription("Test Description");
//
//        Ingredient ingredient1 = new Ingredient();
//        ingredient1.setId(1L);
//        ingredient1.setRecipeIngredients("Ingredient 1");
//
//        Ingredient ingredient2 = new Ingredient();
//        ingredient2.setId(2L);
//        ingredient2.setRecipeIngredients("Ingredient 2");
//
//        recipe.setIngredients(Arrays.asList(ingredient1, ingredient2));
//
//        Procedure procedure = new Procedure();
//        procedure.setId(1L);
//        procedure.setStep("Test Step 1");
//
//        Procedure procedure2 = new Procedure();
//        procedure2.setId(2L);
//        procedure2.setStep("Test Step 2");
//
//        recipe.setProcedures(Arrays.asList(procedure, procedure2));
//
//        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
//
//        // Call the method
//        RecipeDTO result = recipeService.getRecipeById(1L);
//
//        // Assert the result
//        assertNotNull(result);
//        assertEquals("Test Recipe", result.getRecipeTitle());
//        assertEquals("Test Description", result.getRecipeDescription());
//        assertNotNull(result.getIngredients());
//        assertEquals(2, result.getIngredients().size());
//        assertEquals(1L, result.getIngredients().get(0).getId());
//        assertEquals("Ingredient 1", result.getIngredients().get(0).getIngredients());
//        assertEquals(2L, result.getIngredients().get(1).getId());
//        assertEquals("Ingredient 2", result.getIngredients().get(1).getIngredients());
//        assertNotNull(result.getProcedures());
//        assertEquals(2, result.getProcedures().size());
//        assertEquals(1L, result.getProcedures().get(0).getId());
//        assertEquals("Test Step 1", result.getProcedures().get(0).getStep());
//        assertEquals(2L, result.getProcedures().get(1).getId());
//        assertEquals("Test Step 2", result.getProcedures().get(1).getStep());
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void testFindAllRecipes() {
//        // Prepare test data
//        Recipe recipe1 = new Recipe();
//        recipe1.setId(1L);
//        recipe1.setRecipeTitle("Recipe 1");
//        recipe1.setRecipeDescription("Description 1");
//
//        Ingredient ingredient1 = new Ingredient();
//        ingredient1.setId(1L);
//        ingredient1.setRecipeIngredients("Ingredient 1");
//        ingredient1.setRecipe(recipe1);
//        recipe1.getIngredients().add(ingredient1);
//
//        Procedure procedure1 = new Procedure();
//        procedure1.setId(1L);
//        procedure1.setStep("Procedure 1");
//        procedure1.setRecipe(recipe1);
//        recipe1.getProcedures().add(procedure1);
//
//        Recipe recipe2 = new Recipe();
//        recipe2.setId(2L);
//        recipe2.setRecipeTitle("Recipe 2");
//        recipe2.setRecipeDescription("Description 2");
//
//        Ingredient ingredient2 = new Ingredient();
//        ingredient2.setId(2L);
//        ingredient2.setRecipeIngredients("Ingredient 2");
//        ingredient2.setRecipe(recipe2);
//        recipe2.getIngredients().add(ingredient2);
//
//        Procedure procedure2 = new Procedure();
//        procedure2.setId(2L);
//        procedure2.setStep("Procedure 2");
//        procedure2.setRecipe(recipe2);
//        recipe2.getProcedures().add(procedure2);
//
//        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
//
//        when(recipeRepository.findAll()).thenReturn(recipes);
//
//        // Call the service method
//        List<RecipeDTO> recipeDTOs = recipeService.findAllRecipes();
//
//        // Assert the results
//        assertEquals(2, recipeDTOs.size());
//
//        RecipeDTO recipeDTO1 = recipeDTOs.get(0);
//        assertEquals(Long.valueOf(1L), recipeDTO1.getId());
//        assertEquals("Recipe 1", recipeDTO1.getRecipeTitle());
//        assertEquals("Description 1", recipeDTO1.getRecipeDescription());
//        assertEquals(1, recipeDTO1.getIngredients().size());
//        assertEquals(1, recipeDTO1.getProcedures().size());
//
//        RecipeDTO recipeDTO2 = recipeDTOs.get(1);
//        assertEquals(Long.valueOf(2L), recipeDTO2.getId());
//        assertEquals("Recipe 2", recipeDTO2.getRecipeTitle());
//        assertEquals("Description 2", recipeDTO2.getRecipeDescription());
//        assertEquals(1, recipeDTO2.getIngredients().size());
//        assertEquals(1, recipeDTO2.getProcedures().size());
//    }
//
//    @Test
//    public void testSearchRecipes() {
//        String recipeName = "chicken";
//        Recipe testRecipe1 = new Recipe();
//        Recipe testRecipe2 = new Recipe();
//        testRecipe1.setRecipeTitle("Fried Chicken");
//        testRecipe2.setRecipeTitle("Chicken sandwich");
//        List<Recipe> expectedRecipes = Arrays.asList(
//                testRecipe1,
//                testRecipe2
//
//        );
//
//        when(recipeRepository.findByRecipeTitleContainingIgnoreCase(anyString())).thenReturn(expectedRecipes);
//
//        List<Recipe> actualRecipes = recipeService.searchRecipes(recipeName);
//
//        assertEquals(expectedRecipes, actualRecipes);
//    }
//}
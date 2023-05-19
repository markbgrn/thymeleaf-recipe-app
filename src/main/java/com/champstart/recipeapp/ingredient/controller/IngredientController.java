package com.champstart.recipeapp.ingredient.controller;

import com.champstart.recipeapp.ingredient.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping("/ingredient/{id}/delete")
    public String deleteIngredients(@PathVariable("id")Long id) {
        ingredientService.deleteIngredient(id);
        return "view/recipe/recipe-edit";
    }
}

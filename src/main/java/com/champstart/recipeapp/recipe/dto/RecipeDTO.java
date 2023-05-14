package com.champstart.recipeapp.recipe.dto;

import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.procedure.dto.ProcedureDTO;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private Long id;
    private String recipeTitle;
    private String recipeDescription;
//    private CategoryDTO category; private Long categoryId;
    private List<IngredientDTO> ingredients;
    private List<ProcedureDTO> procedures;
//    private List<CommentDTO> comments;
}

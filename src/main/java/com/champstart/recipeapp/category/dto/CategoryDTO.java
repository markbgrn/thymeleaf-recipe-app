package com.champstart.recipeapp.category.dto;

import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO{
    private Long id;
//    @NotEmpty(message = "Category Should not be Empty.")
    private String categoryName;
    private List<RecipeDTO> recipes;
}

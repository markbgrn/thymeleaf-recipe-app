package com.champstart.recipeapp.recipe.dto;

import com.champstart.recipeapp.category.dto.CategoryDTO;
import com.champstart.recipeapp.category.model.Category;
import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.ingredient.dto.IngredientDTO;
import com.champstart.recipeapp.procedure.dto.ProcedureDTO;
import com.champstart.recipeapp.user.model.UserModel;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private Long id;
    private String recipeTitle;
    private String recipeDescription;
    private MultipartFile photo;
    private String photoPath;
    private UserModel user;
    private Category category;
    private List<IngredientDTO> ingredients;
    private List<ProcedureDTO> procedures;
    private List<CommentDTO> comments;
}

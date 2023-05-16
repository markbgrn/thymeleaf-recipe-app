package com.champstart.recipeapp.comment.dto;

import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    @NotEmpty(message = "comment should not be empty")
    private String comment;
    private Recipe recipe;
    private UserModel user;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}

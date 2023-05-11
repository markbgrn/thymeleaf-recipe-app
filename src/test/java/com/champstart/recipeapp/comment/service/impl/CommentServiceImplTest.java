package com.champstart.recipeapp.comment.service.impl;

import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.repository.CommentRepository;
import com.champstart.recipeapp.recipe.model.RecipeModel;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComment_ValidUserAndRecipe_ReturnsCommentModel() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String comment = "Great recipe!";
        Long recipeId = 1L;

        UserModel user = new UserModel();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        RecipeModel recipe = new RecipeModel();
        recipe.setId(recipeId);

        CommentModel commentModel = new CommentModel();
        commentModel.setId(1L);
        commentModel.setFirstName(firstName);
        commentModel.setLastName(lastName);
        commentModel.setComment(comment);
        commentModel.setCreatedOn(LocalDateTime.now());
        commentModel.setRecipe(recipe);

        when(commentRepository.findUserByFirstNameAndLastName(eq(firstName), eq(lastName)))
                .thenReturn(user);
        when(recipeRepository.findById(eq(recipeId))).thenReturn(Optional.of(recipe));
        when(commentRepository.save(any(CommentModel.class))).thenReturn(commentModel);

        // Act
        CommentModel createdComment = commentService.createComment(firstName, lastName, comment, recipeId);

        // Assert
        assertNotNull(createdComment);
        assertEquals(firstName, createdComment.getFirstName());
        assertEquals(lastName, createdComment.getLastName());
        assertEquals(comment, createdComment.getComment());
        assertNotNull(createdComment.getCreatedOn());
        assertEquals(recipe, createdComment.getRecipe());

    }

}
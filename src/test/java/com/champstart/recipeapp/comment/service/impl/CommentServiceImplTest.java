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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void createComment_ValidUserAndRecipe_ReturnsNewComment() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String commentText = "Great recipe!";
        Long recipeId = 123L;

        UserModel user = new UserModel();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        RecipeModel recipe = new RecipeModel();
        recipe.setId(recipeId);

        when(commentRepository.findUserByFirstNameAndLastName(firstName, lastName)).thenReturn(user);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        CommentModel newComment = commentService.createComment(firstName, lastName, commentText, recipeId);

        // Assert
        assertNotNull(newComment);
        assertEquals(firstName, newComment.getFirstName());
        assertEquals(lastName, newComment.getLastName());
        assertEquals(commentText, newComment.getComment());
        assertEquals(recipe, newComment.getRecipe());
        assertNotNull(newComment.getCreatedOn());
    }

    @Test
    void createComment_InvalidUser_ThrowsIllegalArgumentException() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String commentText = "Great recipe!";
        Long recipeId = 123L;

        when(commentRepository.findUserByFirstNameAndLastName(firstName, lastName)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                commentService.createComment(firstName, lastName, commentText, recipeId));
    }

    @Test
    void postComment_ValidComment_CommentSaved() {
        // Arrange
        CommentModel comment = new CommentModel();

        // Act
        commentService.postComment(comment);

        // Assert
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void getCommentsByRecipeId_ValidRecipeId_ReturnsComments() {
        // Arrange
        Long recipeId = 123L;
        CommentModel comment1 = new CommentModel();
        CommentModel comment2 = new CommentModel();
        List<CommentModel> expectedComments = Arrays.asList(comment1, comment2);

        when(commentRepository.findByRecipeId(recipeId)).thenReturn(expectedComments);

        // Act
        List<CommentModel> comments = commentService.getCommentsByRecipeId(recipeId);

        // Assert
        assertNotNull(comments);
        assertEquals(expectedComments.size(), comments.size());
        assertTrue(comments.contains(comment1));
        assertTrue(comments.contains(comment2));
    }
}

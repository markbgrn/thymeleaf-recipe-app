package com.champstart.recipeapp.comment.service.impl;

import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.repository.CommentRepository;
import com.champstart.recipeapp.recipe.model.Recipe;
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

        String firstName = "John";
        String lastName = "Doe";
        String commentText = "Great recipe!";
        Long recipeId = 123L;

        UserModel user = new UserModel();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        when(commentRepository.findUserByFullName(firstName, lastName)).thenReturn(user);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));


        CommentModel newComment = commentService.createComment(firstName, lastName, commentText, recipeId);


        assertNotNull(newComment);
        assertEquals(firstName, newComment.getFirstName());
        assertEquals(lastName, newComment.getLastName());
        assertEquals(commentText, newComment.getComment());
        assertEquals(recipe, newComment.getRecipe());
        assertNotNull(newComment.getCreatedOn());
    }

    @Test
    void createComment_InvalidUser_ThrowsIllegalArgumentException() {

        String firstName = "John";
        String lastName = "Doe";
        String commentText = "Great recipe!";
        Long recipeId = 123L;

        when(commentRepository.findUserByFullName(firstName, lastName)).thenReturn(null);


        assertThrows(IllegalArgumentException.class, () ->
                commentService.createComment(firstName, lastName, commentText, recipeId));
    }

    @Test
    void postComment_ValidComment_CommentSaved() {
        CommentModel comment = new CommentModel();

        commentService.postComment(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void updateComment_ValidComment_ReturnsUpdatedComment() {
        Long commentId = 123L;
        String updatedComment = "Updated comment";

        CommentModel comment = new CommentModel();
        comment.setId(commentId);
        comment.setComment("Old comment");
        comment.setCreatedOn(LocalDateTime.now());

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        CommentModel updated = commentService.updateComment(commentId, updatedComment);

        assertNotNull(updated);
        assertEquals(updatedComment, updated.getComment());
        assertEquals(comment.getCreatedOn(), updated.getCreatedOn());
    }

    @Test
    void updateComment_InvalidComment_ThrowsIllegalArgumentException() {
        Long commentId = 123L;
        String updatedComment = "Updated comment";

        doThrow(IllegalArgumentException.class).when(commentRepository).save(mock(CommentModel.class));
        assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(commentId, updatedComment));
    }

    @Test
    void testGetCommentsByRecipeId(){
        Long recipeId = 1L;
        Recipe recipeModel = new Recipe();
        recipeModel.setId(recipeId);


        CommentModel commentModel1 = new CommentModel();
        commentModel1.setId(1L);
        commentModel1.setFirstName("John");
        commentModel1.setLastName("Doe");
        commentModel1.setComment("John's comment");
        commentModel1.setCreatedOn(LocalDateTime.now());
        commentModel1.setRecipe(recipeModel);

        CommentModel commentModel2 = new CommentModel();
        commentModel2.setId(2L);
        commentModel2.setFirstName("Jane");
        commentModel2.setLastName("Poe");
        commentModel2.setComment("Jane's comment");
        commentModel2.setCreatedOn(LocalDateTime.now());
        commentModel2.setRecipe(recipeModel);


        List<CommentModel> expectedComments = Arrays.asList(
                commentModel1,
                commentModel2
        );
        when(commentRepository.findByRecipeId(recipeId)).thenReturn(expectedComments);

        List<CommentModel> actualComments = commentService.getCommentsByRecipeId(recipeId);

        assertEquals(expectedComments, actualComments);
        verify(commentRepository, times(1)).findByRecipeId(recipeId);
    }

}


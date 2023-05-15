package com.champstart.recipeapp.comment.service.impl;

import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.repository.CommentRepository;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    @Test
//    void saveComment_ValidUser_ReturnsCommentModel() {
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setComment("Test Comment");
//        commentDTO.setUser(new UserModel());
//        commentDTO.setRecipe(new Recipe());
//
//        UserModel sessionUser = new UserModel();
//        sessionUser.setFirstName("John"); // Set the first name of the session user
//
//        // Mock the SecurityContextHolder and Authentication objects
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Authentication authentication = Mockito.mock(Authentication.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//
//        // Set the mock user's first name in the Authentication object
//        Mockito.when(authentication.getPrincipal()).thenReturn(sessionUser.getFirstName());
//
//        SecurityContextHolder.setContext(securityContext);
//
//        CommentModel savedComment = new CommentModel();
//        when(commentRepository.save(any(CommentModel.class))).thenReturn(savedComment);
//
//        CommentModel result = commentService.saveComment(commentDTO);
//
//        assertNotNull(result);
//        assertEquals(savedComment, result);
//        assertEquals("Test Comment", result.getComment());
//    }




//    @Test
//    void saveComment_InvalidUser_ThrowsIllegalArgumentException() {
//
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setUser(new UserModel());
//        commentDTO.setRecipe(new Recipe());
//
//        when(SecurityUtil.getSessionUser()).thenReturn("testUser");
//        when(userRepository.findByFirstName("testUser")).thenReturn(null);
//
//
//        assertThrows(IllegalArgumentException.class, () -> commentService.saveComment(commentDTO));
//    }

    @Test
    void updateComment_ExistingComment_UpdatesComment() {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setComment("Updated Comment");

        CommentModel existingComment = new CommentModel();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(existingComment);


        commentService.updateComment(commentDTO);


        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(existingComment);
        assertEquals("Updated Comment", existingComment.getComment());
        assertNotNull(existingComment.getUpdatedOn());
    }

    @Test
    void updateComment_NonExistingComment_ThrowsIllegalArgumentException() {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setComment("Updated Comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(commentDTO));
    }

    @Test
    void deleteComment_ExistingComment_DeletesComment() {

        CommentModel existingComment = new CommentModel();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(existingComment));

        commentService.deleteComment(1L);


        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).delete(existingComment);
    }

    @Test
    void deleteComment_NonExistingComment_ThrowsIllegalArgumentException() {

        when(commentRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> commentService.deleteComment(1L));
    }

    @Test
    void getCommentsByRecipeId_ExistingRecipe_ReturnsListOfComments() {
        Long recipeId = 1L;

        Recipe recipeModel = new Recipe();
        recipeModel.setId(recipeId);

        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO1.setId(2L);
        commentDTO1.setUser(new UserModel());
        commentDTO1.setComment("Comment 1");
        commentDTO1.setCreatedOn(LocalDateTime.now());
        commentDTO1.setRecipe(new Recipe());

        CommentDTO commentDTO2 = new CommentDTO();
        commentDTO2.setId(3L);
        commentDTO2.setUser(new UserModel());
        commentDTO2.setComment("Comment 2");
        commentDTO2.setCreatedOn(LocalDateTime.now());
        commentDTO2.setRecipe(new Recipe());

        List<CommentDTO> expectedComments = Arrays.asList(commentDTO1, commentDTO2);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeModel));
        when(commentRepository.findByRecipeId(recipeId)).thenReturn(expectedComments);

        List<CommentDTO> actualComments = commentService.getCommentsByRecipeId(recipeId);

        assertEquals(expectedComments, actualComments);
        verify(commentRepository, times(1)).findByRecipeId(recipeId);
    }


    @Test
    void getCommentsByRecipeId_NonExistingRecipe_ThrowsIllegalArgumentException() {

        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> commentService.getCommentsByRecipeId(1L));
    }
}
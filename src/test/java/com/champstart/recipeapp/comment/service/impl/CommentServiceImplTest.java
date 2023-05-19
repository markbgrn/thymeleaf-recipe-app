package com.champstart.recipeapp.comment.service.impl;

import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.comment.dto.CommentMapper;
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
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveComment_ShouldSaveCommentModel() {
        Long recipeId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("Test comment");

        String user = "user@example.com";
        UserModel userModel = new UserModel();
        userModel.setEmail(user);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        Mockito.when(SecurityUtil.getSessionUser()).thenReturn(user);

        Mockito.when(userRepository.findByEmail(user)).thenReturn(userModel);

        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        commentService.saveComment(recipeId, commentDTO);

        Mockito.verify(commentRepository).save(Mockito.any(CommentModel.class));
    }


    @Test
    void updateComment_ExistingCommentId_ShouldUpdateComment() {

        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("Updated comment");

        CommentModel existingComment = new CommentModel();
        existingComment.setId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));


        commentService.updateComment(commentId, commentDTO);


        verify(commentRepository).save(existingComment);
        assertEquals(commentDTO.getComment(), existingComment.getComment());
    }

    @Test
    void updateComment_NonExistingCommentId_ShouldThrowException() {

        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("Updated comment");

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(commentId, commentDTO));
    }
    @Test
    void deleteComment_ExistingCommentId_ShouldDeleteComment() {

        Long commentId = 1L;
        CommentModel existingComment = new CommentModel();
        existingComment.setId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));


        commentService.deleteComment(commentId);


        verify(commentRepository).delete(existingComment);
    }

    @Test
    void deleteComment_NonExistingCommentId_ShouldThrowException() {

        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> commentService.deleteComment(commentId));
    }

    @Test
    void getCommentsByRecipeId_ExistingRecipeId_ShouldReturnComments() {

        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        when(commentRepository.findByRecipeId(recipeId)).thenReturn(Collections.singletonList(new CommentDTO()));


        List<CommentDTO> comments = commentService.getCommentsByRecipeId(recipeId);


        assertEquals(1, comments.size());
    }

    @Test
    void getCommentsByRecipeId_NonExistingRecipeId_ShouldThrowException() {

        Long recipeId = 1L;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> commentService.getCommentsByRecipeId(recipeId));
    }

    @Test
    void findAllComments_ShouldReturnAllComments() {

        List<CommentModel> commentList = Arrays.asList(new CommentModel(), new CommentModel());

        when(commentRepository.findAll(any(Sort.class))).thenReturn(commentList);


        List<CommentDTO> comments = commentService.findAllComments();


        assertEquals(commentList.size(), comments.size());
    }

    @Test
    void getCommentById_ExistingCommentId_ShouldReturnComment() {

        Long commentId = 1L;
        CommentModel comment = new CommentModel();
        comment.setId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));


        List<CommentDTO> result = commentService.getCommentById(commentId);


        assertEquals(1, result.size());
        assertEquals(commentId, result.get(0).getId());
    }

    @Test
    void getCommentById_NonExistingCommentId_ShouldThrowException() {

        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> commentService.getCommentById(commentId));
    }



}





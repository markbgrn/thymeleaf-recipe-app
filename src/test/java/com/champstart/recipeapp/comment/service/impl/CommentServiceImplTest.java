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
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void saveComment_ValidInput_CommentSaved() {
//        // Arrange
//        Long id = 1L;
//        String user = "testuser@example.com";
//        CommentDTO commentDTO = new CommentDTO();
//        UserModel userModel = new UserModel();
//        Recipe recipe = new Recipe();
//        CommentModel savedComment = new CommentModel();
//
//        when(SecurityUtil.getSessionUser()).thenReturn(user);
//        when(userRepository.findByEmail(user)).thenReturn(userModel);
//        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
//        when(commentRepository.save(any(CommentModel.class))).thenReturn(savedComment);
//
//        // Act
//        CommentModel result = commentService.saveComment(id, commentDTO);
//
//        // Assert
//        verify(SecurityUtil).getSessionUser();
//        verify(userRepository).findByEmail(user);
//        verify(recipeRepository).findById(id);
//        verify(commentRepository).save(any(CommentModel.class));
//
//        assertNotNull(result);
//        // Additional assertions can be made on the result object
//    }

//    @Test
//    public void saveComment_InvalidUser_ThrowsIllegalArgumentException() {
//        // Arrange
//        Long id = 1L;
//        String user = "testuser@example.com";
//        CommentDTO commentDTO = new CommentDTO();
//        UserModel userModel = null;
//        Recipe recipe = new Recipe();
//
//        when(SecurityUtil.getSessionUser()).thenReturn(user);
//        when(userRepository.findByEmail(user)).thenReturn(userModel);
//        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
//
//        // Act and Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            commentService.saveComment(id, commentDTO);
//        });
//
//        verify(SecurityUtil).getSessionUser();
//        verify(userRepository).findByEmail(user);
//        verify(recipeRepository).findById(id);
//        verifyNoInteractions(commentRepository);
//    }

    @Test
    public void updateComment_ExistingComment_CommentUpdated() {
        // Arrange
        Long id = 1L;
        CommentDTO commentDTO = new CommentDTO();
        CommentModel existingComment = new CommentModel();
        existingComment.setId(id);
        existingComment.setComment("Old comment");

        when(commentRepository.findById(id)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(CommentModel.class))).thenReturn(existingComment);

        // Act
        commentService.updateComment(id, commentDTO);

        // Assert
        verify(commentRepository).findById(id);
        verify(commentRepository).save(existingComment);

        assertEquals(commentDTO.getComment(), existingComment.getComment());
    }

    @Test
    public void updateComment_NonExistingComment_ThrowsIllegalArgumentException() {
        // Arrange
        Long id = 1L;
        CommentDTO commentDTO = new CommentDTO();

        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            commentService.updateComment(id, commentDTO);
        });

        verify(commentRepository).findById(id);
        verifyNoInteractions(commentRepository);
    }

    @Test
    public void deleteComment_ExistingComment_CommentDeleted() {
        // Arrange
        Long id = 1L;
        CommentModel existingComment = new CommentModel();
        existingComment.setId(id);

        when(commentRepository.findById(id)).thenReturn(Optional.of(existingComment));

        // Act
        commentService.deleteComment(id);

        // Assert
        verify(commentRepository).findById(id);
        verify(commentRepository).delete(existingComment);
    }

    @Test
    public void deleteComment_NonExistingComment_ThrowsIllegalArgumentException() {
        // Arrange
        Long id = 1L;

        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            commentService.deleteComment(id);
        });

        verify(commentRepository).findById(id);
        verifyNoInteractions(commentRepository);
    }

//    @Test
//    public void getCommentsByRecipeId_ExistingRecipe_ReturnsCommentDTOList() {
//        // Arrange
//        Long recipeId = 1L;
//        Recipe recipe = new Recipe();
//        recipe.setId(recipeId);
//        CommentModel comment1 = new CommentModel();
//        comment1.setId(1L);
//        CommentModel comment2 = new CommentModel();
//        comment2.setId(2L);
//        List<CommentModel> comments = List.of(comment1, comment2);
//        List<CommentDTO> expectedDTOs = comments.stream()
//                .map(CommentMapper::mapToCommentDTO)
//                .collect(Collectors.toList());
//
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
//        when(commentRepository.findByRecipeId(recipeId)).thenReturn(expectedDTOs);
//
//        // Act
//        List<CommentDTO> result = commentService.getCommentsByRecipeId(recipeId);
//
//        // Assert
//        verify(recipeRepository).findById(recipeId);
//        verify(commentRepository).findByRecipeId(recipeId);
//
//        assertEquals(expectedDTOs, result);
//    }

    @Test
    public void getCommentsByRecipeId_NonExistingRecipe_ThrowsIllegalArgumentException() {
        // Arrange
        Long recipeId = 1L;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            commentService.getCommentsByRecipeId(recipeId);
        });

        verify(recipeRepository).findById(recipeId);
        verifyNoInteractions(commentRepository);
    }

//    @Test
//    public void findAllComments_CommentsExist_ReturnsCommentDTOList() {
//        // Arrange
//        CommentModel comment1 = new CommentModel();
//        comment1.setId(1L);
//        CommentModel comment2 = new CommentModel();
//        comment2.setId(2L);
//        assertArrayEquals(Collections.singletonList(expectedDTO).toArray(), result.toArray());
//
//        List<CommentDTO> expectedDTOs = comments.stream()
//                .map(CommentMapper::mapToCommentDTO)
//                .collect(Collectors.toList());
//
//        when(commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"))).thenReturn(comments);
//
//        // Act
//        List<CommentDTO> result = commentService.findAllComments();
//
//        // Assert
//        verify(commentRepository).findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
//
//        assertEquals(expectedDTOs, result);
//    }

    @Test
    public void getCommentById_ExistingComment_ReturnsCommentDTOList() {
        // Arrange
        Long commentId = 1L;
        CommentModel comment = new CommentModel();
        comment.setId(commentId);
        CommentDTO expectedDTO = CommentMapper.mapToCommentDTO(comment);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Act
        List<CommentDTO> result = commentService.getCommentById(commentId);

        // Assert
        verify(commentRepository).findById(commentId);

        assertEquals(Collections.singletonList(expectedDTO), result);
    }

    @Test
    public void getCommentById_NonExistingComment_ThrowsEntityNotFoundException() {
        // Arrange
        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> {
            commentService.getCommentById(commentId);
        });

        verify(commentRepository).findById(commentId);
        verifyNoInteractions(commentRepository);
    }
}

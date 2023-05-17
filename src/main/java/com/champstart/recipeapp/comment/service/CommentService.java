package com.champstart.recipeapp.comment.service;

import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.comment.model.CommentModel;

import java.util.List;

public interface CommentService {
    CommentModel saveComment(Long id, CommentDTO commentDTO);
    void updateComment(Long id, CommentDTO commentDTO); // New method to update a comment
    void deleteComment(Long Id);
    List<CommentDTO> getCommentsByRecipeId(Long recipeId);

    List<CommentDTO> getCommentById(Long id);

    List <CommentDTO> findAllComments();
}
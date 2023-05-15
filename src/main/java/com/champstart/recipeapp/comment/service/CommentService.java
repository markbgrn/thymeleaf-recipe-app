package com.champstart.recipeapp.comment.service;

import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.comment.model.CommentModel;

import java.util.List;

public interface CommentService {
    CommentModel saveComment(CommentDTO commentDTO);
    void updateComment(CommentDTO commentDTO);
    void deleteComment(Long Id);
    List<CommentDTO> getCommentsByRecipeId(Long recipeId);
}
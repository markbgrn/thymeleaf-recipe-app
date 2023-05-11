package com.champstart.recipeapp.comment.service;

import com.champstart.recipeapp.comment.model.CommentModel;

import java.util.List;

public interface CommentService {
    CommentModel createComment(String firstName, String lastName, String comment, Long recipeId);
    void postComment(CommentModel comment);
    List<CommentModel> getCommentsByRecipeId(Long recipeId);
}
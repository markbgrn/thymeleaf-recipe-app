package com.champstart.recipeapp.comment.dto;


import com.champstart.recipeapp.comment.model.CommentModel;

public class CommentMapper {
    public static CommentModel mapToCommentEntity(CommentDTO commentDTO){
        return CommentModel.builder()
                .id(commentDTO.getId())
                .comment(commentDTO.getComment())
                .createdOn(commentDTO.getCreatedOn())
                .updatedOn(commentDTO.getUpdatedOn())
                .recipe(commentDTO.getRecipe())
                .users(commentDTO.getUser())
                .build();
    }
    public static CommentDTO mapToCommentDTO(CommentModel commentModel){
        return CommentDTO.builder()
                .id(commentModel.getId())
                .comment(commentModel.getComment())
                .createdOn(commentModel.getCreatedOn())
                .updatedOn(commentModel.getUpdatedOn())
                .recipe(commentModel.getRecipe())
                .user(commentModel.getUsers())
                .build();
    }
}



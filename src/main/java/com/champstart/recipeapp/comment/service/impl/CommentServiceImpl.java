package com.champstart.recipeapp.comment.service.impl;

import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.repository.CommentRepository;
import com.champstart.recipeapp.comment.service.CommentService;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.champstart.recipeapp.comment.dto.CommentMapper.mapToCommentEntity;


@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              RecipeRepository recipeRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public CommentModel saveComment(CommentDTO commentDTO) {
        String user = SecurityUtil.getSessionUser();
        UserModel firstName = userRepository.findByEmail(user);

        if (firstName == null) {
            throw new IllegalArgumentException("Invalid user");
        }

        CommentModel commentModel = mapToCommentEntity(commentDTO);
        commentModel.setUsers(firstName);
        return commentRepository.save(commentModel);
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        CommentModel commentModel = commentRepository.findById(commentDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        commentModel.setComment(commentDTO.getComment());
        commentModel.setUpdatedOn(LocalDateTime.now());

        commentRepository.save(commentModel);

    }

    @Override
    public void deleteComment(Long id) {
        CommentModel commentModel = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        commentRepository.delete(commentModel);

    }

    @Override
    public List<CommentDTO> getCommentsByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        return commentRepository.findByRecipeId(recipeId);
    }

}
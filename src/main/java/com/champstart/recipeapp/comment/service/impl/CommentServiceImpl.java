package com.champstart.recipeapp.comment.service.impl;

import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.comment.dto.CommentMapper;
import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.repository.CommentRepository;
import com.champstart.recipeapp.comment.service.CommentService;
import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import com.champstart.recipeapp.recipe.service.RecipeService;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import com.champstart.recipeapp.user.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.champstart.recipeapp.comment.dto.CommentMapper.mapToCommentEntity;


@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    private final RecipeService recipeService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              RecipeRepository recipeRepository, RecipeService recipeService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    @Override
    public CommentModel saveComment(Long id, CommentDTO commentDTO) {
        String user = SecurityUtil.getSessionUser();
        UserModel firstName = userRepository.findByEmail(user);
        Recipe recipe = recipeRepository.findById(id).get();

        if (firstName == null) {
            throw new IllegalArgumentException("Invalid user");
        }

        CommentModel commentModel = mapToCommentEntity(commentDTO);
        commentModel.setUsers(firstName);
        commentModel.setRecipe(recipe);
        return commentRepository.save(commentModel);
    }


    @Transactional
    @Override
    public void updateComment(Long id, CommentDTO commentDTO) {
        Optional<CommentModel> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            CommentModel comment = optionalComment.get();
            comment.setComment(commentDTO.getComment());
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Comment not found with id: " + id);
        }
    }

    @Transactional
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

    @Override
    public List<CommentDTO> findAllComments() {
        List<CommentModel> comments = commentRepository.findAll(Sort.by(Sort.Direction.DESC,"createdOn"));
        return comments.stream()
                .map(CommentMapper :: mapToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getCommentById(Long id) {
        CommentModel comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        return Collections.singletonList(CommentMapper.mapToCommentDTO(comment));
    }



}
package com.champstart.recipeapp.comment.service.impl;

import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.repository.CommentRepository;
import com.champstart.recipeapp.comment.service.CommentService;
import com.champstart.recipeapp.recipe.model.RecipeModel;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public CommentModel createComment(String firstName, String lastName, String comment, Long recipeId) {
        UserModel user = commentRepository.findUserByFullName(firstName, lastName);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        RecipeModel recipeOptional = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId));

        CommentModel newComment = new CommentModel();
        newComment.setRecipe(recipeOptional);
        newComment.setComment(comment);
        newComment.setCreatedOn(LocalDateTime.now());
        newComment.setFirstName(firstName);
        newComment.setLastName(lastName);

        return newComment;
    }
    @Override
    public void postComment(CommentModel comment) {
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Optional<CommentModel> commentOptional = Optional.ofNullable(commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId)));

        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentModel updateComment(Long commentId, String updatedComment) {
        CommentModel comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));

        comment.setComment(updatedComment);
        comment.setCreatedOn(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Override
    public List<CommentModel> getCommentsByRecipeId(Long recipeId) {
        return commentRepository.findByRecipeId(recipeId);
    }
}
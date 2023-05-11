package com.champstart.recipeapp.comment.controller;

import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipes/{recipeId}/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public String displayComments(Long recipeId, Model model){
        List<CommentModel> comments = commentService.getCommentsByRecipeId(recipeId);
        model.addAttribute("comments", comments);
        return null;
    }
}

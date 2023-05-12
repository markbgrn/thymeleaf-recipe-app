package com.champstart.recipeapp.comment.controller;

import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public String createComment(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("comment") String comment,
            @RequestParam("recipeId") Long recipeId
    ) {
        commentService.createComment(firstName, lastName, comment, recipeId);
        return "redirect:/comments";
    }

    @GetMapping("/{recipeId}")
    public String getCommentsByRecipeId(
            @PathVariable("recipeId") Long recipeId,
            Model model
    ) {
        List<CommentModel> comments = commentService.getCommentsByRecipeId(recipeId);
        model.addAttribute("comments", comments);
        return "comments";
    }
}

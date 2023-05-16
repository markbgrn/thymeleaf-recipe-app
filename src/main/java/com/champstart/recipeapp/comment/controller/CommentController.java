package com.champstart.recipeapp.comment.controller;

import com.champstart.recipeapp.comment.dto.CommentDTO;
import com.champstart.recipeapp.comment.service.CommentService;
import com.champstart.recipeapp.recipe.dto.RecipeDTO;
import com.champstart.recipeapp.recipe.service.RecipeService;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.security.SecurityUtil;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.champstart.recipeapp.user.security.SecurityUtil.*;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    private final RecipeService recipeService;

    private final SecurityUtil securityUtil;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, RecipeService recipeService, SecurityUtil securityUtil) {
        this.commentService = commentService;
        this.userService = userService;

        this.recipeService = recipeService;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/comments/save/{recipeID}")
    public String saveComment(@Valid @ModelAttribute("comment") CommentDTO commentDTO, BindingResult bindingResult, @PathVariable Long recipeID, Model model){
        UserModel user = new UserModel();
        String firstName = getSessionUser();
        model.addAttribute("firstName", securityUtil.getUserModel().getFirstName());
        model.addAttribute("lastName", securityUtil.getUserModel().getLastName());
        model.addAttribute("comment", commentDTO);
        if (recipeID != null) {
            commentService.saveComment(recipeID, commentDTO);
        } else {
            System.out.println("error");
        }
        RecipeDTO recipeDTO = recipeService.getRecipeById(recipeID);
        model.addAttribute("recipe", recipeDTO);
        return "view/recipe/recipe-detail";
    }


    @PutMapping("/comments/{id}")
    public String updateComment(@PathVariable("id") Long id, @ModelAttribute("commentDTO") CommentDTO commentDTO, Model model){
        commentDTO.setId(id);
        commentService.updateComment(commentDTO);
        return "view/recipe/recipe-detail";
    }

    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return "view/recipe/recipe-detail";
    }

    @GetMapping("/comments")
    public String getCommentsByRecipeid(@PathVariable("recipeId") Long recipeId, Model model){
        UserModel user = new UserModel();
        List<CommentDTO> comments = commentService.findAllComments();
        String firstName = getSessionUser();
        model.addAttribute("firstName", securityUtil.getUserModel().getFirstName());
        model.addAttribute("lastName", securityUtil.getUserModel().getLastName());
        model.addAttribute("userComment", comments);
        return "view/recipe/recipe-detail";
    }

}
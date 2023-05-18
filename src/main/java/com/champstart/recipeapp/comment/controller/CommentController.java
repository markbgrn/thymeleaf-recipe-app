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
import java.security.Principal;
import java.time.LocalDateTime;
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
        return "redirect:/recipes/" + recipeDTO.getId();
    }


    @PostMapping("/comments/update/{id}")
    public String updateComment(@PathVariable("id") Long id, @Valid @ModelAttribute("comment") CommentDTO commentDTO, Model model) {
        List<CommentDTO> comments = commentService.getCommentById(id);
        CommentDTO comment = comments.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (comment == null) {
            System.out.println("User is not authorized to edit the comment");
        }
        if (securityUtil.getUserModel().getId() != comment.getUser().getId()) {
            System.out.println("User is not authorized to edit the comment");
            Long recipeId = comment.getRecipe().getId();
            return "redirect:/home";
        }

        UserModel user = new UserModel();
        String email = SecurityUtil.getSessionUser();
        user = userService.findByEmail(email);
        model.addAttribute("user", user);

        comment.setUpdatedOn(LocalDateTime.now());
        comment.setComment(commentDTO.getComment());

        commentService.updateComment(id, commentDTO);

        Long recipeId = comment.getRecipe().getId();
        return "redirect:/recipes/" + recipeId;
    }


    @PostMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id, Model model) {
        List<CommentDTO> comments = commentService.getCommentById(id);
        CommentDTO comment = comments.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (comment == null) {
            System.out.println("User is not authorized to delete the comment");
        }
        if (securityUtil.getUserModel().getId() != comment.getUser().getId()) {
            System.out.println("User is not authorized to delete the comment");
            Long recipeId = comment.getRecipe().getId();
            return "redirect:/home";
        }
        commentService.deleteComment(id);
        Long recipeId = comment.getRecipe().getId();
        return "redirect:/recipes/" + recipeId;
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
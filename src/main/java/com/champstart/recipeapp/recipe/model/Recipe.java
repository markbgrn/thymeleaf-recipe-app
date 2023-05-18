package com.champstart.recipeapp.recipe.model;

import com.champstart.recipeapp.category.model.Category;
import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.ingredient.model.Ingredient;
import com.champstart.recipeapp.procedure.model.Procedure;
import com.champstart.recipeapp.user.model.UserModel;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String recipeTitle;
    private String recipeDescription;
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    @OneToMany(mappedBy = "recipe", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Ingredient> ingredients = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Procedure> procedures = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<CommentModel> comments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserModel user;
}

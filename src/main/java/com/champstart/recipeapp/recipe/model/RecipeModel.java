package com.champstart.recipeapp.recipe.model;

import com.champstart.recipeapp.category.model.CategoryModel;
import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.ingredient.model.IngredientModel;
import com.champstart.recipeapp.procedure.model.ProcedureModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipes")
public class RecipeModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "recipe_id")
    private Long id;
    private String recipeTitle;
    private String recipeDescription;
    @OneToOne(mappedBy = "recipe", cascade = ALL, fetch = LAZY, optional = false)
    private CategoryModel categoryModel;
    @OneToOne(mappedBy = "recipe", cascade = ALL, fetch = LAZY, optional = false)
    private IngredientModel ingredientModel;
    @OneToOne(mappedBy = "recipe", cascade = ALL, fetch = LAZY, optional = false)
    private ProcedureModel procedureModel;
    @OneToMany(mappedBy = "recipe")
    private List<CommentModel> comments;
}

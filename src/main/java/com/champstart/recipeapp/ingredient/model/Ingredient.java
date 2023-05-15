package com.champstart.recipeapp.ingredient.model;

import com.champstart.recipeapp.recipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String recipeIngredients;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id",nullable = false)
    private Recipe recipe;
}

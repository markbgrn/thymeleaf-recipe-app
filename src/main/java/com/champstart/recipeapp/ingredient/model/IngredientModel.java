package com.champstart.recipeapp.ingredient.model;

import com.champstart.recipeapp.recipe.model.RecipeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class IngredientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeIngredient;
    @ManyToOne(fetch = FetchType.EAGER)
    private RecipeModel recipeModel;

}

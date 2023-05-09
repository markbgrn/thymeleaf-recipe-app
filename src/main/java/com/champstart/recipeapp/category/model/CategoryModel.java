package com.champstart.recipeapp.category.model;

import com.champstart.recipeapp.recipe.model.RecipeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private RecipeModel recipeModel;
}

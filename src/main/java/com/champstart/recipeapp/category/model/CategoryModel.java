package com.champstart.recipeapp.category.model;

import com.champstart.recipeapp.recipe.model.RecipeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeCategoryName;
    private String recipeCategoryDescription;
    @ManyToOne(fetch = FetchType.EAGER)
    private RecipeModel recipeModel;
}

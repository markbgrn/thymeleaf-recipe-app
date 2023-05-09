package com.champstart.recipeapp.recipe.model;

import com.champstart.recipeapp.category.model.CategoryModel;
import com.champstart.recipeapp.ingredient.model.IngredientModel;
import com.champstart.recipeapp.procedure.model.ProcedureModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class RecipeModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String recipeTitle;
    private String recipeDescription;
    @OneToOne(mappedBy = "recipeModel", cascade = ALL, fetch = LAZY, optional = false)
    private CategoryModel categoryModel;
    @OneToOne(mappedBy = "recipeModel", cascade = ALL, fetch = LAZY, optional = false)
    private IngredientModel ingredientModel;
    @OneToOne(mappedBy = "recipeModel", cascade = ALL, fetch = LAZY, optional = false)
    private ProcedureModel procedureModel;
}

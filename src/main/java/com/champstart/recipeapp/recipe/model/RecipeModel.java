package com.champstart.recipeapp.recipe.model;

import com.champstart.recipeapp.category.model.CategoryModel;
import com.champstart.recipeapp.ingredient.model.IngredientModel;
import com.champstart.recipeapp.procedure.model.ProcedureModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class RecipeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeTitle;
    private String recipeDescription;
    @OneToMany(mappedBy = "recipeModel", cascade = CascadeType.ALL)
    private List<CategoryModel> categoryModels = new ArrayList<>();
    @OneToMany(mappedBy = "recipeModel", cascade = CascadeType.ALL)
    private List<IngredientModel> ingredientModels = new ArrayList<>();
    @OneToMany(mappedBy = "recipeModel", cascade = CascadeType.ALL)
    private List<ProcedureModel> procedureModels = new ArrayList<>();
}

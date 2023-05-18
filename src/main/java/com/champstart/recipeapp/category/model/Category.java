package com.champstart.recipeapp.category.model;

import com.champstart.recipeapp.recipe.model.Recipe;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String categoryName;
    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes = new ArrayList<>();
}

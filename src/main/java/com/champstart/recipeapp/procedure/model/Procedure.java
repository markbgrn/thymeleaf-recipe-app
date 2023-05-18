package com.champstart.recipeapp.procedure.model;

import com.champstart.recipeapp.recipe.model.Recipe;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "procedures")
public class Procedure {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String step;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
}

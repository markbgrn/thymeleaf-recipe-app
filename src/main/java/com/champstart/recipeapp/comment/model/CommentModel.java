package com.champstart.recipeapp.comment.model;
import com.champstart.recipeapp.recipe.model.RecipeModel;
import com.champstart.recipeapp.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment", length = 4000, nullable = false)
    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userModel;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeModel recipeModel;

    // Add profile picture association here

    @Transient
    private String firstName;

    @Transient
    private String lastName;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    // Get the first name from the associated user
    @PostLoad
    private void populateName() {
        if (userModel != null) {
            firstName = userModel.getFirstName();
            lastName = userModel.getLastName();
        }
    }
}


package com.champstart.recipeapp.comment.repository;

import com.champstart.recipeapp.comment.model.CommentModel;
import com.champstart.recipeapp.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    List<CommentModel> findByRecipeId(Long recipeId);
    UserModel findUserByFirstNameAndLastName(String firstName, String lastName);
}

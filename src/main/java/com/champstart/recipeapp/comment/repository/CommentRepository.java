package com.champstart.recipeapp.comment.repository;

import com.champstart.recipeapp.comment.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    // custom query methods
}

package com.champstart.recipeapp.user.repository;

import com.champstart.recipeapp.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByVerificationId(String verificationId);

    UserModel findByEmail(String email);

   UserModel findByFirstName(String firstName);

   UserModel findFirstByEmail(String email);
}

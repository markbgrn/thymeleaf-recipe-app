package com.champstart.recipeapp.procedure.repository;

import com.champstart.recipeapp.procedure.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
}

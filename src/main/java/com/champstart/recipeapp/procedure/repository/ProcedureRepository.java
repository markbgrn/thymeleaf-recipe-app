package com.champstart.recipeapp.procedure.repository;

import com.champstart.recipeapp.procedure.model.ProcedureModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<ProcedureModel, Long> {
}

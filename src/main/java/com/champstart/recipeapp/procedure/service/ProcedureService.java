package com.champstart.recipeapp.procedure.service;

import com.champstart.recipeapp.procedure.dto.ProcedureDTO;

public interface ProcedureService {
    void createProcedure(Long id,ProcedureDTO procedureDTO);
}

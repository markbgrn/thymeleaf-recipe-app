package com.champstart.recipeapp.procedure.dto.mapper;

import com.champstart.recipeapp.procedure.dto.ProcedureDTO;
import com.champstart.recipeapp.procedure.model.Procedure;

public class ProcedureMapper {
    public static Procedure mapToProcedureEntity(ProcedureDTO procedureDTO) {
        return Procedure.builder()
                .id(procedureDTO.getId())
                .step(procedureDTO.getStep())
                .recipe(procedureDTO.getRecipe())
                .build();
    }
    public static ProcedureDTO mapToProcedureDTO(Procedure procedure) {
        return ProcedureDTO.builder()
                .id(procedure.getId())
                .step(procedure.getStep())
                .recipe(procedure.getRecipe())
                .build();
    }
}


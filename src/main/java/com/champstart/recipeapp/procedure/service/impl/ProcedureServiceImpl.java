package com.champstart.recipeapp.procedure.service.impl;

import com.champstart.recipeapp.procedure.dto.ProcedureDTO;
import com.champstart.recipeapp.procedure.dto.mapper.ProcedureMapper;
import com.champstart.recipeapp.procedure.model.Procedure;
import com.champstart.recipeapp.procedure.repository.ProcedureRepository;
import com.champstart.recipeapp.procedure.service.ProcedureService;
import com.champstart.recipeapp.recipe.model.Recipe;
import com.champstart.recipeapp.recipe.repository.RecipeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.champstart.recipeapp.procedure.dto.mapper.ProcedureMapper.mapToProcedureEntity;

@Service
@NoArgsConstructor
public class ProcedureServiceImpl implements ProcedureService {
    private ProcedureRepository procedureRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    public ProcedureServiceImpl(ProcedureRepository procedureRepository, RecipeRepository recipeRepository) {
        this.procedureRepository = procedureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void createProcedure(Long id, ProcedureDTO procedureDTO) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        Procedure procedure = mapToProcedureEntity(procedureDTO);
        recipe.ifPresent(procedure::setRecipe);
        procedureRepository.save(procedure);
    }
}

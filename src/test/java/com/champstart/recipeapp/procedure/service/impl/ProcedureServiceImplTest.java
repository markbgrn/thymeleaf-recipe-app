package com.champstart.recipeapp.procedure.service.impl;

import com.champstart.recipeapp.procedure.dto.ProcedureDTO;
import com.champstart.recipeapp.procedure.dto.mapper.ProcedureMapper;
import com.champstart.recipeapp.procedure.model.Procedure;
import com.champstart.recipeapp.procedure.repository.ProcedureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;

class ProcedureServiceImplTest {
    @Mock
    private ProcedureRepository procedureRepository;

    @InjectMocks
    private ProcedureServiceImpl procedureService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveProcedure() {

    }
}
package com.champstart.recipeapp.category.service.impl;

import com.champstart.recipeapp.category.dto.CategoryDto;
import com.champstart.recipeapp.exception.NotFoundException;
import com.champstart.recipeapp.category.model.Category;
import com.champstart.recipeapp.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindAllCategories() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setCategoryName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setCategoryName("Category 2");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryDto> result = categoryService.findAllCategories();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getCategoryName());
        assertEquals("Category 2", result.get(1).getCategoryName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Chicken");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto result = categoryService.findCategoryById(categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Chicken", result.getCategoryName());

        verify(categoryRepository, times(1)).findById(categoryId);

    }

    @Test
    public void testGetCategoryById_CategoryNotFound() {
        // Arrange
        long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act
        CategoryDto result = categoryService.findCategoryById(categoryId);

        // Assert
        assertNull(result);

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testSaveCategory() {
        // Arrange
        Category categoryToSave = new Category();
        categoryToSave.setCategoryName("Test Category");

        Category savedCategory = new Category();
        savedCategory.setId(2L);
        savedCategory.setCategoryName("Test Category");

        when(categoryRepository.save(categoryToSave)).thenReturn(savedCategory);

        // Act
        Category result = categoryService.saveCategory(categoryToSave);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Test Category", result.getCategoryName());

        verify(categoryRepository, times(1)).save(categoryToSave);
    }


    @Test
    void testDeleteCategoryById() {
        long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        boolean result = categoryService.deleteById(categoryId);

        assertTrue(result);

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testDeleteCategoryById_CategoryNotFound() {
        long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        boolean result = categoryService.deleteById(categoryId);

        assertFalse(result);
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Pork");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setCategoryName("Chicken");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        // Act
        CategoryDto result = categoryService.updateCategory(categoryId, categoryDto);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Pork", result.getCategoryName());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    public void testUpdateCategory_CategoryNotFound() {
        // Arrange
        long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Pork");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.updateCategory(categoryId, categoryDto));

        // Assert
        assertEquals("Category not found with ID: " + categoryId, exception.getMessage());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

}
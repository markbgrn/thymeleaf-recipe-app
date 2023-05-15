package com.champstart.recipeapp.category.service.impl;

import com.champstart.recipeapp.category.dto.CategoryDTO;
import com.champstart.recipeapp.category.dto.mapper.CategoryMapper;
import com.champstart.recipeapp.exception.NotFoundException;
import com.champstart.recipeapp.category.model.Category;
import com.champstart.recipeapp.category.repository.CategoryRepository;
import com.champstart.recipeapp.category.service.CategoryService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.champstart.recipeapp.category.dto.mapper.CategoryMapper.mapToCategoryDTO;
import static java.util.stream.Collectors.*;
@Service
@NoArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::mapToCategoryDTO).collect(toList());
    }

    @Override
    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
      Optional<Category> categoryOptional = categoryRepository.findById(id);
      return categoryOptional.map(CategoryMapper::mapToCategoryDTO).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDto) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found with ID: "+ id));

        existingCategory.setCategoryName(categoryDto.getCategoryName());

        Category updateCategory = categoryRepository.save(existingCategory);

        return mapToCategoryDTO(updateCategory);
    }
}

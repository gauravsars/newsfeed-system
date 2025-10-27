package com.example.newsfeed.service;

import com.example.newsfeed.dto.CategoryResponse;
import com.example.newsfeed.dto.CreateCategoryRequest;
import com.example.newsfeed.repository.CategoryRepository;
import com.example.newsfeed.mapper.CategoryMapper;
import com.example.newsfeed.entity.Category;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        return categoryRepository.findByCategoryName(request.getName())
                .map(categoryMapper::toResponse)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setCategoryName(request.getName());
                    return categoryMapper.toResponse(categoryRepository.save(category));
                });
    }

    public CategoryResponse getCategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return categoryMapper.toResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}

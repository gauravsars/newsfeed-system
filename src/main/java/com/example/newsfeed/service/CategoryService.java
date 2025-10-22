package com.example.newsfeed.service;

import com.example.newsfeed.dto.CreateCategoryRequest;
import com.example.newsfeed.entity.Category;
import com.example.newsfeed.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(CreateCategoryRequest request) {
        return categoryRepository.findByCategoryName(request.getName())
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setCategoryName(request.getName());
                    return categoryRepository.save(category);
                });
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

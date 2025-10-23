package com.example.newsfeed.controller;

import com.example.newsfeed.dto.CategoryResponse;
import com.example.newsfeed.dto.CreateCategoryRequest;
import com.example.newsfeed.entity.Category;
import com.example.newsfeed.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        Category category = categoryService.createCategory(request);
        CategoryResponse response = new CategoryResponse(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getAllCategories().stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getCategoryName(),
                        category.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        CategoryResponse response =  new CategoryResponse(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt()
        );
        System.out.println("Hello World");
        return response;
    }
}

package com.example.newsfeed.mapper;

import com.example.newsfeed.dto.CategoryResponse;
import com.example.newsfeed.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt()
        );
    }
}

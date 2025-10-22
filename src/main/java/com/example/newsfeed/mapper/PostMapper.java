package com.example.newsfeed.mapper;

import com.example.newsfeed.dto.PostResponse;
import com.example.newsfeed.dto.UserSummary;
import com.example.newsfeed.entity.Post;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {
        UserSummary author = new UserSummary(
                post.getUser().getId(),
                post.getUser().getName(),
                post.getUser().getEmail());

        Set<String> categories = post.getCategories().stream()
                .map(category -> category.getCategoryName())
                .collect(Collectors.toSet());

        long commentCount = post.getComments().stream()
                .filter(comment -> !comment.isDeleted())
                .count();

        long likeCount = post.getLikes().size();
        long shareCount = post.getShares().stream()
                .filter(share -> !share.isDeleted())
                .count();

        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getMediaUrl(),
                post.getCreatedAt(),
                author,
                categories,
                likeCount,
                commentCount,
                shareCount
        );
    }
}

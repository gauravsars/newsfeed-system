package com.example.newsfeed.dto;

import java.time.Instant;
import java.util.Set;

public record PostResponse(
        Long postId,
        String content,
        String mediaUrl,
        Instant createdAt,
        UserSummary author,
        Set<String> categories,
        long likeCount,
        long commentCount,
        long shareCount
) {
}

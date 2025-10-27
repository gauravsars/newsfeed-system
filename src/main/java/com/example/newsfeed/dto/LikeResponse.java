package com.example.newsfeed.dto;

import java.time.Instant;

public record LikeResponse(Long id, Long postId, Long userId, Instant createdAt) {
}

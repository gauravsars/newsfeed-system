package com.example.newsfeed.dto;

import java.time.Instant;

public record ShareResponse(Long id, Long postId, Long userId, Instant createdAt, boolean deleted) {
}

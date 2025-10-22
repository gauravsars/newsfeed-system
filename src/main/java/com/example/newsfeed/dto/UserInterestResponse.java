package com.example.newsfeed.dto;

import java.time.Instant;

public record UserInterestResponse(Long userId, Long categoryId, Instant createdAt) {
}

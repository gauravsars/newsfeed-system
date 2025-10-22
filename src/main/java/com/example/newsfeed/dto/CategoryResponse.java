package com.example.newsfeed.dto;

import java.time.Instant;

public record CategoryResponse(Long id, String name, Instant createdAt) {
}

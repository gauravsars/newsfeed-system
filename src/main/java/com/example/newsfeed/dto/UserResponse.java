package com.example.newsfeed.dto;

import java.time.Instant;

public record UserResponse(Long id, String name, String email, String status, Instant createdAt) {
}

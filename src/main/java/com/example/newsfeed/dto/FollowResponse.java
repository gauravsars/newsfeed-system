package com.example.newsfeed.dto;

import java.time.Instant;

public record FollowResponse(Long followerId, Long followeeId, Instant createdAt) {
}

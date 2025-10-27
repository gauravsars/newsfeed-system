package com.example.newsfeed.controller;

import com.example.newsfeed.dto.PostResponse;
import com.example.newsfeed.service.FeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/{userId}")
    public List<PostResponse> getFeed(@PathVariable Long userId) {
        return feedService.getFeedForUser(userId);
    }
}

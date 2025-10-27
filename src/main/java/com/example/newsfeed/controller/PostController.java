package com.example.newsfeed.controller;

import com.example.newsfeed.dto.CommentRequest;
import com.example.newsfeed.dto.CommentResponse;
import com.example.newsfeed.dto.CreatePostRequest;
import com.example.newsfeed.dto.LikeResponse;
import com.example.newsfeed.dto.PostResponse;
import com.example.newsfeed.dto.ShareResponse;
import com.example.newsfeed.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request
    ) {
        CommentResponse response = postService.addComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeResponse> likePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        LikeResponse response = postService.likePost(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{postId}/shares")
    public ResponseEntity<ShareResponse> sharePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        ShareResponse response = postService.sharePost(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

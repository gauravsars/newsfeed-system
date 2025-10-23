package com.example.newsfeed.controller;

import com.example.newsfeed.dto.CommentRequest;
import com.example.newsfeed.dto.CommentResponse;
import com.example.newsfeed.dto.CreatePostRequest;
import com.example.newsfeed.dto.LikeResponse;
import com.example.newsfeed.dto.PostResponse;
import com.example.newsfeed.dto.ShareResponse;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.PostLike;
import com.example.newsfeed.entity.Share;
import com.example.newsfeed.mapper.PostMapper;
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
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        Post post = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toResponse(post));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody CommentRequest request
    ) {
        Comment comment = postService.addComment(postId, request);
        CommentResponse response = new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getCommentedBy().getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeResponse> likePost(
            @PathVariable("postId") Long postId,
            @RequestParam("userId") Long userId
    ) {
        PostLike like = postService.likePost(postId, userId);
        LikeResponse response = new LikeResponse(
                like.getId(),
                like.getPost().getId(),
                like.getLikedBy().getId(),
                like.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{postId}/shares")
    public ResponseEntity<ShareResponse> sharePost(
            @PathVariable("postId") Long postId,
            @RequestParam("userId") Long userId
    ) {
        Share share = postService.sharePost(postId, userId);
        ShareResponse response = new ShareResponse(
                share.getId(),
                share.getPost().getId(),
                share.getSharedBy().getId(),
                share.getCreatedAt(),
                share.isDeleted()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

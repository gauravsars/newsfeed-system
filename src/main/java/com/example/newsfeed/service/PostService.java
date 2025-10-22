package com.example.newsfeed.service;

import com.example.newsfeed.dto.CommentRequest;
import com.example.newsfeed.dto.CreatePostRequest;
import com.example.newsfeed.entity.Category;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.PostLike;
import com.example.newsfeed.entity.Share;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.CategoryRepository;
import com.example.newsfeed.repository.CommentRepository;
import com.example.newsfeed.repository.PostLikeRepository;
import com.example.newsfeed.repository.PostRepository;
import com.example.newsfeed.repository.ShareRepository;
import com.example.newsfeed.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final ShareRepository shareRepository;

    @Transactional
    public Post createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
        if (categories.size() != request.getCategoryIds().size()) {
            throw new EntityNotFoundException("One or more categories not found");
        }

        Post post = new Post();
        post.setUser(user);
        post.setContent(request.getContent());
        post.setMediaUrl(request.getMediaUrl());
        post.setCategories(new HashSet<>(categories));

        return postRepository.save(post);
    }

    @Transactional
    public Comment addComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        if (post.isDeleted()) {
            throw new IllegalStateException("Cannot comment on a deleted post");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setCommentedBy(user);
        comment.setContent(request.getContent());

        return commentRepository.save(comment);
    }

    @Transactional
    public PostLike likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        if (post.isDeleted()) {
            throw new IllegalStateException("Cannot like a deleted post");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (postLikeRepository.existsByPost_IdAndLikedBy_Id(postId, userId)) {
            throw new IllegalStateException("User has already liked this post");
        }

        PostLike like = new PostLike();
        like.setPost(post);
        like.setLikedBy(user);
        return postLikeRepository.save(like);
    }

    @Transactional
    public void unlikePost(Long postId, Long userId) {
        postLikeRepository.findByPost_IdAndLikedBy_Id(postId, userId)
                .ifPresent(postLikeRepository::delete);
    }

    @Transactional
    public Share sharePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        if (post.isDeleted()) {
            throw new IllegalStateException("Cannot share a deleted post");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Share share = new Share();
        share.setPost(post);
        share.setSharedBy(user);
        return shareRepository.save(share);
    }

    public Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public Set<Post> getPostsByIds(Set<Long> postIds) {
        return new HashSet<>(postRepository.findAllById(postIds));
    }
}

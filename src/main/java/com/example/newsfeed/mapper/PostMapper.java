package com.example.newsfeed.mapper;

import com.example.newsfeed.dto.CommentResponse;
import com.example.newsfeed.dto.LikeResponse;
import com.example.newsfeed.dto.PostResponse;
import com.example.newsfeed.dto.ShareResponse;
import com.example.newsfeed.dto.UserSummary;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.PostLike;
import com.example.newsfeed.entity.Share;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {
        UserSummary author = new UserSummary(
                post.getUser().getId(),
                post.getUser().getName(),
                post.getUser().getEmail());

        Set<String> categories = post.getCategories().stream()
                .map(category -> category.getCategoryName())
                .collect(Collectors.toSet());

        long commentCount = post.getComments().stream()
                .filter(comment -> !comment.isDeleted())
                .count();

        long likeCount = post.getLikes().size();
        long shareCount = post.getShares().stream()
                .filter(share -> !share.isDeleted())
                .count();

        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getMediaUrl(),
                post.getCreatedAt(),
                author,
                categories,
                likeCount,
                commentCount,
                shareCount
        );
    }

    public CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getCommentedBy().getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

    public LikeResponse toLikeResponse(PostLike like) {
        return new LikeResponse(
                like.getId(),
                like.getPost().getId(),
                like.getLikedBy().getId(),
                like.getCreatedAt()
        );
    }

    public ShareResponse toShareResponse(Share share) {
        return new ShareResponse(
                share.getId(),
                share.getPost().getId(),
                share.getSharedBy().getId(),
                share.getCreatedAt(),
                share.isDeleted()
        );
    }
}

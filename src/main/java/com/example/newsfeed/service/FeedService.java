package com.example.newsfeed.service;

import com.example.newsfeed.dto.PostResponse;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.UserInterest;
import com.example.newsfeed.mapper.PostMapper;
import com.example.newsfeed.repository.FollowRepository;
import com.example.newsfeed.repository.PostRepository;
import com.example.newsfeed.repository.UserInterestRepository;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final UserInterestRepository userInterestRepository;
    private final PostMapper postMapper;

    public List<PostResponse> getFeedForUser(Long userId) {
        List<UserInterest> interests = userInterestRepository.findByUser_Id(userId);
        List<Long> categoryIds = interests.stream()
                .map(interest -> interest.getCategory().getId())
                .toList();

        var followees = followRepository.findByFollower_Id(userId);
        List<Long> followeeIds = followees.stream()
                .map(follow -> follow.getFollowee().getId())
                .toList();

        Collection<Post> posts = collectPosts(categoryIds, followeeIds);

        if (posts.isEmpty()) {
            posts = postRepository.findByDeletedFalseOrderByCreatedAtDesc();
        }

        return posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(postMapper::toResponse)
                .toList();
    }

    private Collection<Post> collectPosts(List<Long> categoryIds, List<Long> followeeIds) {
        Set<Post> combined = new HashSet<>();
        if (!categoryIds.isEmpty()) {
            combined.addAll(postRepository.findDistinctByDeletedFalseAndCategories_IdInOrderByCreatedAtDesc(categoryIds));
        }
        if (!followeeIds.isEmpty()) {
            combined.addAll(postRepository.findByDeletedFalseAndUser_IdInOrderByCreatedAtDesc(followeeIds));
        }
        return combined;
    }
}

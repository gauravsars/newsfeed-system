package com.example.newsfeed.service;

import com.example.newsfeed.dto.CreateUserRequest;
import com.example.newsfeed.dto.FollowResponse;
import com.example.newsfeed.dto.UserInterestResponse;
import com.example.newsfeed.dto.UserResponse;
import com.example.newsfeed.mapper.UserMapper;
import com.example.newsfeed.entity.Category;
import com.example.newsfeed.entity.Follow;
import com.example.newsfeed.entity.FollowId;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.entity.User.Status;
import com.example.newsfeed.entity.UserInterest;
import com.example.newsfeed.entity.UserInterestId;
import com.example.newsfeed.repository.CategoryRepository;
import com.example.newsfeed.repository.FollowRepository;
import com.example.newsfeed.repository.UserInterestRepository;
import com.example.newsfeed.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FollowRepository followRepository;
    private final UserInterestRepository userInterestRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            throw new IllegalStateException("User already exists with email: " + request.getEmail());
        });

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus(Status.active);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public FollowResponse followUser(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new IllegalArgumentException("Users cannot follow themselves");
        }
        if (followRepository.existsByFollower_IdAndFollowee_Id(followerId, followeeId)) {
            throw new IllegalStateException("Follow relationship already exists");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new EntityNotFoundException("Followee not found"));

        Follow follow = new Follow();
        follow.setId(new FollowId(followerId, followeeId));
        follow.setFollower(follower);
        follow.setFollowee(followee);
        return userMapper.toFollowResponse(followRepository.save(follow));
    }

    @Transactional
    public UserInterestResponse addInterest(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        UserInterestId id = new UserInterestId(userId, categoryId);
        return userInterestRepository.findById(id)
                .map(userMapper::toInterestResponse)
                .orElseGet(() -> {
                    UserInterest interest = new UserInterest();
                    interest.setId(id);
                    interest.setUser(user);
                    interest.setCategory(category);
                    return userMapper.toInterestResponse(userInterestRepository.save(interest));
                });
    }
}

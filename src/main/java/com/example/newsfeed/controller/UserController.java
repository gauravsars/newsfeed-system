package com.example.newsfeed.controller;

import com.example.newsfeed.dto.CreateUserRequest;
import com.example.newsfeed.dto.FollowResponse;
import com.example.newsfeed.dto.UserInterestResponse;
import com.example.newsfeed.dto.UserResponse;
import com.example.newsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{userId}/follow/{followeeId}")
    public ResponseEntity<FollowResponse> followUser(
            @PathVariable Long userId,
            @PathVariable Long followeeId
    ) {
        FollowResponse response = userService.followUser(userId, followeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{userId}/interests/{categoryId}")
    public ResponseEntity<UserInterestResponse> addInterest(
            @PathVariable Long userId,
            @PathVariable Long categoryId
    ) {
        UserInterestResponse response = userService.addInterest(userId, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

package com.example.newsfeed.mapper;

import com.example.newsfeed.dto.FollowResponse;
import com.example.newsfeed.dto.UserInterestResponse;
import com.example.newsfeed.dto.UserResponse;
import com.example.newsfeed.entity.Follow;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.entity.UserInterest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus().name(),
                user.getCreatedAt()
        );
    }

    public FollowResponse toFollowResponse(Follow follow) {
        return new FollowResponse(
                follow.getFollower().getId(),
                follow.getFollowee().getId(),
                follow.getCreatedAt()
        );
    }

    public UserInterestResponse toInterestResponse(UserInterest interest) {
        return new UserInterestResponse(
                interest.getUser().getId(),
                interest.getCategory().getId(),
                interest.getCreatedAt()
        );
    }
}

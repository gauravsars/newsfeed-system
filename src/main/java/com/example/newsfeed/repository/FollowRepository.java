package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Follow;
import com.example.newsfeed.entity.FollowId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    List<Follow> findByFollower_Id(Long followerId);

    boolean existsByFollower_IdAndFollowee_Id(Long followerId, Long followeeId);
}

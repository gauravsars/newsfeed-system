package com.example.newsfeed.repository;

import com.example.newsfeed.entity.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPost_IdAndLikedBy_Id(Long postId, Long userId);

    Optional<PostLike> findByPost_IdAndLikedBy_Id(Long postId, Long userId);
}

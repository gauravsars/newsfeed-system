package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Post;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"user", "categories"})
    List<Post> findDistinctByDeletedFalseAndCategories_IdInOrderByCreatedAtDesc(Collection<Long> categoryIds);

    @EntityGraph(attributePaths = {"user", "categories"})
    List<Post> findByDeletedFalseAndUser_IdInOrderByCreatedAtDesc(Collection<Long> userIds);

    @EntityGraph(attributePaths = {"user", "categories"})
    List<Post> findByDeletedFalseOrderByCreatedAtDesc();
}

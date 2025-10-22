package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share, Long> {
}

package com.example.newsfeed.repository;

import com.example.newsfeed.entity.UserInterest;
import com.example.newsfeed.entity.UserInterestId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest, UserInterestId> {

    List<UserInterest> findByUser_Id(Long userId);
}

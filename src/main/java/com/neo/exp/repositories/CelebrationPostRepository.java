package com.neo.exp.repositories;

import com.neo.exp.entities.Posts.CelebrationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CelebrationPostRepository extends JpaRepository<CelebrationPost, Long> {
    // Custom queries (if needed) can be defined here
}
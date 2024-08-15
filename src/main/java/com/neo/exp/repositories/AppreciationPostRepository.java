package com.neo.exp.repositories;

import com.neo.exp.entities.Posts.AppreciationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppreciationPostRepository extends JpaRepository<AppreciationPost, Long> {
    // Custom queries (if needed) can be defined here
}

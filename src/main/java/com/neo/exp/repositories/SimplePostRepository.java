package com.neo.exp.repositories;

import com.neo.exp.entities.Posts.SimplePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimplePostRepository extends JpaRepository<SimplePost, Long> {

}
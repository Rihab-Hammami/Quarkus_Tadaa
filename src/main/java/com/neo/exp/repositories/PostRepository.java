package com.neo.exp.repositories;

import com.neo.exp.entities.Posts.AppreciationPost;
import com.neo.exp.entities.Posts.CelebrationPost;
import com.neo.exp.entities.Posts.Post;
import com.neo.exp.entities.Posts.SimplePost;
import io.quarkus.vertx.web.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public interface PostRepository extends JpaRepository<Post, Long> {



}

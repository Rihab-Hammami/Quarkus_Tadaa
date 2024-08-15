package com.neo.exp.services;

import com.neo.exp.config.UserHolder;
import com.neo.exp.dto.*;

import com.neo.exp.entities.Comment;
import com.neo.exp.entities.Posts.AppreciationPost;
import com.neo.exp.entities.Posts.CelebrationPost;
import com.neo.exp.entities.Posts.Post;
import com.neo.exp.entities.Posts.SimplePost;
import com.neo.exp.repositories.*;
import org.hibernate.Hibernate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostService {

    @Inject
    private PostRepository postRepository;

    @Inject
    private SimplePostRepository simplePostRepository;

    @Inject
    private AppreciationPostRepository appreciationPostRepository;

    @Inject
    private CelebrationPostRepository celebrationPostRepository;

    @Inject
    private CommentRepository commentRepository;

    @Inject
    UserHolder userHolder;

//************ Simple Post ****************************
    @Transactional
    public SimplePost createSimplePost(SimplePostDTO dto) {

        SimplePost post = new SimplePost();
        post.setContent(dto.getContent());
        post.setMedia(dto.getMedia());
        post.setTaggedUsers(dto.getTaggedUsers());
        post.setCreatedAt(dto.getCreatedAt());
        post.setName(userHolder.getName());
        post.setUserId(userHolder.getUserId());
        return postRepository.save(post);
    }

    @Transactional
    public boolean updateSimplePost(Long postId, SimplePostDTO dto) {
        SimplePost post = simplePostRepository.findById(postId).orElse(null);
        if (post == null) {
            return false; // Post not found
        }

        // Check ownership
        if (!post.getName().equals(userHolder.getName())) {
            throw new RuntimeException("user not allowed ");
        }

        if (dto.getContent() != null) {
            post.setContent(dto.getContent());
        }
        if (dto.getMedia() != null) {
            post.setMedia(dto.getMedia());
        }
        if (dto.getTaggedUsers() != null) {
            post.setTaggedUsers(dto.getTaggedUsers());
        }
        if (dto.getCreatedAt() != null) {
            post.setCreatedAt(dto.getCreatedAt());
        }

        simplePostRepository.save(post);
        return true;
    }
    public List<SimplePost> getSimplePosts() {
        return simplePostRepository.findAll();
    }


    //************ Appreciation Post ****************************
    @Transactional
    public AppreciationPost createAppreciationPost(AppreciationPostDTO dto) {
        AppreciationPost post = new AppreciationPost();
        post.setPoints(dto.getPoints());
        post.setAction(dto.getAction());
        post.setAppreciationText(dto.getAppreciationText());
        post.setTaggedUsers(dto.getTaggedUsers());
        post.setCreatedAt(dto.getCreatedAt());
        post.setName(userHolder.getName());
        return postRepository.save(post);
    }
    @Transactional
    public boolean updateAppreciationPost(Long postId, AppreciationPostDTO dto) {
        AppreciationPost post = (AppreciationPost) postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false; // Post not found
        }

        if (!post.getName().equals(userHolder.getName())) {
            return false; // Current user is not the owner of the post
        }
        if (dto.getPoints() != 0) {
            post.setPoints(dto.getPoints());
        }
        if (dto.getAction() != null) {
            post.setAction(dto.getAction());
        }
        if (dto.getAppreciationText() != null) {
            post.setAppreciationText(dto.getAppreciationText());
        }
        if (dto.getTaggedUsers() != null) {
            post.setTaggedUsers(dto.getTaggedUsers());
        }
        if (dto.getCreatedAt() != null) {
            post.setCreatedAt(dto.getCreatedAt());
        }

        postRepository.save(post);
        return true;
    }
    public List<AppreciationPost> getAppreciationPosts() {
        return appreciationPostRepository.findAll();
    }

    //************ Celebration Post ****************************
    @Transactional
    public CelebrationPost createCelebrationPost(CelebrationPostDTO dto) {
        CelebrationPost post = new CelebrationPost();
        post.setEventType(dto.getEventType());
        post.setEventImageUrl(dto.getEventImageUrl());
        post.setTaggedUsers(dto.getTaggedUsers());
        post.setCreatedAt(dto.getCreatedAt());
        post.setName(userHolder.getName());
        return postRepository.save(post);
    }

    @Transactional
    public boolean updateCelebrationPost(Long postId, CelebrationPostDTO dto) {
        CelebrationPost post = (CelebrationPost) postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false;
        }
        if (!post.getName().equals(userHolder.getName())) {
            throw new RuntimeException("user not allowed ");
        }

        if (dto.getEventType() != null) {
            post.setEventType(dto.getEventType());
        }
        if (dto.getEventImageUrl() != null) {
            post.setEventImageUrl(dto.getEventImageUrl());
        }
        if (dto.getTaggedUsers() != null) {
            post.setTaggedUsers(dto.getTaggedUsers());
        }
        if (dto.getCreatedAt() != null) {
            post.setCreatedAt(dto.getCreatedAt());
        }

        postRepository.save(post);
        return true;
    }

    public List<CelebrationPost> getCelebrationPosts() {
        return celebrationPostRepository.findAll();
    }


    @Transactional
    public boolean deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false;
        }
        if (!post.getName().equals(userHolder.getName())) {
            throw new RuntimeException("user not allowed ");
        }
        postRepository.delete(post);
        return true;
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null); // Ensure the result is of the expected type
    }




//************* likes **************************

    @Transactional
    public boolean addLike(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false; // Post not found
        }
        Integer currentLikes = post.getLikes();
        post.setLikes((currentLikes != null ? currentLikes : 0) + 1); // Handle null values
        postRepository.save(post);
        return true; // Like added successfully
    }

    @Transactional
    public boolean removeLike(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false; // Post not found
        }
        Integer currentLikes = post.getLikes();
        if (currentLikes != null && currentLikes > 0) {
            post.setLikes(currentLikes - 1); // Handle null values
            postRepository.save(post);
        }
        return true; // Like removed successfully or post had no likes
    }

    @Transactional
    public Integer getLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return 0; // Return 0 if the post is not found
        }
        return post.getLikes(); // Return the count of likes
    }

    //************* Comments **************************
   @Transactional
    public void addComment(Long postId, String commentText) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment();
        comment.setCommentText(commentText);
        comment.setPost(post);
        comment.setUsername(userHolder.getUsername()); // Set username from UserHolder
        // No need to manually set createdAt as it is handled by @PrePersist
        post.getComments().add(comment);
        postRepository.save(post);
    }

    @Transactional
    public boolean deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false;
        }
        if (!post.getName().equals(userHolder.getName())) {
            throw new RuntimeException("user not allowed ");
        }
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || !comment.getPost().equals(post)) {
            return false;
        }

        post.getComments().remove(comment);
        commentRepository.delete(comment);
        return true;
    }

    @Transactional
    public boolean updateComment(Long postId, Long commentId, String newText) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false; // Post not found
        }
        if (!post.getName().equals(userHolder.getName())) {
            throw new RuntimeException("user not allowed ");
        }
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || !comment.getPost().equals(post)) {
            return false; // Comment not found or doesn't belong to the post
        }

        comment.setCommentText(newText);
        commentRepository.save(comment);
        return true;
    }
    @Transactional
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        // Find the post by ID
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }

        // Map the comments to DTOs
        return post.getComments().stream()
                .map(comment -> {
                    CommentDTO dto = new CommentDTO();
                    dto.setCommentText(comment.getCommentText());
                    dto.setUsername(comment.getUsername());
                    dto.setCreatedAt(comment.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public int getNumberOfComments(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        return post.getComments().size();
    }

}















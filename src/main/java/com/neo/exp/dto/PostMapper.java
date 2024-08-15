package com.neo.exp.dto;


import com.neo.exp.entities.Comment;
import com.neo.exp.entities.Posts.AppreciationPost;
import com.neo.exp.entities.Posts.CelebrationPost;
import com.neo.exp.entities.Posts.Post;
import com.neo.exp.entities.Posts.SimplePost;
import com.neo.exp.dto.AppreciationPostDTO;
import com.neo.exp.dto.CelebrationPostDTO;
import com.neo.exp.dto.SimplePostDTO;
import com.neo.exp.dto.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

    // Mapping method for SimplePost
    public static SimplePostDTO toSimplePostDTO(SimplePost post) {
        SimplePostDTO dto = new SimplePostDTO();
        dto.setId(post.getId());
        dto.setName(post.getName());
        dto.setUserId(post.getUserId());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setContent(post.getContent());
        dto.setMedia(post.getMedia());
        dto.setTaggedUsers(post.getTaggedUsers());
        dto.setComments(post.getComments().stream()
                .map(comment -> toCommentDTO(comment))
                .collect(Collectors.toList()));
        return dto;
    }

    // Mapping method for AppreciationPost
    public static AppreciationPostDTO toAppreciationPostDTO(AppreciationPost post) {
        AppreciationPostDTO dto = new AppreciationPostDTO();
        dto.setId(post.getId());
        dto.setName(post.getName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setPoints(post.getPoints());
        dto.setAction(post.getAction());
        dto.setAppreciationText(post.getAppreciationText());
        dto.setTaggedUsers(post.getTaggedUsers());
        dto.setComments(post.getComments().stream()
                .map(comment -> toCommentDTO(comment))
                .collect(Collectors.toList()));
        return dto;
    }

    // Mapping method for CelebrationPost
    public static CelebrationPostDTO toCelebrationPostDTO(CelebrationPost post) {
        CelebrationPostDTO dto = new CelebrationPostDTO();
        dto.setId(post.getId());
        dto.setName(post.getName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setEventType(post.getEventType());
        dto.setEventImageUrl(post.getEventImageUrl());
        dto.setTaggedUsers(post.getTaggedUsers());
        dto.setComments(post.getComments().stream()
                .map(comment -> toCommentDTO(comment))
                .collect(Collectors.toList()));
        return dto;
    }

    // General method to convert Comment entity to CommentDTO
    private static CommentDTO toCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentText(comment.getCommentText());
        dto.setUsername(comment.getUsername());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    // General method to convert Post entity to PostDTO
    public static PostDTO toPostDTO(Post post) {
        if (post instanceof SimplePost) {
            return toSimplePostDTO((SimplePost) post);
        } else if (post instanceof AppreciationPost) {
            return toAppreciationPostDTO((AppreciationPost) post);
        } else if (post instanceof CelebrationPost) {
            return toCelebrationPostDTO((CelebrationPost) post);
        } else {
            throw new IllegalArgumentException("Unknown post type: " + post.getClass().getName());
        }
    }
}


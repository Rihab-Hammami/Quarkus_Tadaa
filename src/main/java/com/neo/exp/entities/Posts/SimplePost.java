package com.neo.exp.entities.Posts;

import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.DiscriminatorValue;
import java.util.List;

@Entity
@DiscriminatorValue("simple")
public class SimplePost extends Post {
    private String content;

    @ElementCollection
    private List<String> media;

    @ElementCollection
    private List<String> taggedUsers;

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }
}

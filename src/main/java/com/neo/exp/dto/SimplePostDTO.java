package com.neo.exp.dto;

import java.util.List;

public class SimplePostDTO extends PostDTO{
    private String content;
    private List<String> media;
    private List<String> taggedUsers;

    public SimplePostDTO() {
    }

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

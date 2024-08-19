package com.neo.exp.dto;


import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public class SimplePostForm {

    @FormParam("content")
    @PartType(MediaType.TEXT_PLAIN)
    private String content;

    @FormParam("taggedUsers")
    @PartType(MediaType.TEXT_PLAIN)
    private String taggedUsers;

    @FormParam("media")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private List<InputPart> media;

    // Getters and setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(String taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public List<InputPart> getMedia() {
        return media;
    }

    public void setMedia(List<InputPart> media) {
        this.media = media;
    }
}

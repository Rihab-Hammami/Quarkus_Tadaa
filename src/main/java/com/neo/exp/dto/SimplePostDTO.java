package com.neo.exp.dto;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimplePostDTO extends PostDTO {

    @FormParam("content")
    private String content;

    @FormParam("taggedUsers")
    private List<String> taggedUsers;

    // Since media is a list of files, handle it with MultipartFormDataInput
    private MultipartFormDataInput media;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public MultipartFormDataInput getMedia() {
        return media;
    }

    public void setMedia(MultipartFormDataInput mediaInput) {
        this.media= media;
    }


}

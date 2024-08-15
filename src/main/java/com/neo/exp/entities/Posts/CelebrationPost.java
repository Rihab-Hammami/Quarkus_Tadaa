package com.neo.exp.entities.Posts;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import java.util.List;


@Entity
@DiscriminatorValue("celebration")
public class CelebrationPost extends Post {
    private String eventType;
    private String eventImageUrl;

    @ElementCollection
    private List<String> taggedUsers;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

}

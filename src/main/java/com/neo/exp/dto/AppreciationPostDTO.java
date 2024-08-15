package com.neo.exp.dto;

import com.neo.exp.entities.Posts.Post;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;


public class AppreciationPostDTO extends PostDTO {
    private int points;
    private String action;
    private String appreciationText;
    private List<String> taggedUsers;

    public AppreciationPostDTO() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public String getAppreciationText() {
        return appreciationText;
    }

    public void setAppreciationText(String appreciationText) {
        this.appreciationText = appreciationText;
    }
}

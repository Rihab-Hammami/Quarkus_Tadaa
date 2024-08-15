package com.neo.exp.entities.Posts;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("appreciation")
public class AppreciationPost extends Post {
    private int points;
    private String action;
    private String appreciationText;

    @ElementCollection
    private List<String> taggedUsers;

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

    public String getAppreciationText() {
        return appreciationText;
    }

    public void setAppreciationText(String appreciationText) {
        this.appreciationText = appreciationText;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }
}

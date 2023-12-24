package com.project.plateapi.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.plateapi.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;

    private String content;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", description=" + content + "]";
    }
}

package com.project.plateapi.user;

import com.project.plateapi.comment.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "user")
@NoArgsConstructor
public class User {
    public User(String userId, String userPassword, String name, String nickname) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
        this.nickname = nickname;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    @Size(min=2, message = "Name should have at least 2 characters")
    @Column(name = "user_name")
    private String name;

    @Column(name = "user_nickname")
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

}
package com.project.plateapi.user;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.role.Role;
import com.project.plateapi.user_role.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@Getter
@Setter
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Size(min = 2, message = "Name should have at least 2 characters")
    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_NICKNAME")
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

    @Column(name = "CREATED_DATE")
    @CreatedDate
    private String createdDate;

    @Column(name = "DELETED_DATE")
    @LastModifiedDate
    private String deletedDate;

    private boolean enabled = Boolean.FALSE;

    private boolean deleted = Boolean.FALSE;


    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public void update(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }


}
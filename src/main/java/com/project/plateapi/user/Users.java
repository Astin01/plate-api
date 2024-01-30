package com.project.plateapi.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.plateapi.comment.Comment;
import com.project.plateapi.role.Role;
import com.project.plateapi.user_role.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name = "USER_PASSWORD_CHECK")
    private String userPasswordCheck;

    @Size(min = 2, message = "Name should have at least 2 characters")
    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_NICKNAME")
    private String nickname;

    @Column(name = "USER_EMAIL")
    private String email;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<UserRole> userRoles = new ArrayList<>();

    @Column(name = "CREATED_DATE")
    @CreatedDate
    private String createdDate;

    @Column(name = "DELETED_DATE")
    @LastModifiedDate
    private String deletedDate;

    @Column(name = "ENABLED")
    private boolean enabled = Boolean.TRUE;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public void update(Users user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userPassword = user.getUserPassword();
    }


}
package com.project.plateapi.user.domain;

import com.project.plateapi.user_role.domain.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Size(min = 4 ,max=10, message = "Id should have at least 4 characters")
    @Column(name = "USER_ID")
    private String userId;

    @NotNull
    @Size(min = 2, message = "Password should have at least 2 characters")
    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @NotNull
    @Size(min = 2, message = "Name should have at least 2 characters")
    @Column(name = "USER_NAME")
    private String name;

    @NotNull
    @Size(min = 2, message = "NickName should have at least 2 characters")
    @Column(name = "USER_NICKNAME")
    private String nickname;

    @NotNull
    @Email
    @Size(min = 2, message = "Email should have at least 2 characters")
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

    public void update(Users user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userPassword = user.getUserPassword();
    }


}
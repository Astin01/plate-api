package com.project.plateapi.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.plateapi.discussion.Discussion;
import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.user.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="COMMENT", columnDefinition = "TEXT")
    @NotBlank
    private String comment;

    @Column(name = "CREATED_DATE")
    @CreatedDate
    private String createdDate;

    @Column(name = "MODIFIED_DATE")
    @LastModifiedDate
    private String modifiedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISCUSSION_ID")
    private Discussion discussion;

    public void update(String comment, String modifiedDate) {
        this.comment = comment;
        this.modifiedDate = modifiedDate;
    }
}

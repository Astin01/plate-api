package com.project.plateapi.discussion;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.user.Users;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
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
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", columnDefinition = "VARCHAR(20)", nullable = false)
    private String title;

    @Column(name="CONTENT",columnDefinition = "TEXT")
    private  String content;

    @Column(name = "CLOSED")
    private boolean closed;

    @Column(name = "CREATED_DATE", nullable = false)
    @CreatedDate
    private String createdDate;

    @Column(name = "CLOSED_DATE")
    @LastModifiedDate
    private String closedDate;

    @OneToMany(mappedBy = "discussion")
    private List<Comment> comments = new ArrayList<>();

    public void edit(Discussion discussion) {
        this.title = discussion.getTitle();
        this.content = discussion.getContent();
    }
}

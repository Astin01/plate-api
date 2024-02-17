package com.project.plateapi.notice;

import com.project.plateapi.restaurant.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Size(min = 1, message = "Name should have at least 1 characters")
    @Column(name = "TITLE",columnDefinition = "VARCHAR(20)", nullable = false)
    private String title;

    @Column(name="CONTENT",columnDefinition = "TEXT")
    private  String content;

    @Column(name="IMAGE")
    private String imageUrl;

    @Column(name="CLOSED")
    private boolean closed;

    @Column(name = "CREATED_DATE", nullable = false)
    @CreatedDate
    private String createdDate;

    @Column(name = "CLOSED_DATE")
    @LastModifiedDate
    private String closedDate;

    public void update(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.imageUrl = notice.getImageUrl();
    }
}

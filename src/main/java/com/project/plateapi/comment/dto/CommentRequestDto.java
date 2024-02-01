package com.project.plateapi.comment.dto;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.discussion.Discussion;
import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.user.Users;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private String comment;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private Users user;
    private Discussion discussion;

    public Comment toEntity() {
        Comment comments = Comment.builder()
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .user(user)
                .discussion(discussion)
                .build();
        return comments;
    }


}

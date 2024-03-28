package com.project.plateapi.comment.controller.dto.request;

import com.project.plateapi.comment.domain.Comment;
import com.project.plateapi.discussion.domain.Discussion;
import com.project.plateapi.user.domain.Users;
import jakarta.validation.constraints.NotBlank;
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
public class CommentCreateDto {

    @NotBlank
    private String comment;
    private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private Users user;
    private Discussion discussion;

    public Comment toEntity() {
        return Comment.builder()
                .comment(comment)
                .createdDate(createdDate)
                .user(user)
                .discussion(discussion)
                .build();
    }


}

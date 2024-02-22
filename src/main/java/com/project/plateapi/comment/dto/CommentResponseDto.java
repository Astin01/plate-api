package com.project.plateapi.comment.dto;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.role.Role;
import com.project.plateapi.user.Users;
import com.project.plateapi.user_role.UserRole;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comments;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private UserCommentDto commentContent;

    public CommentResponseDto(List<Comment> commentList) {
        this.commentContent = new UserCommentDto(commentList);
    }
    @Getter
    public static class UserCommentDto {
        private final List<ModifyCommentDto> comments;

        public UserCommentDto(List<Comment> commentList) {
            this.comments = commentList.stream().map(comment1->{
                ModifyCommentDto dto = new ModifyCommentDto();
                dto.setId(comment1.getId());
                dto.setComment(comment1.getComment());
                dto.setCreatedDate(comment1.getCreatedDate());
                dto.setUserNickname(comment1.getUser().getNickname());
                return dto;
            }).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class ModifyCommentDto{
            private Long id;
            private String comment;
            private String createdDate;
            private String userNickname;
        }
    }


}

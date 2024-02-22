package com.project.plateapi.discussion.dto;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.discussion.Discussion;
import com.project.plateapi.role.Role;
import com.project.plateapi.user.Users;
import com.project.plateapi.user.dto.UserInfoResponseDto.UserRoleDto;
import com.project.plateapi.user_role.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionResponseDto {
    private Long id;
    private String title;
    private String content;
    private boolean closed;
    private CommentDto comments;

    public DiscussionResponseDto(Discussion discussion){
        this.id = discussion.getId();
        this.title = discussion.getTitle();
        this.content = discussion.getContent();
        this.closed = discussion.isClosed();
        this.comments = new CommentDto(discussion.getComments());
    }
    @Getter
    public static class CommentDto {
        private final List<DiscussionCommentDto> comment;
        public CommentDto(List<Comment> commentLists) {
            this.comment = commentLists.stream().map(
                    comment1 -> {
                        DiscussionCommentDto comments = new DiscussionCommentDto();
                        comments.setId(comment1.getId());
                        comments.setComment(comment1.getComment());
                        comments.setUserNickname(comment1.getUser().getNickname());
                        return comments;
                    }
            ).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class DiscussionCommentDto{
            private long id;
            private String comment;
            private String userNickname;
        }

    }

}

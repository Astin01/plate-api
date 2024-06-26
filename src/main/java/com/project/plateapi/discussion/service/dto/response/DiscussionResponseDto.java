package com.project.plateapi.discussion.service.dto.response;

import com.project.plateapi.comment.domain.Comment;
import com.project.plateapi.discussion.domain.Discussion;
import com.project.plateapi.user.domain.Users;
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
    private UserIdDto userId;
    private CommentDto comments;

    public DiscussionResponseDto(Discussion discussion) {
        this.id = discussion.getId();
        this.title = discussion.getTitle();
        this.content = discussion.getContent();
        this.closed = discussion.isClosed();
        this.userId = new UserIdDto(discussion.getUser());
        this.comments = new CommentDto(discussion.getComments());
    }

    @Getter
    public static class UserIdDto {
        private final String userId;

        public UserIdDto(Users user) {
            this.userId = user.getUserId();
        }
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
        public class DiscussionCommentDto {
            private long id;
            private String comment;
            private String userNickname;
        }

    }

}

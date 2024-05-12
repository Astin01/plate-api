package com.project.plateapi.comment.service;

import com.project.plateapi.comment.controller.dto.request.CommentDto;
import com.project.plateapi.comment.domain.Comment;
import com.project.plateapi.comment.domain.CommentRepository;
import com.project.plateapi.comment.service.dto.response.CommentResponseDto;
import com.project.plateapi.discussion.domain.Discussion;
import com.project.plateapi.discussion.domain.DiscussionRepository;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.domain.Users;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiscussionRepository discussionRepository;

    public CommentResponseDto getComment(long discussion_id) {
        Discussion discussion = discussionRepository.findById(discussion_id).orElseThrow(IllegalArgumentException::new);

        List<Comment> commentList = commentRepository.findAllByDiscussion(discussion);

        return new CommentResponseDto(commentList);
    }

    @Transactional
    public void createComment(CustomUser customUser, Long id, CommentDto commentdto) {
        final Users user = customUser.getUser();
        final Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패."));
        final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        final Comment comment = Comment.builder()
                .comment(commentdto.comment())
                .user(user)
                .discussion(discussion)
                .createdDate(createdDate)
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentDto commentdto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id= " + id));
        String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        comment.update(commentdto.comment(), modifiedDate);
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

}

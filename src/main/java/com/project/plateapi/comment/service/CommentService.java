package com.project.plateapi.comment.service;

import com.project.plateapi.comment.controller.dto.request.CommentCreateDto;
import com.project.plateapi.comment.controller.dto.request.CommentUpdateDto;
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

    public ResponseEntity<CommentResponseDto> getComment(long discussion_id) {
        Discussion discussion = discussionRepository.findById(discussion_id).orElseThrow(IllegalArgumentException::new);

        List<Comment> commentList = commentRepository.findAllByDiscussion(discussion);

        CommentResponseDto responseDto = new CommentResponseDto(commentList);

        return ResponseEntity.ok(responseDto);
    }

    @Transactional
    public ResponseEntity<Void> createComment(CustomUser customUser, Long id, CommentCreateDto createDto) {
        Users user = customUser.getUser();
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패."));

        createDto.setComment(user,discussion);

        Comment comment = createDto.toEntity();
        commentRepository.save(comment);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Void> updateComment(Long id, CommentUpdateDto updateDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id= " + id));
        String modifiedDate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        comment.update(updateDto.comment(), modifiedDate);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Void> deleteComment(long id) {
        commentRepository.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

}

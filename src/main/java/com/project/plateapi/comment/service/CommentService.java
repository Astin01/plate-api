package com.project.plateapi.comment.service;

import com.project.plateapi.comment.domain.Comment;
import com.project.plateapi.comment.domain.CommentRepository;
import com.project.plateapi.comment.controller.dto.request.CommentCreateDto;
import com.project.plateapi.comment.service.dto.response.CommentResponseDto;
import com.project.plateapi.comment.controller.dto.request.CommentUpdateDto;
import com.project.plateapi.discussion.domain.Discussion;
import com.project.plateapi.discussion.domain.DiscussionRepository;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.domain.Users;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiscussionRepository discussionRepository;


    @Transactional
    public ResponseEntity<?> createComment(CustomUser customUser, Long id, CommentCreateDto createDto) {
        Users user = customUser.getUser();
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패."));

        createDto.setUser(user);
        createDto.setDiscussion(discussion);

        Comment comment = createDto.toEntity();
        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long id, CommentUpdateDto updateDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id= " + id));
        comment.update(updateDto.getComment(), updateDto.getModifiedDate());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteComment(long id) {
        commentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getComment(long discussion_id) {
        Discussion discussion = discussionRepository.findById(discussion_id).orElseThrow(IllegalArgumentException::new);

        List<Comment> commentList = commentRepository.findAllByDiscussion(discussion);

        CommentResponseDto responseDto = new CommentResponseDto(commentList);

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}
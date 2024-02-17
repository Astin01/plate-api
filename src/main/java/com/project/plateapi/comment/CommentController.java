package com.project.plateapi.comment;

import com.project.plateapi.comment.dto.CommentCreateDto;
import com.project.plateapi.comment.dto.CommentUpdateDto;
import com.project.plateapi.security.custom.dto.CustomUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/api/comment/{discussion_id}")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal CustomUser customUser, @PathVariable long discussion_id, @Valid @RequestBody CommentCreateDto createDto) {
        return service.createComment(customUser, discussion_id, createDto);
    }

    @PutMapping("/api/comment/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable long comment_id, @RequestBody CommentUpdateDto updateDto) {
        return service.updateComment(comment_id, updateDto);
    }

    @DeleteMapping("/api/comment/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable long comment_id) {
        return service.deleteComment(comment_id);
    }

}
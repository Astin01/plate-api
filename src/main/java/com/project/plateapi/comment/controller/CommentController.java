package com.project.plateapi.comment.controller;

import com.project.plateapi.comment.service.CommentService;
import com.project.plateapi.comment.controller.dto.request.CommentRequestDto;
import com.project.plateapi.comment.service.dto.response.CommentResponseDto;
import com.project.plateapi.security.custom.dto.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService service;

    @GetMapping("/{discussion_id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable long discussion_id) {
        CommentResponseDto response = service.getComment(discussion_id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{discussion_id}")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal CustomUser customUser,
                                              @PathVariable long discussion_id,
                                              @Valid @RequestBody CommentRequestDto request) {
        service.createComment(customUser, discussion_id, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<Void> updateComment(@PathVariable long comment_id,
                                              @RequestBody CommentRequestDto commentdto) {
        service.updateComment(comment_id, commentdto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long comment_id) {
        service.deleteComment(comment_id);

        return ResponseEntity.noContent().build();
    }

}
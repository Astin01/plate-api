package com.project.plateapi.comment;

import com.project.plateapi.comment.dto.CommentRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/api/comment/{id}")
    public void createComment(String nickname, @PathVariable long id, @Valid @RequestBody CommentRequestDto dto) {
        nickname = "ace";
        service.createComment(nickname, id, dto);
    }

    @PutMapping("/api/comment/{id}")
    public void updateComment(@PathVariable long id, @RequestBody CommentRequestDto dto) {
        service.updateComment(id, dto);
    }

    @DeleteMapping("/api/comment/{id}")
    public void deleteComment(@PathVariable long id) {
        service.deleteComment(id);
    }

}
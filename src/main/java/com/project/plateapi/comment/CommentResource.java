package com.project.plateapi.comment;

import com.project.plateapi.comment.dto.CommentRequestDto;
import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.restaurant.RestaurantService;
import com.project.plateapi.restaurant.dto.RestaurantUpdateRequestDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentResource {
    private final CommentService service;

    public CommentResource(CommentService service) {
        this.service = service;
    }

    @PostMapping("/api/{id}/comment")
    public void createComment(String nickname, @PathVariable long id, @Valid @RequestBody CommentRequestDto dto) {
        nickname = "ace";
        service.createComment(nickname, id, dto);
    }

    @PutMapping("/api/{id}/comment")
    public void updateComment(@PathVariable long id, @RequestBody CommentRequestDto dto) {
        service.updateComment(id, dto);
    }

    @DeleteMapping("/api/{id}/comment")
    public void deleteComment(@PathVariable long id) {
        service.deleteComment(id);
    }

}
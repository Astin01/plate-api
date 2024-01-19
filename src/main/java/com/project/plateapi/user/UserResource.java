package com.project.plateapi.user;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.user.dto.UserRequestDto;
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
public class UserResource {
    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/users")
    public void createUser(@Valid @RequestBody User user) {
        service.createUser(user);
    }

    @GetMapping("/api/users/{id}")
    public User retrieveUser(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PutMapping("/api/users/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        service.updateUser(id, dto);
    }

    @GetMapping("/api/users/{id}/comments")
    public List<Comment> retrieveCommentsForUser(@PathVariable Long id) {
        User user = service.findById(id);

        return user.getComments();

    }

}

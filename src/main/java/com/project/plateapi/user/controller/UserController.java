package com.project.plateapi.user.controller;

import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.service.UserService;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user.dto.UserRequestDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto dto) {
        Users user = dto.toEntity();
        return service.createUser(user);
    }

    @Secured({"USER", "ADMIN"})
    @GetMapping("/api/users/info")
    public ResponseEntity<?> retrieveUserInfo(@AuthenticationPrincipal CustomUser customUser) {
        return service.retrieveUserInfo(customUser);
    }

    @Secured({"USER", "ADMIN"})
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal CustomUser customUser, @PathVariable String id) {
        return service.deleteUser(customUser, id);
    }

    @Secured({"USER", "ADMIN"})
    @PutMapping("/api/users")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto dto) {
        return service.updateUser(dto);
    }

}

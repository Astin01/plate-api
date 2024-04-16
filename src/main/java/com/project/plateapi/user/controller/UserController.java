package com.project.plateapi.user.controller;

import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.controller.dto.request.UserInfoRequest;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user.service.UserService;
import com.project.plateapi.user.service.dto.response.UserInfoResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @PostMapping()
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserInfoRequest dto) {
        Users user = Users.builder()
                .userId(dto.userId())
                .userPassword(dto.userPassword())
                .name(dto.name())
                .nickname(dto.nickname())
                .email(dto.email())
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
        return service.createUser(user);
    }

    @Secured({"USER", "ADMIN"})
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> retrieveUserInfo(@AuthenticationPrincipal CustomUser customUser) {
        return service.retrieveUserInfo(customUser);
    }

    @Secured({"USER", "ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal CustomUser customUser, @PathVariable String id) {
        return service.deleteUser(customUser, id);
    }

    @Secured({"USER", "ADMIN"})
    @PutMapping()
    public ResponseEntity<Void> updateUser(@RequestBody UserInfoRequest dto) {
        return service.updateUser(dto);
    }

}

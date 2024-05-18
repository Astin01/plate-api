package com.project.plateapi.discussion.controller;

import com.project.plateapi.discussion.controller.dto.request.DiscussionRequestDto;
import com.project.plateapi.discussion.service.DiscussionService;
import com.project.plateapi.discussion.service.dto.response.DiscussionListResponseDto;
import com.project.plateapi.discussion.service.dto.response.DiscussionResponseDto;
import com.project.plateapi.security.custom.dto.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

@RequestMapping("/api/discussion")
@RequiredArgsConstructor
@RestController
public class DiscussionController {
    private final DiscussionService service;

    @GetMapping()
    public ResponseEntity<DiscussionListResponseDto> getAllDiscussion() {
        boolean isClosed = false;
        DiscussionListResponseDto response = service.getAllDiscussion(isClosed);

        return ResponseEntity.ok(response);
    }

    @Secured("USER")
    @PostMapping()
    public ResponseEntity<Void> createDiscussion(@AuthenticationPrincipal CustomUser customUser,
                                                 @Valid @RequestBody DiscussionRequestDto request) {
        service.createDiscussion(customUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionResponseDto> getDiscussion(@PathVariable Long id) {
        DiscussionResponseDto response = service.getDiscussion(id);

        return ResponseEntity.ok(response);
    }

    @Secured("USER")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDiscussionTitle(@AuthenticationPrincipal CustomUser customUser,
                                                      @PathVariable long id,
                                                      @RequestBody DiscussionRequestDto request) {
        service.editDiscussion(customUser, id, request);

        return ResponseEntity.ok().build();
    }

    @Secured("USER")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscussion(@AuthenticationPrincipal CustomUser customUser,
                                                 @PathVariable long id) {
        service.deleteDiscussion(customUser, id);

        return ResponseEntity.noContent().build();
    }
}
package com.project.plateapi.discussion.controller;

import com.project.plateapi.discussion.domain.Discussion;
import com.project.plateapi.discussion.service.DiscussionService;
import com.project.plateapi.discussion.controller.dto.request.DiscussionEditDto;
import com.project.plateapi.discussion.controller.dto.request.DiscussionCreateDto;
import com.project.plateapi.security.custom.dto.CustomUser;
import jakarta.validation.Valid;
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

@RestController
public class DiscussionController {
    private final DiscussionService service;

    public DiscussionController(DiscussionService service) {
        this.service = service;
    }

    @GetMapping("/api/discussion")
    public ResponseEntity<?> getAllDiscussion(){
        boolean isClosed = false;
        return service.getAllDiscussion(isClosed);
    }

    @Secured("USER")
    @PostMapping("/api/discussion")
    public ResponseEntity<?> createDiscussion(@AuthenticationPrincipal CustomUser customUser, @Valid @RequestBody DiscussionCreateDto createDto) {
        Discussion discussion = createDto.toEntity();
        return service.createDiscussion(customUser, discussion);
    }

    @GetMapping("/api/discussion/{id}")
    public ResponseEntity<?> getDiscussion(@PathVariable long id){
        return service.getDiscussion(id);
    }

    @Secured("USER")
    @PutMapping("/api/discussion/{id}")
    public ResponseEntity<?> updateDiscussionTitle(@AuthenticationPrincipal CustomUser customUser,@PathVariable long id, @RequestBody DiscussionEditDto editDto) {
        return service.editDiscussion(customUser,id, editDto);
    }

    @Secured("USER")
    @DeleteMapping("/api/discussion/{id}")
    public ResponseEntity<?> closeDiscussion(@AuthenticationPrincipal CustomUser customUser,@PathVariable long id) {
        return service.deleteDiscussion(customUser,id);
    }
}
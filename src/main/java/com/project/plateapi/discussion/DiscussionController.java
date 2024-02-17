package com.project.plateapi.discussion;

import com.project.plateapi.discussion.dto.DiscussionEditRequestDto;
import com.project.plateapi.discussion.dto.DiscussionRequestDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/discussion")
    public ResponseEntity<?> createDiscussion(@Valid @RequestBody DiscussionRequestDto dto) {
        Discussion discussion = dto.toEntity();
        return service.createDiscussion(discussion);
    }

    @GetMapping("/api/discussion/{id}")
    public ResponseEntity<?> getDiscussion(@PathVariable long id){
        return service.getDiscussion(id);
    }

    @PutMapping("/api/discussion/{id}")
    public ResponseEntity<?> updateDiscussionTitle(@PathVariable long id, @RequestBody DiscussionEditRequestDto dto) {
        return service.editDiscussion(id, dto);
    }

    @DeleteMapping("/api/discussion/{id}")
    public ResponseEntity<?> closeDiscussion(@PathVariable long id) {
        return service.deleteDiscussion(id);
    }
}
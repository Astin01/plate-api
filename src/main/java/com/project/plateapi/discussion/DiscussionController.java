package com.project.plateapi.discussion;

import com.project.plateapi.comment.CommentService;
import com.project.plateapi.comment.dto.CommentRequestDto;
import com.project.plateapi.discussion.dto.DiscussionEditRequestDto;
import com.project.plateapi.discussion.dto.DiscussionRequestDto;
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
public class DiscussionController {
    private final DiscussionService service;

    public DiscussionController(DiscussionService service) {
        this.service = service;
    }

    @GetMapping("/api/discussion")
    public List<Discussion> getAllDiscussion(){
        boolean isClosed = false;
        return service.getAllDiscussion(isClosed);
    }

    @PostMapping("/api/discussion")
    public void createDiscussion(@Valid @RequestBody DiscussionRequestDto dto) {
        Discussion discussion = dto.toEntity();
        service.createDiscussion(discussion);
    }

    @GetMapping("/api/discussion/{id}")
    public Discussion getDiscussion(@PathVariable long id){
        return service.getDiscussion(id);
    }

    @PutMapping("/api/discussion/{id}")
    public void updateDiscussionTitle(@PathVariable long id, @RequestBody DiscussionEditRequestDto dto) {
        service.changeDiscussionTitle(id, dto);
    }

    @DeleteMapping("/api/discussion/{id}")
    public void closeDiscussion(@PathVariable long id) {
        service.deleteDiscussion(id);
    }
}
package com.project.plateapi.discussion.service;

import com.project.plateapi.discussion.domain.Discussion;
import com.project.plateapi.discussion.domain.DiscussionRepository;
import com.project.plateapi.discussion.controller.dto.request.DiscussionEditDto;
import com.project.plateapi.discussion.service.dto.response.DiscussionListResponseDto;
import com.project.plateapi.discussion.service.dto.response.DiscussionResponseDto;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.domain.Users;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

    @Transactional
    public ResponseEntity<Void> createDiscussion(CustomUser customUser, Discussion discussion) {
        Users user = customUser.getUser();

        discussion.setUser(user);
        discussionRepository.save(discussion);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> editDiscussion(CustomUser customUser, Long id, DiscussionEditDto dto) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if(notUser(customUser, discussion)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        discussion.setTitle(dto.getTitle());
        discussion.setContent(dto.getContent());

        discussion.edit(discussion);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> deleteDiscussion(CustomUser customUser,long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if(notUser(customUser, discussion)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        discussionRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<DiscussionListResponseDto> getAllDiscussion(boolean closed) {
        List<Discussion> discussionList = discussionRepository.findAllByClosed(closed);

        DiscussionListResponseDto responseDto = new DiscussionListResponseDto(discussionList);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    public ResponseEntity<DiscussionResponseDto> getDiscussion(long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(""));

        DiscussionResponseDto responseDto = new DiscussionResponseDto(discussion);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private boolean notUser(CustomUser customUser, Discussion discussion){
        String userId = customUser.getUsername();
        String discussionUserId = discussion.getUser().getUserId();

        return !userId.equals(discussionUserId);
    }
}

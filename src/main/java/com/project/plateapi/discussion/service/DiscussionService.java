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
    public void createDiscussion(CustomUser customUser, Discussion discussion) {
        Users user = customUser.getUser();

        discussion.setUser(user);
        discussionRepository.save(discussion);
    }

    @Transactional
    public void editDiscussion(CustomUser customUser, Long id, DiscussionEditDto dto) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (notUser(customUser, discussion)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        discussion.setTitle(dto.getTitle());
        discussion.setContent(dto.getContent());

        discussion.edit(discussion);
    }

    @Transactional
    public void deleteDiscussion(CustomUser customUser, long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (notUser(customUser, discussion)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        discussionRepository.deleteById(id);
    }

    public DiscussionListResponseDto getAllDiscussion(boolean closed) {
        List<Discussion> discussionList = discussionRepository.findAllByClosed(closed);

        return new DiscussionListResponseDto(discussionList);
    }

    public DiscussionResponseDto getDiscussion(Long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(""));

        return new DiscussionResponseDto(discussion);
    }

    private boolean notUser(CustomUser customUser, Discussion discussion) {
        String userId = customUser.getUsername();
        String discussionUserId = discussion.getUser().getUserId();

        return !userId.equals(discussionUserId);
    }
}

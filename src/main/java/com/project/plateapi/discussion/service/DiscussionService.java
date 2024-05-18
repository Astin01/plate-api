package com.project.plateapi.discussion.service;

import com.project.plateapi.discussion.controller.dto.request.DiscussionRequestDto;
import com.project.plateapi.discussion.domain.Discussion;
import com.project.plateapi.discussion.domain.DiscussionRepository;
import com.project.plateapi.discussion.service.dto.response.DiscussionListResponseDto;
import com.project.plateapi.discussion.service.dto.response.DiscussionResponseDto;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.domain.Users;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

    @Transactional
    public Long createDiscussion(CustomUser customUser, DiscussionRequestDto request) {
        Users user = customUser.getUser();
        Discussion discussion = Discussion.builder()
                .title(request.title())
                .content(request.content())
                .closed(false)
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .user(user)
                .build();

        Discussion created = discussionRepository.save(discussion);

        return created.getId();
    }

    @Transactional
    public void editDiscussion(CustomUser customUser, Long id, DiscussionRequestDto request) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (notUser(customUser, discussion)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        discussion.edit(request.title(), request.content());
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
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 토론이 없습니다"));

        return new DiscussionResponseDto(discussion);
    }

    private boolean notUser(CustomUser customUser, Discussion discussion) {
        String userId = customUser.getUsername();
        String discussionUserId = discussion.getUser().getUserId();

        return !userId.equals(discussionUserId);
    }
}

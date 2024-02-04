package com.project.plateapi.discussion;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.comment.CommentRepository;
import com.project.plateapi.comment.dto.CommentRequestDto;
import com.project.plateapi.discussion.dto.DiscussionEditRequestDto;
import com.project.plateapi.discussion.dto.DiscussionRequestDto;
import com.project.plateapi.user.UserRepository;
import com.project.plateapi.user.Users;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

    @Transactional
    public void createDiscussion(Discussion discussion) {
          discussionRepository.save(discussion);
    }

    @Transactional
    public void changeDiscussionTitle(Long id, DiscussionEditRequestDto dto) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        discussion.setTitle(dto.getTitle());

        discussion.changeTitle(discussion);
    }

    public void deleteDiscussion(long id) {
        discussionRepository.deleteById(id);
    }

    public List<Discussion> getAllDiscussion(boolean closed) {
        return discussionRepository.findAllByClosed(closed);
    }

    public Discussion getDiscussion(long id) {
        return discussionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(""));
    }
}

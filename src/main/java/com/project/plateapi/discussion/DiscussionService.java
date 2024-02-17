package com.project.plateapi.discussion;

import com.project.plateapi.discussion.dto.DiscussionEditRequestDto;
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
    public ResponseEntity<?> createDiscussion(Discussion discussion) {
          discussionRepository.save(discussion);

          return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> editDiscussion(Long id, DiscussionEditRequestDto dto) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        discussion.setTitle(dto.getTitle());
        discussion.setContent(dto.getContent());

        discussion.edit(discussion);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteDiscussion(long id) {
        discussionRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getAllDiscussion(boolean closed) {
        List<Discussion> discussionList = discussionRepository.findAllByClosed(closed);

        return new ResponseEntity<>(discussionList, HttpStatus.OK);

    }

    public ResponseEntity<?> getDiscussion(long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(""));

        return new ResponseEntity<>(discussion,HttpStatus.OK);
    }
}

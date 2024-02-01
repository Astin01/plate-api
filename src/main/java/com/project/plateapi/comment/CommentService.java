package com.project.plateapi.comment;

import com.project.plateapi.comment.dto.CommentRequestDto;
import com.project.plateapi.discussion.Discussion;
import com.project.plateapi.discussion.DiscussionRepository;
import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.restaurant.RestaurantRepository;
import com.project.plateapi.user.Users;
import com.project.plateapi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;


    @Transactional
    public void createComment(String nickname, Long id, CommentRequestDto dto) {
        Users user = userRepository.findByNickname(nickname);
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패."));

        dto.setUser(user);
        dto.setDiscussion(discussion);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id= " + id));
        comment.update(dto.getComment(), dto.getModifiedDate());
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

}

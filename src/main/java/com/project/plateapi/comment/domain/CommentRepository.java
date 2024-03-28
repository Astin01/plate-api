package com.project.plateapi.comment.domain;

import com.project.plateapi.discussion.domain.Discussion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiscussion(Discussion discussion);
}

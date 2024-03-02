package com.project.plateapi.comment;

import com.project.plateapi.discussion.Discussion;
import com.project.plateapi.restaurant.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiscussion(Discussion discussion);
}

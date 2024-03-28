package com.project.plateapi.discussion.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findAllByClosed(boolean closed);

    @Query("select distinct d from Discussion d join fetch d.comments")
    Optional<Discussion> findById(Long id);

}

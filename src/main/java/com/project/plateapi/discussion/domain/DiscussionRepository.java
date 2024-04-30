package com.project.plateapi.discussion.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findAllByClosed(boolean closed);

    Optional<Discussion> findById(Long id);

}

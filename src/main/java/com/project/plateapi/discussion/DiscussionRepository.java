package com.project.plateapi.discussion;

import com.project.plateapi.restaurant.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findAllByClosed(boolean closed);

    Optional<Discussion> findById(Long id);

}

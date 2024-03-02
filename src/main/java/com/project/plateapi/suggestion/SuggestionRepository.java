package com.project.plateapi.suggestion;

import com.project.plateapi.restaurant.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    Optional<Suggestion> findById(Long id);

    @EntityGraph(attributePaths = {"user","restaurant"})
    List<Suggestion> findAll();
}

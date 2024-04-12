package com.project.plateapi.suggestion.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    Optional<Suggestion> findById(Long id);

//    @EntityGraph(attributePaths = {"user","restaurant"})
    @Query("select m from Suggestion m left join fetch m.restaurant left join fetch m.user ")
    List<Suggestion> findAll();
}

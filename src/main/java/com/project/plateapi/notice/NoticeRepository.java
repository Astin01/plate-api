package com.project.plateapi.notice;

import com.project.plateapi.suggestion.Suggestion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findById(Long id);
}

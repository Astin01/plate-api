package com.project.plateapi.icon.domain;

import com.project.plateapi.notice.domain.Notice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconRepository extends JpaRepository<Icon, Long> {
    Optional<Icon> findById(Long id);
}

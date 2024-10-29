package com.project.plateapi.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

    @Query("SELECT up FROM UserPreference up WHERE up.user.id = :userId")
    Optional<UserPreference> findByUserId(Long userId);
}

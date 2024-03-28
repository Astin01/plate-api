package com.project.plateapi.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByNickname(String nickname);

    Users findByUserId(String userID);

    Optional<Users> findById(Long id);

    void deleteByUserId(String userId);

    Users findByEmail(String email);
}
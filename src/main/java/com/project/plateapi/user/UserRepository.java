package com.project.plateapi.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);

    Optional<User> findById(Long id);
}
package com.project.plateapi.user;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByNickname(String nickname);

    Users findByUserId(String userID);

    Optional<Users> findById(Long id);

    void deleteByUserId(String userId);

    Users findByEmail(String email);
}
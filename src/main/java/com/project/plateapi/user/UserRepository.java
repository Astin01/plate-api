package com.project.plateapi.user;

import java.util.Optional;
import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByNickname(String nickname);

    Users findByUserId(String userID);

    Optional<Users> findById(Long id);
}
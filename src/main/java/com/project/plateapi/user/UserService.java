package com.project.plateapi.user;

import com.project.plateapi.role.Role;
import com.project.plateapi.user.dto.UserRequestDto;
import com.project.plateapi.user_role.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id= " + id));
        user.update(dto.getName(), dto.getNickname());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no such id exist"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}

package com.project.plateapi.user.service;

import com.project.plateapi.role.domain.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.controller.dto.request.UserInfoRequest;
import com.project.plateapi.user.domain.UserRepository;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user.exception.UserNotFoundException;
import com.project.plateapi.user.service.dto.response.UserInfoResponse;
import com.project.plateapi.user_role.domain.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserService {

    private final UserRepository userRepository;
    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void updateUser(UserInfoRequest dto) {
        Users user = userRepository.findByUserId(dto.userId());
        String encodedPw = passwordEncoder.encode(dto.userPassword());

        user.updateInfo(dto,encodedPw);
    }

    @Transactional
    public Long createUser(Users user) {
        checkUser(user);
        saveUser(user);

        return user.getId();
    }

    private void checkUser(Users user) {
        String nickname = user.getNickname();
        String email = user.getEmail();

        if (userRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("Nickname already exists");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    private void saveUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        user.setEnabled(true);
        em.persist(user);

        Role role = em.find(Role.class, 1L);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        em.persist(userRole);

        em.flush();
    }

    @Transactional
    public void deleteUser(CustomUser customUser, String userId) {
        Users user = customUser.getUser();
        String jwtUserId = user.getUserId();

        if (!jwtUserId.equals(userId)) {
            throw new IllegalArgumentException("Invalid");
        }

        userRepository.deleteByUserId(userId);
    }


    public UserInfoResponse retrieveUserInfo(CustomUser customUser) {
        Users user = customUser.getUser();
        UserInfoResponse dto = new UserInfoResponse(user);

        if (user.getUserId() == null) {
            throw new UserNotFoundException("Not user");
        }
        return dto;
    }
}

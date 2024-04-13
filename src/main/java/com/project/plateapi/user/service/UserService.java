package com.project.plateapi.user.service;

import com.project.plateapi.role.domain.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.domain.UserRepository;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user.dto.UserInfoResponseDto;
import com.project.plateapi.user.dto.UserRequestDto;
import com.project.plateapi.user.exception.UserNotFoundException;
import com.project.plateapi.user_role.domain.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;


    @Transactional
    public ResponseEntity<Void> updateUser(UserRequestDto dto) {
        Users user = userRepository.findByUserId(dto.getUserId());

        String userPassword = dto.getUserPassword();
        String encodedPw = passwordEncoder.encode(userPassword);

        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setUserPassword(encodedPw);
        user.setEmail(dto.getEmail());

        user.update(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Void> createUser(Users user) {
        checkUser(user);
        saveUser(user);

        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> deleteUser(CustomUser customUser, String userId) {
        Users user = customUser.getUser();
        String jwtUserId = user.getUserId();

        if (!jwtUserId.equals(userId)) {
            throw new IllegalArgumentException("Invalid");
        }

        userRepository.deleteByUserId(userId);

        return ResponseEntity.noContent().build();
    }

    public void login(Users user, HttpServletRequest request) throws Exception {
        String userId = user.getUserId();
        String password = user.getUserPassword();
        log.info("User " + userId);
        log.info("Password " + password);

        //아이디 패스워드 인증 토큰 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, password);

        // 토큰에 요청정보 등록
        token.setDetails(new WebAuthenticationDetails(request));

        //토큰을 이용해서 인증 요청- 로그인
        Authentication authentication = authenticationManager.authenticate(token);

        log.info("인증여부" + authentication.isAuthenticated());

        User authUser = (User) authentication.getPrincipal();
        log.info("사용자 아이디" + authUser.getName());

        //시큐리티 컨텍스트에 인증된 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional
    public ResponseEntity<?> retrieveUserInfo(CustomUser customUser) {
        Users user = customUser.getUser();
        UserInfoResponseDto dto = new UserInfoResponseDto(user);

        // 인증된 사용자 정보
        if (user != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        // 인증되지 않은 사용자 정보
        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }
}

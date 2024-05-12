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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void updateUser(UserInfoRequest dto) {
        Users user = userRepository.findByUserId(dto.userId());
        String encodedPw = passwordEncoder.encode(dto.userPassword());

        user.updateInfo(dto, encodedPw);
    }

    @Transactional
    public void createUser(UserInfoRequest request) {
        checkUser(request);
        saveUser(request);
    }

    private void checkUser(UserInfoRequest request) {
        String nickname = request.nickname();
        String email = request.email();

        if (userRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("Nickname already exists");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    private void saveUser(UserInfoRequest request) {
        String encodedPassword = passwordEncoder.encode(request.userPassword());
        Users user = Users.builder()
                .userId(request.userId())
                .userPassword(encodedPassword)
                .name(request.name())
                .nickname(request.nickname())
                .email(request.email())
                .enabled(true)
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
        em.persist(user);

        Role role = em.find(Role.class, 1L);

        UserRole userRole = new UserRole();
        userRole.setRole(user,role);

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

        Users userInfo = userRepository.findByUserId(userId);
        userInfo.closeUser();
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

    public UserInfoResponse retrieveUserInfo(CustomUser customUser) {
        Users user = customUser.getUser();
        UserInfoResponse dto = new UserInfoResponse(user);

        if (user.getUserId() == null) {
            throw new UserNotFoundException("Not user");
        }
        return dto;
    }
}

package com.project.plateapi.user.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.role.domain.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.controller.dto.request.UserInfoRequest;
import com.project.plateapi.user.domain.UserRepository;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user.service.dto.response.UserInfoResponse;
import com.project.plateapi.user_role.domain.UserRole;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@SpringBootTest
class UserServiceTest {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ID = "ace1225";
    private static final String PASSWORD = "1q2W3e4r!";
    private static final String NAME = "김철수";
    private static final String NICKNAME = "불주먹";
    private static final String EMAIL = "ace1225@naver.com";
    private Users savedHost;

    @BeforeEach
    void setUp() {
        savedHost = userRepository.save(Users.builder()
                .id(1L)
                .userId(ID)
                .userPassword(passwordEncoder.encode(PASSWORD))
                .name(NAME)
                .nickname(NICKNAME)
                .email(EMAIL)
                .enabled(true)
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build());
    }


    @DisplayName("회원 가입을 한다")
    @Test
    void signUp() {
        userService.createUser(new UserInfoRequest("0h22", "oh12234", "oh22@naver.com", "김영희", "안녕희"));
        Users user = userRepository.findByUserId("0h22");

        assertThat(user.getName()).isEqualTo("김영희");
        assertThat(user.getNickname()).isEqualTo("안녕희");
        assertThat(user.getEmail()).isEqualTo("oh22@naver.com");
    }


    @DisplayName("유저 정보를 수정한다")
    @Test
    void updateUserInfo() {
        String changedPassword = "ace1234";
        String changedName = "홍길동";
        String changedNickName = "은하철도999";
        String changedEmail = "rail999@gmail.com";
        UserInfoRequest request = new UserInfoRequest(ID, changedPassword, changedName, changedNickName, changedEmail);

        userService.updateUser(request);
        Users user = userRepository.findByUserId(ID);

        assertThat(user.getUserPassword()).isNotEqualTo(passwordEncoder.encode(PASSWORD));
        assertThat(user.getName()).isNotEqualTo(NAME);
        assertThat(user.getNickname()).isNotEqualTo(NICKNAME);
        assertThat(user.getEmail()).isNotEqualTo(EMAIL);
    }

    @Nested
    class customUserTest {
        private CustomUser customUser;

        @BeforeEach
        void setup() {
            Role role = new Role(1L, "USER");

            UserRole userRole = new UserRole();
            userRole.setRole(savedHost, role);

            List<UserRole> roles = new ArrayList<>();
            roles.add(userRole);
            savedHost.setUserRoles(roles);

            customUser = new CustomUser(savedHost);

            Authentication auth = new UsernamePasswordAuthenticationToken(customUser, null,
                    customUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        @DisplayName("유저 정보를 가져온다")
        @Test
        void getUserInfo() {
            UserInfoResponse response = userService.retrieveUserInfo(customUser);

            assertThat(response.getUserId()).isEqualTo(ID);
            assertThat(response.getName()).isEqualTo(NAME);
            assertThat(response.getNickname()).isEqualTo(NICKNAME);
            assertThat(response.getEmail()).isEqualTo(EMAIL);
            assertThat(response.getUserRoles().getUserRole().contains(new Role(1L, "USER"))).isEqualTo(true);
        }

        @DisplayName("회원정보를 삭제한다")
        @Test
        void deleteUser() {
            userService.deleteUser(customUser, ID);
        }
    }


}

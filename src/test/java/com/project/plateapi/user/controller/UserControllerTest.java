package com.project.plateapi.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.role.domain.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.controller.dto.request.UserInfoRequest;
import com.project.plateapi.user.domain.UserRepository;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user.service.UserService;
import com.project.plateapi.user_role.domain.UserRole;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@SpringBootTest
class UserControllerTest {

    private static final String ID = "ace1225";
    private static final String PASSWORD = "1q2W3e4r!";
    private static final String NAME = "김철수";
    private static final String NICKNAME = "불주먹";
    private static final String EMAIL = "ace1225@naver.com";
    private static final String BASE_URL = "/api/users";


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp() {
        Users user = Users.builder()
                .id(1L)
                .userId(ID)
                .userPassword(passwordEncoder.encode(PASSWORD))
                .name(NAME)
                .nickname(NICKNAME)
                .email(EMAIL)
                .enabled(true)
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        Role role = new Role(1L, "USER");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);
        user.setUserRoles(roles);

        CustomUser customUser = new CustomUser(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @DisplayName("회원가입이 정상적으로 되는지 테스트한다")
    @Test
    void signUp() throws Exception {
        String content = objectMapper.writeValueAsString(
                new UserInfoRequest("0hee", "o01123", "김영희", "00", "0h22@naver.com"));
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("유저 정보를 조회할 수 있는지 테스트한다")
    @Test
    void getUserInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.userId").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.nickname").value(NICKNAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.userRoles.userRole[0].role").value("USER"));
    }

    @DisplayName("유저 정보가 수정되는지 테스트한다")
    @Test
    void changeUserInfo() throws Exception {
        String changedName = "홍길동";
        String changedPw = "ace1234";
        String changedNickName = "은하철도999";
        String changeEmail = "rail999@gmail.com";
        String content = objectMapper.writeValueAsString(
                new UserInfoRequest(ID, changedPw, changedName, changedNickName, changeEmail));

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("유저가 삭제되는지 테스트한다")
    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }


}

package com.project.plateapi.discussion.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.comment.controller.dto.request.CommentRequestDto;
import com.project.plateapi.comment.service.CommentService;
import com.project.plateapi.discussion.controller.dto.request.DiscussionRequestDto;
import com.project.plateapi.discussion.service.DiscussionService;
import com.project.plateapi.restaurant.service.RestaurantService;
import com.project.plateapi.role.domain.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.controller.dto.request.UserInfoRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@SpringBootTest
class DiscussionControllerTest {

    private final String BASE_URL = "/api/discussion";
    private CustomUser customUser;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DiscussionService discussionService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp() {
        Users user = Users.builder()
                .id(1L)
                .userId("ace1225")
                .userPassword("1234")
                .name("김철수")
                .nickname("불주먹")
                .email("ace1225@naver.com")
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

        customUser = new CustomUser(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        saveUsers("ace1225", "1234", "ace1225@naver.com", "김철수", "불주먹");
        saveDiscussion("토론제목1", "내용1");
        saveDiscussion("토론제목2", "내용2");
        saveComment("댓글1");
    }

    @DisplayName("토론이 정상적으로 생성되는지 테스트한다")
    @Test
    void createDiscussion() throws Exception {
        String content = objectMapper.writeValueAsString(new DiscussionRequestDto("토론주제", "내용"));
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("토론을 찾을 수 있는지 테스트한다")
    @Test
    void findDiscussion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{discussion_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.title").value("토론제목1"))
                .andExpect(jsonPath("$.content").value("내용1"))
                .andExpect(jsonPath("$.userId.userId").value("ace1225"))
                .andExpect(jsonPath("$.comments.comment[0].comment").value("댓글1"))
                .andExpect(jsonPath("$.comments.comment[0].userNickname").value("불주먹"));
    }

    @DisplayName("토론을 모두 찾을 수 있는지 테스트한다")
    @Test
    void findAllDiscussion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.discussions.discussions", hasSize(2)));
    }

    @DisplayName("토론이 수정되는지 테스트한다")
    @Test
    void editDiscussion() throws Exception {
        String content = objectMapper.writeValueAsString(new DiscussionRequestDto("변경제목1", "변경내용1"));
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{discussion_id}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("토론이 삭제되는지 테스트한다")
    @Test
    void deleteDiscussion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{discussion_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    private void saveComment(String comment) {
        CommentRequestDto request = new CommentRequestDto(comment);
        commentService.createComment(customUser, 1L, request);
    }

    private void saveDiscussion(String title, String content) {
        DiscussionRequestDto request = new DiscussionRequestDto(title, content);
        discussionService.createDiscussion(customUser, request);
    }

    private void saveUsers(String userId, String userPassword, String email, String name, String nickname) {
        UserInfoRequest request = new UserInfoRequest(userId, userPassword, email, name, nickname);
        userService.createUser(request);
    }
}

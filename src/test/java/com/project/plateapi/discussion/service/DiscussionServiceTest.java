package com.project.plateapi.discussion.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.comment.controller.dto.request.CommentRequestDto;
import com.project.plateapi.comment.service.CommentService;
import com.project.plateapi.discussion.controller.dto.request.DiscussionRequestDto;
import com.project.plateapi.discussion.service.dto.response.DiscussionListResponseDto;
import com.project.plateapi.discussion.service.dto.response.DiscussionResponseDto;
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
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@RequiredArgsConstructor
@SpringBootTest
class DiscussionServiceTest {
    private final DiscussionService discussionService;
    private final CommentService commentService;
    private final UserService userService;
    private CustomUser customUser;

    @BeforeEach
    void setUp() {
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

    @DisplayName("토론들이 모두 조회되는지 테스트한다")
    @Test
    void findAllDiscussion() {
        DiscussionListResponseDto response = discussionService.getAllDiscussion(false);

        assertThat(response.getDiscussions().getDiscussions().size()).isEqualTo(2);
    }

    @DisplayName("아이디로 제안이 조회되는지 테스트한다")
    @Test
    void findDiscussion() {
        DiscussionResponseDto response = discussionService.getDiscussion(1L);

        assertThat(response.getTitle()).isEqualTo("토론제목1");
        assertThat(response.getContent()).isEqualTo("내용1");
        assertThat(response.getUserId().getUserId()).isEqualTo("ace1225");
        assertThat(response.getComments().getComment().size()).isEqualTo(1);

    }

    @DisplayName("토론이 생성되는지 테스트한다")
    @Test
    void createDiscussion() {
        DiscussionRequestDto request = new DiscussionRequestDto("토론제목3", "내용3");

        Long id = discussionService.createDiscussion(customUser, request);

        assertThat(id).isEqualTo(3);
    }

    @DisplayName("토론이 삭제되었는지 테스트한다")
    @Test
    void deleteDiscussion() {
        discussionService.deleteDiscussion(customUser, 1L);

        assertThatThrownBy(() -> {
            discussionService.getDiscussion(1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("토론이 수정되는지 테스트한다")
    @Test
    void editDiscussion() {
        DiscussionRequestDto changeRequest = new DiscussionRequestDto("바뀐제목1", "바뀐내용1");

        discussionService.editDiscussion(customUser, 1L, changeRequest);
        DiscussionResponseDto response = discussionService.getDiscussion(1L);

        assertThat(response.getTitle()).isEqualTo("바뀐제목1");
        assertThat(response.getContent()).isEqualTo("바뀐내용1");
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

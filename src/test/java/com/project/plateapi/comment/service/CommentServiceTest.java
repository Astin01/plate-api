package com.project.plateapi.comment.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.comment.controller.dto.request.CommentRequestDto;
import com.project.plateapi.comment.service.dto.response.CommentResponseDto;
import com.project.plateapi.comment.service.dto.response.CommentResponseDto.UserCommentDto.ModifyCommentDto;
import com.project.plateapi.discussion.controller.dto.request.DiscussionRequestDto;
import com.project.plateapi.discussion.service.DiscussionService;
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
class CommentServiceTest {
    private final CommentService commentService;
    private final DiscussionService discussionService;
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
        saveComment("댓글1");
        saveComment("댓글2");
    }

    @DisplayName("댓글이 조회되는지 테스트한다")
    @Test
    void findComment() {
        CommentResponseDto response = commentService.getComment(1L);

        List<ModifyCommentDto> comments = response.getCommentContent().getComments();

        assertThat(comments.get(0).getComment()).isEqualTo("댓글1");
        assertThat(comments.get(1).getComment()).isEqualTo("댓글2");
    }

    @DisplayName("댓글이 생성되는지 테스트한다")
    @Test
    void createComment() {
        CommentRequestDto request = new CommentRequestDto("내용3");

        Long id = commentService.createComment(customUser, 1L, request);

        assertThat(id).isEqualTo(3);
    }

    @DisplayName("댓글이 삭제되었는지 테스트한다")
    @Test
    void deleteComment() {
        commentService.deleteComment(1L);

        CommentResponseDto response = commentService.getComment(1L);

        assertThat(response.getCommentContent().getComments().size()).isEqualTo(1);
    }

    @DisplayName("댓글이 수정되는지 테스트한다")
    @Test
    void editComment() {
        CommentRequestDto changeRequest = new CommentRequestDto("바뀐댓글");

        commentService.updateComment(1L, changeRequest);
        CommentResponseDto response = commentService.getComment(1L);
        List<ModifyCommentDto> comments = response.getCommentContent().getComments();

        assertThat(comments.get(0).getComment()).isEqualTo("바뀐댓글");
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

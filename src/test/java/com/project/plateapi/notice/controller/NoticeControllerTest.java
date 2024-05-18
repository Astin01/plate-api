package com.project.plateapi.notice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.comment.controller.dto.request.CommentRequestDto;
import com.project.plateapi.comment.service.CommentService;
import com.project.plateapi.discussion.controller.dto.request.DiscussionRequestDto;
import com.project.plateapi.discussion.service.DiscussionService;
import com.project.plateapi.notice.controller.dto.request.NoticeRequestDto;
import com.project.plateapi.notice.service.NoticeService;
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
class NoticeControllerTest {
    private final String BASE_URL = "/api/notice";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    NoticeService noticeService;

    @BeforeEach
    public void setUp() {
        saveNotice("공지1", "내용1", "url1");
        saveNotice("공지2", "내용2", "url2");
    }

    @DisplayName("공지가 정상적으로 생성되는지 테스트한다")
    @Test
    void createNotice() throws Exception {
        String content = objectMapper.writeValueAsString(new NoticeRequestDto("공지3", "내용3", "url3"));
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("공지를 모두 찾을 수 있는지 테스트한다")
    @Test
    void findAllNotice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.notices.size()").value(2));
    }

    @DisplayName("공지를 찾을 수 있는지 테스트한다")
    @Test
    void findNotice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{notice_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("공지가 수정되는지 테스트한다")
    @Test
    void editNotice() throws Exception {
        String content = objectMapper.writeValueAsString(new NoticeRequestDto("수정공지1", "수정내용1", "수정url1"));
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{notice_id}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("공지가 삭제되는지 테스트한다")
    @Test
    void deleteNotice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{notice_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    private void saveNotice(String title, String content, String imageUrl) {
        NoticeRequestDto request = new NoticeRequestDto(title, content, imageUrl);
        noticeService.createNotice(request);
    }
}

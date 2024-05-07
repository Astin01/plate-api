package com.project.plateapi.notice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.notice.controller.dto.request.NoticeRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class NoticeControllerTest {

    private static final String BASE_URL = "/api/notice";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("공지가 모두 조회되는지 테스트한다")
    @Test
    void findAllNotice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.notices", hasSize(2)));
    }

    @DisplayName("공지가 조회되는지 테스트한다")
    @Test
    void findNotice() throws Exception {
        Long noticeId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/{notice_id}",noticeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("공지가 생성되는지 테스트한다")
    @Test
    void createNotice() throws Exception {
        NoticeRequestDto request = new NoticeRequestDto("공지합니다","공지입니다","https://fth.cg/z80.png");
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("공지가 수정되는지 테스트한다")
    @Test
    void editNotice() throws Exception {
        Long noticeId = 1L;
        NoticeRequestDto request = new NoticeRequestDto("공지","공지","http://image.png");
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL+"/{notice_id}",noticeId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("공지가 삭제되는지 테스트한다")
    @Test
    void deleteNotice() throws Exception {
        Long noticeId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL+"/{notice_id}",noticeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }
}

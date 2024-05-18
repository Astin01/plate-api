package com.project.plateapi.icon.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.icon.controller.dto.request.IconRequestDto;
import com.project.plateapi.icon.service.IconService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@SpringBootTest
class IconControllerTest {
    private final String BASE_URL = "/api/icon";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    IconService iconService;

    @BeforeEach
    public void setUp() {
        saveIcon("아이콘1", "이름1", "url1");
        saveIcon("아이콘2", "이름2", "url2");
    }

    @DisplayName("아이콘이 정상적으로 생성되는지 테스트한다")
    @Test
    void createIcon() throws Exception {
        String content = objectMapper.writeValueAsString(new IconRequestDto("아이콘3", "이름3", "url3"));
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("아이콘을 모두 찾을 수 있는지 테스트한다")
    @Test
    void findAllIcon() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.iconList.size()").value(2));
    }

    @DisplayName("아이콘이 수정되는지 테스트한다")
    @Test
    void editIcon() throws Exception {
        String content = objectMapper.writeValueAsString(new IconRequestDto("수정아이콘1", "수정이름1", "수정url1"));
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{icon_id}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("아이콘이 삭제되는지 테스트한다")
    @Test
    void deleteIcon() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{notice_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    private void saveIcon(String icon, String name, String link) {
        IconRequestDto request = new IconRequestDto(icon, name, link);
        iconService.createIcon(request);
    }
}

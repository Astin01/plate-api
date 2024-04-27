package com.project.plateapi.icon.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class IconControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("아이콘들이 모두 조회되는지 테스트한다")
    @Test
    void findAllIcon() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/icon")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.iconList[0].link").value("ko"))
                .andExpect(jsonPath("$.iconList[1].link").value("jp"))
                .andExpect(jsonPath("$.iconList[2].link").value("ch"))
                .andExpect(jsonPath("$.iconList[3].link").value("eu"))
                .andExpect(jsonPath("$.iconList[4].link").value("etc"))
                .andExpect(jsonPath("$.iconList[5].link").value("cafe"));
    }
}

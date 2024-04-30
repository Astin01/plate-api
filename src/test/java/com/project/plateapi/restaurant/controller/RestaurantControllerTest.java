package com.project.plateapi.restaurant.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
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
class RestaurantControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "김밥천국";
    private static final String CATEGORY = "ko";
    private static final String ICON = "stockpot";
    private static final String CONTENT = "김밥천국은 간단하게 끼니를 떼우기 좋은 분식집이다";
    private static final String BASE_URL = "/api/restaurants";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @DisplayName("음식점들이 모두 조회되는지 테스트한다")
    @Test
    void findAllRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.restaurantList", hasSize(70)));
    }

    @DisplayName("음식점이 생성되는지 테스트한다")
    @Test
    void createRestaurant() throws Exception {
        RestaurantRequest request = new RestaurantRequest(NAME, CATEGORY, ICON, CONTENT);
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("카테고리별로 음식점들이 조회되는지 테스트한다")
    @Test
    void findRestaurantByCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/category/{category}", "ko")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.restaurantList", hasSize(21)));
    }

    @DisplayName("이름으로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/name/{name}", "밥은")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("아이디로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("음식점 정보가 수정되는지 테스트한다")
    @Test
    void changeRestaurantInfo() throws Exception {
        String changedName = "맘스터치";
        String changedCategory = "eu";
        String changedIcon = "lunch_dining";
        String changedContent = "싸이버거로 대표되는 버거집";
        RestaurantRequest request = new RestaurantRequest(changedName, changedCategory, changedIcon, changedContent);
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL+"/id/{id}",1L)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("음식점이 삭제되었는지 테스트한다")
    @Test
    void deleteRestaurant() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL+"/id/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

}


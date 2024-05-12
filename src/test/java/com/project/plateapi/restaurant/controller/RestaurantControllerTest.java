package com.project.plateapi.restaurant.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.service.RestaurantService;
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
class RestaurantControllerTest {
    private static final String BASE_URL = "/api/restaurants";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RestaurantService restaurantService;

    @DisplayName("음식점들이 모두 조회되는지 테스트한다")
    @Test
    void findAllRestaurants() throws Exception {
        saveRestaurant("김밥천국", "ko", "stockpot", "분식집");
        saveRestaurant("피자헛", "eu", "dining_lunch", "피자집");

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.restaurantList", hasSize(2)));
    }

    @DisplayName("음식점이 생성되는지 테스트한다")
    @Test
    void createRestaurant() throws Exception {
        RestaurantRequest request = new RestaurantRequest("맘스터치", "eu", "dining_lunch", "햄버거집");
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$", is(1)));
    }

    @DisplayName("카테고리별로 음식점들이 조회되는지 테스트한다")
    @Test
    void findRestaurantByCategory() throws Exception {
        saveRestaurant("김밥천국", "ko", "stockpot", "분식집");
        saveRestaurant("피자헛", "eu", "dining_lunch", "피자집");
        saveRestaurant("맘스터치", "eu", "dining_lunch", "햄버거집");

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/category/{category}", "eu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.restaurantList", hasSize(2)));
    }

    @DisplayName("이름으로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantByName() throws Exception {
        saveRestaurant("김밥천국", "ko", "stockpot", "분식집");

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/name/{name}", "김밥천국")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.restaurant.name", is("김밥천국")))
                .andExpect(jsonPath("$.restaurant.category", is("ko")))
                .andExpect(jsonPath("$.restaurant.icon", is("stockpot")))
                .andExpect(jsonPath("$.restaurant.content", is("분식집")));
    }

    @DisplayName("아이디로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantById() throws Exception {
        saveRestaurant("김밥천국", "ko", "stockpot", "분식집");

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.restaurant.name", is("김밥천국")))
                .andExpect(jsonPath("$.restaurant.category", is("ko")))
                .andExpect(jsonPath("$.restaurant.icon", is("stockpot")))
                .andExpect(jsonPath("$.restaurant.content", is("분식집")));
    }

    @DisplayName("음식점 정보가 수정되는지 테스트한다")
    @Test
    void changeRestaurantInfo() throws Exception {
        saveRestaurant("김밥천국", "ko", "stockpot", "분식집");
        String changedName = "맘스터치";
        String changedCategory = "eu";
        String changedIcon = "lunch_dining";
        String changedContent = "싸이버거로 대표되는 버거집";
        RestaurantRequest request = new RestaurantRequest(changedName, changedCategory, changedIcon, changedContent);
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/id/{id}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("음식점이 삭제되었는지 테스트한다")
    @Test
    void deleteRestaurant() throws Exception {
        saveRestaurant("김밥천국", "ko", "stockpot", "분식집");

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/id/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    private Long saveRestaurant(String name, String category, String icon, String content) {
        RestaurantRequest request = new RestaurantRequest(name, category, icon, content);
        return restaurantService.createRestaurant(request);
    }
}


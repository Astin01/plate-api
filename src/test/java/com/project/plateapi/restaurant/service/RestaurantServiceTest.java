package com.project.plateapi.restaurant.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.domain.RestaurantRepository;
import com.project.plateapi.restaurant.service.dto.response.RestaurantListResponse;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@RequiredArgsConstructor
@SpringBootTest
class RestaurantServiceTest {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository.save(createRestaurant("김밥천국", "ko", "stockpot", "분식집"));
        restaurantRepository.save(createRestaurant("맘스터치", "eu", "dinner_dining", "햄버거집"));
        restaurantRepository.save(createRestaurant("피자헛", "eu", "dinner_dining", "피자집"));
    }

    @DisplayName("음식점들이 모두 조회되는지 테스트한다")
    @Test
    void findAllRestaurant() {
        RestaurantListResponse response = restaurantService.findAllRestaurants();

        assertThat(response.restaurantList().size()).isEqualTo(3);
    }

    @DisplayName("카테고리별로 음식점들이 조회되는지 테스트한다")
    @Test
    void findAllRestaurantsByCategory() {
        String category = "ko";
        RestaurantListResponse response = restaurantService.findAllRestaurantsByCategory(category);

        assertThat(response.restaurantList().size()).isEqualTo(1);
    }

    @DisplayName("이름으로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantByName() {
        String name = "김밥천국";
        RestaurantResponse response = restaurantService.findRestaurantByName(name);

        assertThat(response.restaurant().getName()).isEqualTo("김밥천국");
        assertThat(response.restaurant().getIcon()).isEqualTo("stockpot");
        assertThat(response.restaurant().getCategory()).isEqualTo("ko");
        assertThat(response.restaurant().getContent()).isEqualTo("분식집");
    }

    @DisplayName("아이디로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantById() {
        Long id = 1L;
        RestaurantResponse response = restaurantService.findRestaurantById(id);

        assertThat(response.restaurant().getName()).isEqualTo("김밥천국");
        assertThat(response.restaurant().getIcon()).isEqualTo("stockpot");
        assertThat(response.restaurant().getCategory()).isEqualTo("ko");
        assertThat(response.restaurant().getContent()).isEqualTo("분식집");
    }

    @DisplayName("음식점이 생성되는지 테스트한다")
    @Test
    void createRestaurant() {
        RestaurantRequest request = new RestaurantRequest("설빙", "cf", "cafe", "빙수집");
        Long id = restaurantService.createRestaurant(request);

        assertThat(id).isEqualTo(4);
    }

    @DisplayName("음식점이 삭제되었는지 테스트한다")
    @Test
    void deleteRestaurant() {
        restaurantService.deleteRestaurant(1L);

        assertThatThrownBy(() -> {
            restaurantService.findRestaurantById(1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음식점 정보가 수정되는지 테스트한다")
    @Test
    void changeRestaurantInfo() {
        String changedName = "맥도날드";
        String changedCategory = "eu";
        String changedIcon = "lunch_dining";
        String changedContent = "햄버거집";
        RestaurantRequest request = new RestaurantRequest(changedName, changedCategory, changedIcon, changedContent);

        restaurantService.changeRestaurantInfo(1L, request);

        RestaurantResponse restaurant = restaurantService.findRestaurantById(1L);

        assertThat(restaurant.restaurant().getName()).isEqualTo(changedName);
        assertThat(restaurant.restaurant().getCategory()).isEqualTo(changedCategory);
        assertThat(restaurant.restaurant().getIcon()).isEqualTo(changedIcon);
        assertThat(restaurant.restaurant().getContent()).isEqualTo(changedContent);
    }

    private Restaurant createRestaurant(String name, String category, String icon, String content) {
        return Restaurant.builder()
                .name(name)
                .category(category)
                .icon(icon)
                .content(content)
                .build();
    }
}

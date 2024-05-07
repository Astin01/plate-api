package com.project.plateapi.restaurant.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.domain.RestaurantRepository;
import com.project.plateapi.restaurant.service.dto.response.RestaurantListResponse;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class RestaurantServiceTest {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    private static final Long ID = 1L;
    private static final String NAME = "김밥천국";
    private static final String CATEGORY = "ko";
    private static final String ICON = "stockpot";
    private static final String CONTENT = "김밥천국은 간단하게 끼니를 떼우기 좋은 분식집이다";

    @DisplayName("음식점들이 모두 조회되는지 테스트한다")
    @Test
    void findAllRestaurant() {
        RestaurantListResponse response = restaurantService.findAllRestaurants();

        assertThat(response.restaurantList().size()).isEqualTo(70);
    }

    @DisplayName("카테고리별로 음식점들이 조회되는지 테스트한다")
    @Test
    void findAllRestaurantsByCategory() {
        String category = "ko";
        RestaurantListResponse response = restaurantService.findAllRestaurantsByCategory(category);

        assertThat(response.restaurantList().size()).isEqualTo(21);
    }

    @DisplayName("이름으로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantByName() {
        String name = "밥은";
        RestaurantResponse response = restaurantService.findRestaurantByName(name);

        assertThat(response.restaurant().getName()).isEqualTo("밥은");
        assertThat(response.restaurant().getIcon()).isEqualTo("stockpot");
        assertThat(response.restaurant().getCategory()).isEqualTo("ko");
        assertThat(response.restaurant().getContent()).isEqualTo("착한 가격에 많은 양, 거리도 학교 정문에서 가깝다");
    }

    @DisplayName("아이디로 음식점이 조회되는지 테스트한다")
    @Test
    void findRestaurantById() {
        Long id = 21L;
        RestaurantResponse response = restaurantService.findRestaurantById(id);

        assertThat(response.restaurant().getName()).isEqualTo("밥은");
        assertThat(response.restaurant().getIcon()).isEqualTo("stockpot");
        assertThat(response.restaurant().getCategory()).isEqualTo("ko");
        assertThat(response.restaurant().getContent()).isEqualTo("착한 가격에 많은 양, 거리도 학교 정문에서 가깝다");
    }

    @DisplayName("음식점이 생성되는지 테스트한다")
    @Test
    void createRestaurant() {
        RestaurantRequest request = new RestaurantRequest(NAME, CATEGORY, ICON, CONTENT);
//        Long name = restaurantService.createRestaurant(request);

    }

    @DisplayName("음식점이 삭제되었는지 테스트한다")
    @Test
    void deleteRestaurant(){
        restaurantService.deleteRestaurant(ID);

        Mockito.verify(restaurantRepository).deleteById(ID);
    }

    @DisplayName("음식점 정보가 수정되는지 테스트한다")
    @Test
    void changeRestaurantInfo(){
        String changedName = "맘스터치";
        String changedCategory = "eu";
        String changedIcon = "lunch_dining";
        String changedContent = "싸이버거로 대표되는 버거집";
        RestaurantRequest request = new RestaurantRequest(changedName, changedCategory, changedIcon, changedContent);

        restaurantService.changeRestaurantInfo(ID,request);

        RestaurantResponse restaurant = restaurantService.findRestaurantById(ID);

        assertThat(restaurant.restaurant().getName()).isEqualTo(changedName);
        assertThat(restaurant.restaurant().getCategory()).isEqualTo(changedCategory);
        assertThat(restaurant.restaurant().getIcon()).isEqualTo(changedIcon);
        assertThat(restaurant.restaurant().getContent()).isEqualTo(changedContent);
    }

}

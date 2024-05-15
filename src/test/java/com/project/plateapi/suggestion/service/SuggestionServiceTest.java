package com.project.plateapi.suggestion.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.service.RestaurantService;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import com.project.plateapi.role.domain.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.suggestion.controller.dto.request.SuggestionRequest;
import com.project.plateapi.suggestion.service.dto.response.SuggestionListResponse;
import com.project.plateapi.suggestion.service.dto.response.SuggestionResponse;
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
class SuggestionServiceTest {
    private final SuggestionService suggestionService;
    private final RestaurantService restaurantService;
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
        saveRestaurant("김밥천국", "ko", "stockpot", "분식집");
        saveSuggestion("이건어떨까요", "내용");
        saveSuggestion("이렇게 바꿔요", "내용2");
    }

    @DisplayName("제안들이 모두 조회되는지 테스트한다")
    @Test
    void findAllSuggestion() {
        SuggestionListResponse response = suggestionService.findAllSuggestion();

        assertThat(response.suggestionList().size()).isEqualTo(2);
    }

    @DisplayName("제안이 조회되는지 테스트한다")
    @Test
    void findSuggestion() {
        SuggestionResponse response = suggestionService.findSuggestion(1L);

        assertThat(response.title()).isEqualTo("이건어떨까요");
        assertThat(response.content()).isEqualTo("내용");
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

    @DisplayName("제안이 생성되는지 테스트한다")
    @Test
    void createSuggestion() {
        SuggestionRequest request = new SuggestionRequest("이건괜찮나요", "내용3");

        Long id = suggestionService.createSuggestion(customUser, 1L, request);

        assertThat(id).isEqualTo(3);
    }

    @DisplayName("제안이 삭제되었는지 테스트한다")
    @Test
    void deleteSuggestion() {
        suggestionService.deleteSuggestion(customUser, 1L);

        assertThatThrownBy(() -> {
            suggestionService.findSuggestion(1L);
        }).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("제안이 수정되는지 테스트한다")
    @Test
    void editSuggestion() {
        SuggestionRequest changeRequest = new SuggestionRequest("이걸로해요", "바뀐내용");

        suggestionService.editSuggestion(customUser, 1L, changeRequest);
        SuggestionResponse response = suggestionService.findSuggestion(1L);

        assertThat(response.title()).isEqualTo("이걸로해요");
        assertThat(response.content()).isEqualTo("바뀐내용");
    }

    @DisplayName("제안이 위키에 반영되는지 테스트한다")
    @Test
    void putSuggestionToRestaurant() {
        suggestionService.putSuggestionToRestaurant(1L);
        RestaurantResponse restaurantResponse = restaurantService.findRestaurantById(1L);

        assertThat(restaurantResponse.restaurant().getContent()).isEqualTo("내용");
    }

    private void saveRestaurant(String name, String category, String icon, String content) {
        RestaurantRequest request = new RestaurantRequest(name, category, icon, content);
        restaurantService.createRestaurant(request);
    }

    private void saveSuggestion(String title, String content) {
        SuggestionRequest request = new SuggestionRequest(title, content);
        suggestionService.createSuggestion(customUser, 1L, request);
    }

    private void saveUsers(String userId, String userPassword, String email, String name, String nickname) {
        UserInfoRequest request = new UserInfoRequest(userId, userPassword, email, name, nickname);
        userService.createUser(request);
    }
}

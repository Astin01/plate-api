package com.project.plateapi.suggestion.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.service.RestaurantService;
import com.project.plateapi.role.domain.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.suggestion.controller.dto.request.SuggestionRequest;
import com.project.plateapi.suggestion.service.SuggestionService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@SpringBootTest
class SuggestionControllerTest {

    private final String BASE_URL = "/api/suggestion";
    private CustomUser customUser;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SuggestionService suggestionService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp() {
        Users user = Users.builder()
                .id(1L)
                .userId("ace1225")
                .userPassword(passwordEncoder.encode("1234"))
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

    @DisplayName("제안이 정상적으로 생성되는지 테스트한다")
    @Test
    void createSuggestion() throws Exception {
        String content = objectMapper.writeValueAsString(new SuggestionRequest("제안합니다", "내용"));
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/{restaurant_id}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("제안을 찾을 수 있는지 테스트한다")
    @Test
    void findSuggestion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{suggestion_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.title").value("이건어떨까요"))
                .andExpect(jsonPath("$.content").value("내용"));
    }

    @DisplayName("제안을 모두 찾을수 있는지 테스트한다")
    @Test
    void findAllSuggestion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.suggestionList", hasSize(2)));
    }

    @DisplayName("제안이 수정되는지 테스트한다")
    @Test
    void editSuggestion() throws Exception {
        String content = objectMapper.writeValueAsString(new SuggestionRequest("제안합니다", "내용"));
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{suggestion_id}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("제안이 삭제되는지 테스트한다")
    @Test
    void deleteSuggestion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{suggestion_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @DisplayName("제안이 음식점내용에 반영되는지 테스트한다")
    @Test
    void putSuggestionToRestaurant() throws Exception {
        String content = objectMapper.writeValueAsString(new SuggestionRequest("제안합니다", "내용"));
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{suggestion_id}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value()));
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

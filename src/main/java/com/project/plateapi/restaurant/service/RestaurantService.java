package com.project.plateapi.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.plateapi.restaurant.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.domain.RestaurantRepository;
import com.project.plateapi.restaurant.service.dto.response.RestaurantListResponse;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import com.project.plateapi.restaurant.support.RestaurantConnector;
import com.project.plateapi.user.domain.UserPreference;
import com.project.plateapi.user.domain.UserPreferenceRepository;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantConnector restaurantConnector;
    private final UserPreferenceRepository userPreferenceRepository;

    public RestaurantListResponse findAllRestaurants() {
        return new RestaurantListResponse(restaurantRepository.findAll());
    }

    public RestaurantListResponse findAllRestaurantsByCategory(String category) {
        return new RestaurantListResponse(restaurantRepository.findAllByCategory(category));
    }

    public RestaurantResponse findRestaurantByName(String name) {
        String convertedName = URLDecoder.decode(name, StandardCharsets.UTF_8);

        return new RestaurantResponse(restaurantRepository.findByName(convertedName));
    }

    public RestaurantResponse findRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return new RestaurantResponse(restaurant);
    }

    @Transactional
    public Long createRestaurant(RestaurantRequest request) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .category(request.category())
                .icon(request.icon())
                .content(request.content())
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return savedRestaurant.getId();
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Transactional
    public void changeRestaurantInfo(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Restaurant updatedRestaurant = Restaurant.builder()
                .name(request.name())
                .icon(request.icon())
                .category(request.category())
                .content(request.content())
                .build();

        restaurant.update(updatedRestaurant);
    }

    public RestaurantListResponse getAllRecommendedRestaurantsByCategory(Users user, String category) throws JsonProcessingException {
        UserPreference userPreference = userPreferenceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
        RestaurantRecommendListResponse response =restaurantConnector.requestRecommendation(userPreference,category);

        List<Restaurant> restaurantListResponse = response.getRestaurantRecommendList().stream()
                .map(recommendation -> restaurantRepository.findByName(recommendation.getName()))
                .toList();

        return new RestaurantListResponse(restaurantListResponse);
    }
}

package com.project.plateapi.restaurant.service;

import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.domain.RestaurantRepository;
import com.project.plateapi.restaurant.service.dto.response.RestaurantListResponse;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

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
        Restaurant restaurant= Restaurant.builder()
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
}

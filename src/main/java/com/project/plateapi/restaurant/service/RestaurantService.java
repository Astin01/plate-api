package com.project.plateapi.restaurant.service;

import com.project.plateapi.restaurant.controller.dto.request.RestaurantUpdateDto;
import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.domain.RestaurantRepository;
import com.project.plateapi.restaurant.service.dto.response.RestaurantListResponse;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public ResponseEntity<RestaurantListResponse> findAllRestaurants() {
        RestaurantListResponse restaurantList = new RestaurantListResponse(restaurantRepository.findAll());

        return ResponseEntity.ok()
                .body(restaurantList);
    }

    public ResponseEntity<RestaurantListResponse> findAllRestaurantsByCategory(String category) {
        RestaurantListResponse restaurantList = new RestaurantListResponse(restaurantRepository.findAllByCategory(category));

        return ResponseEntity.ok()
                .body(restaurantList);
    }

    public ResponseEntity<RestaurantResponse> findRestaurantByName(String name) {
        String convertedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        RestaurantResponse restaurant = new RestaurantResponse(restaurantRepository.findByName(convertedName));

        return ResponseEntity.ok()
                .body(restaurant);
    }

    public ResponseEntity<RestaurantResponse> findRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        RestaurantResponse response = new RestaurantResponse(restaurant);

        return ResponseEntity.ok()
                .body(response);
    }

    @Transactional
    public ResponseEntity<Long> createRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);

        return ResponseEntity.ok()
                .body(restaurant.getId());
    }

    @Transactional
    public ResponseEntity<Void> deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Void> changeRestaurantInfo(Long id, RestaurantUpdateDto updateDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Restaurant updatedRestaurant = Restaurant.builder()
                .name(updateDto.getName())
                .icon(updateDto.getIcon())
                .category(updateDto.getCategory())
                .content(updateDto.getContent())
                .build();

        restaurant.update(updatedRestaurant);

        return ResponseEntity.ok().build();

    }
}

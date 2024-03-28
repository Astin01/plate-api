package com.project.plateapi.restaurant.service;

import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.domain.RestaurantRepository;
import com.project.plateapi.restaurant.controller.dto.request.RestaurantUpdateDto;
import jakarta.transaction.Transactional;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public ResponseEntity<?> findAllRestaurants() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        return new ResponseEntity<>(restaurantList,HttpStatus.OK);
    }

    public ResponseEntity<?> findAllRestaurantsByCategory(String category) {
        List<Restaurant> restaurantList = restaurantRepository.findAllByCategory(category);

        return new ResponseEntity<>(restaurantList,HttpStatus.OK);
    }

    public ResponseEntity<?> findRestaurantName(String name) {
        String convertedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        Restaurant restaurant = restaurantRepository.findByName(convertedName);

        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

    public ResponseEntity<Long> createRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);

        return new ResponseEntity<>(restaurant.getId(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> changeRestaurantInfo(Long id, RestaurantUpdateDto updateDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        restaurant.setName(updateDto.getName());
        restaurant.setIcon(updateDto.getIcon());
        restaurant.setCategory(updateDto.getCategory());
        restaurant.setContent(updateDto.getContent());

        restaurant.update(restaurant);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<?> findRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return new ResponseEntity<>(restaurant,HttpStatus.OK);

    }
}

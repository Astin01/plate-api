package com.project.plateapi.restaurant.controller;

import com.project.plateapi.restaurant.service.RestaurantService;
import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.controller.dto.request.RestaurantUpdateDto;
import com.project.plateapi.restaurant.service.dto.response.RestaurantListResponse;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping()
    public ResponseEntity<RestaurantListResponse> findAllRestaurants() {
        return service.findAllRestaurants();
    }

    @PostMapping()
    public ResponseEntity<Long> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return service.createRestaurant(restaurant);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<RestaurantListResponse> findAllRestaurantsByCategory(@PathVariable String category) {
        return service.findAllRestaurantsByCategory(category);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestaurantResponse> findRestaurant(@PathVariable String name) {
        return service.findRestaurantByName(name);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestaurantResponse> findRestaurantById(@PathVariable Long id) {
        return service.findRestaurantById(id);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Void> changeRestaurantInfo(@PathVariable Long id, @Valid @RequestBody RestaurantUpdateDto updateDto){
        return service.changeRestaurantInfo(id,updateDto);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        return service.deleteRestaurant(id);
    }
}
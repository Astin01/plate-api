package com.project.plateapi.restaurant.controller;

import com.project.plateapi.restaurant.controller.dto.request.RestaurantRequest;
import com.project.plateapi.restaurant.service.RestaurantService;
import com.project.plateapi.restaurant.service.dto.response.RestaurantListResponse;
import com.project.plateapi.restaurant.service.dto.response.RestaurantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        RestaurantListResponse response = service.findAllRestaurants();

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Void> createRestaurant(@Valid @RequestBody RestaurantRequest request) {
        service.createRestaurant(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<RestaurantListResponse> findAllRestaurantsByCategory(@PathVariable String category) {
        RestaurantListResponse response = service.findAllRestaurantsByCategory(category);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestaurantResponse> findRestaurant(@PathVariable String name) {
        RestaurantResponse response = service.findRestaurantByName(name);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestaurantResponse> findRestaurantById(@PathVariable Long id) {
        RestaurantResponse response = service.findRestaurantById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Void> changeRestaurantInfo(@PathVariable Long id,
                                                     @Valid @RequestBody RestaurantRequest request) {
        service.changeRestaurantInfo(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        service.deleteRestaurant(id);

        return ResponseEntity.noContent().build();
    }
}
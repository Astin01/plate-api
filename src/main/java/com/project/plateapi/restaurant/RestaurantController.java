package com.project.plateapi.restaurant;

import com.project.plateapi.restaurant.dto.RestaurantUpdateDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {
    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping("/api/restaurants")
    public ResponseEntity<?> findAllRestaurants() {
        return service.findAllRestaurants();
    }

    @PostMapping("/api/restaurants")
    public ResponseEntity<Long> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return service.createRestaurant(restaurant);
    }

    @GetMapping("/api/restaurants/category/{category}")
    public ResponseEntity<?> findAllRestaurantsByCategory(@PathVariable String category) {
        return service.findAllRestaurantsByCategory(category);
    }

    @GetMapping("/api/restaurants/name/{name}")
    public ResponseEntity<?> findRestaurant(@PathVariable String name) {
        return service.findRestaurantName(name);
    }

    @GetMapping("/api/restaurants/id/{id}")
    public ResponseEntity<?> findRestaurantById(@PathVariable Long id) {
        return service.findRestaurantById(id);
    }

    @PutMapping("/api/restaurants/id/{id}")
    public ResponseEntity<?> changeRestaurantInfo(@PathVariable Long id, @Valid @RequestBody RestaurantUpdateDto updateDto){
        return service.changeRestaurantInfo(id,updateDto);
    }

    @DeleteMapping("/api/restaurants/id/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        return service.deleteRestaurant(id);
    }
}
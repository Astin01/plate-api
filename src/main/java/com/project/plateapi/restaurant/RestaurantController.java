package com.project.plateapi.restaurant;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.restaurant.dto.RestaurantUpdateRequestDto;
import jakarta.validation.Valid;
import java.util.List;
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
    public List<Restaurant> retrieveRestaurants() {
        return service.findAllRestaurants();
    }

    @PostMapping("/api/restaurants")
    public void createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        service.createRestaurant(restaurant);
    }

    @GetMapping("/api/restaurants/category/{category}")
    public List<Restaurant> retrieveRestaurants(@PathVariable String category) {
        return service.findAllRestaurantsByCategory(category);
    }

    @GetMapping("/api/restaurants/name/{name}")
    public Restaurant retrieveRestaurant(@PathVariable String name) {
        return service.findRestaurantName(name);
    }

    @PutMapping("/api/restaurants/id/{id}")
    public void updateRestaurant(@PathVariable Long id, @RequestBody RestaurantUpdateRequestDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/api/restaurants/id/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        service.deleteRestaurant(id);
    }
}
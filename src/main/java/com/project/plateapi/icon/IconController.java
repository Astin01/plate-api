package com.project.plateapi.icon;

import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.restaurant.RestaurantService;
import com.project.plateapi.restaurant.dto.RestaurantUpdateRequestDto;
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
public class IconController {
    private final IconService service;

    public IconController(IconService service) {
        this.service = service;
    }

    @GetMapping("/api/icon")
    public ResponseEntity<?> findAllIcon() {
        return service.findAllIcon();
    }

}
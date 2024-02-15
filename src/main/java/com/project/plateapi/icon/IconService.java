package com.project.plateapi.icon;

import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.restaurant.RestaurantRepository;
import com.project.plateapi.restaurant.dto.RestaurantUpdateRequestDto;
import jakarta.transaction.Status;
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
public class IconService {
    private final IconRepository iconRepository;

    public ResponseEntity<?> findAllIcon() {
        List<Icon> icons = iconRepository.findAll();

        return new ResponseEntity<>(icons, HttpStatus.OK);
    }

}

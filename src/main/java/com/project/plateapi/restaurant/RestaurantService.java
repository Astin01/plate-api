package com.project.plateapi.restaurant;

import com.project.plateapi.restaurant.dto.RestaurantUpdateRequestDto;
import jakarta.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void update(Long id, RestaurantUpdateRequestDto updateRequestDto){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        restaurant.update(updateRequestDto.getName(), updateRequestDto.getCategory());
    }

    public List<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> findAllRestaurantsByCategory(String category) {
        return restaurantRepository.findAllByCategory(category);
    }

    public Restaurant findRestaurantName(String name) {
        String convertedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return restaurantRepository.findByName(convertedName);
    }

    public void createNewRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}

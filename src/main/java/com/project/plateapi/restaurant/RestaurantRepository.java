package com.project.plateapi.restaurant;

import com.project.plateapi.restaurant.dto.RestaurantUpdateRequestDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findAllByCategory(String category);

    Restaurant findOneByName(String name);
}

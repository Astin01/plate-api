package com.project.plateapi.restaurant;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllByCategory(String category);

    Optional<Restaurant> findById(Long id);

    Restaurant findByName(String name);
}

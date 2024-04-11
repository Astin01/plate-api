package com.project.plateapi.restaurant.service.dto.response;

import com.project.plateapi.restaurant.domain.Restaurant;
import java.util.List;

public record RestaurantListResponse(List<Restaurant> restaurantList) {
}

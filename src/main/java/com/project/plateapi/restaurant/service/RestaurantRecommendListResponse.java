package com.project.plateapi.restaurant.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRecommendListResponse {
    private List<RestaurantRecommendation> restaurantRecommendList;

    @Getter
    @AllArgsConstructor
    public static class RestaurantRecommendation {
        private String name;
        private String score;
    }
}

package com.project.plateapi.restaurant.support;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Component
public class RestaurantProvider {

    private final String recommendUrl;

    public RestaurantProvider(@Value("${restaurant.recommend.url") String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

}

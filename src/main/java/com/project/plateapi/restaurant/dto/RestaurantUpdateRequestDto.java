package com.project.plateapi.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantUpdateRequestDto {
    private String name;
    private String category;
    private String icon;

    @Builder
    public RestaurantUpdateRequestDto(String name, String category, String icon) {
        this.name = name;
        this.category = category;
        this.icon = icon;
    }
}

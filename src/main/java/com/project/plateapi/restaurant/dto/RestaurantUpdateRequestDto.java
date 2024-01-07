package com.project.plateapi.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantUpdateRequestDto {
    private String name;
    private String category;

    @Builder
    public RestaurantUpdateRequestDto(String name, String category){
        this.name=name;
        this.category=category;
    }
}

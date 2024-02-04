package com.project.plateapi.restaurant.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class RestaurantUpdateRequestDto {
    private String name;
    private String category;
    private String icon;
    private String content;
}

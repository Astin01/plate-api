package com.project.plateapi.restaurant.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class RestaurantUpdateDto {
    private String name;
    private String category;
    private String icon;
    private String content;
}

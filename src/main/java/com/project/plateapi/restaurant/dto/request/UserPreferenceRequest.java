package com.project.plateapi.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceRequest {
    private BigDecimal taste;
    private BigDecimal price;
    private BigDecimal service;
    private BigDecimal fresh;
    private BigDecimal interior;
    private BigDecimal quantity;
    private BigDecimal group;
    private BigDecimal special;
    private BigDecimal clean;
}

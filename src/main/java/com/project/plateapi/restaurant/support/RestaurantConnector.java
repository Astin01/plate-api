package com.project.plateapi.restaurant.support;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plateapi.restaurant.dto.request.UserPreferenceRequest;
import com.project.plateapi.restaurant.service.RestaurantRecommendListResponse;
import com.project.plateapi.user.domain.UserPreference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
@Component
public class RestaurantConnector {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private final RestaurantProvider provider;
    private final ObjectMapper objectMapper;

    public RestaurantRecommendListResponse requestRecommendation(UserPreference userPreference, String category) throws JsonProcessingException {
        UserPreferenceRequest userPreferenceRequest = UserPreferenceRequest.builder()
                .taste(userPreference.getTaste())
                .price(userPreference.getPrice())
                .service(userPreference.getService())
                .fresh(userPreference.getFresh())
                .interior(userPreference.getInterior())
                .quantity(userPreference.getQuantity())
                .group(userPreference.getGroup())
                .special(userPreference.getSpecial())
                .clean(userPreference.getClean())
                .build();
        String json = objectMapper.writeValueAsString(userPreferenceRequest);

        HttpEntity<String> entity = new HttpEntity<>(json, createHeaders());

//        String url = "http://localhost:5000/api/recommend" + "/" + category;
        String url = "http://localhost:5000/recommend/ko";

        ResponseEntity<RestaurantRecommendListResponse> responseEntity = REST_TEMPLATE.exchange(url,
                HttpMethod.POST, entity,
                RestaurantRecommendListResponse.class);

        return responseEntity.getBody();
    }


    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}

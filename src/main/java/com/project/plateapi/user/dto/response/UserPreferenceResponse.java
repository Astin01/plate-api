package com.project.plateapi.user.dto.response;

import com.project.plateapi.user.domain.UserPreference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class UserPreferenceResponse {
    private Long userId;
    private Map<String, Double> preferences;
}

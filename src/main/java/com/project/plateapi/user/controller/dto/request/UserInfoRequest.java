package com.project.plateapi.user.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserInfoRequest(@NotBlank String userId, @NotBlank String userPassword, @Email String email,
                              @NotBlank String name, @NotBlank String nickname) {
}

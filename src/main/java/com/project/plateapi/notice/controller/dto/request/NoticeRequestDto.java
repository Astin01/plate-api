package com.project.plateapi.notice.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NoticeRequestDto(@NotBlank String title, @NotBlank String content, @NotBlank String imageUrl) {
}

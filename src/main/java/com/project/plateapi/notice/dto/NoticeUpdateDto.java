package com.project.plateapi.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoticeUpdateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String imageUrl;
}

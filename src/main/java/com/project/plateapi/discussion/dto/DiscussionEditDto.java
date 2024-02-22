package com.project.plateapi.discussion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DiscussionEditDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}

package com.project.plateapi.comment.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateDto(@NotBlank String comment) {
}

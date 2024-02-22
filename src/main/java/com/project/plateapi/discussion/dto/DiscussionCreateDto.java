package com.project.plateapi.discussion.dto;

import com.project.plateapi.discussion.Discussion;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscussionCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private final boolean closed = false;
    private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    public Discussion toEntity() {
        return Discussion.builder()
                .title(title)
                .content(content)
                .closed(closed)
                .createdDate(createdDate)
                .build();
    }

}

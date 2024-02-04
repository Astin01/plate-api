package com.project.plateapi.discussion.dto;

import com.project.plateapi.discussion.Discussion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class DiscussionRequestDto {
    private String title;
    private final boolean closed = false;
    private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    public Discussion toEntity() {
        Discussion discussion = Discussion.builder()
                .title(title)
                .closed(closed)
                .createdDate(createdDate)
                .build();
        return discussion;
    }

}

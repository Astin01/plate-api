package com.project.plateapi.suggestion.dto;

import com.project.plateapi.discussion.Discussion;
import com.project.plateapi.suggestion.Suggestion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuggestionCreateDto {
    private String title;
    private String content;
    private final boolean closed = false;
    private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    public Suggestion toEntity() {
        return Suggestion.builder()
                .title(title)
                .content(content)
                .closed(closed)
                .createdDate(createdDate)
                .build();
    }

}

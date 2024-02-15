package com.project.plateapi.notice.dto;

import com.project.plateapi.notice.Notice;
import com.project.plateapi.suggestion.Suggestion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeCreateDto {
    private String title;
    private String content;
    private String imageUrl;
    private final boolean closed = false;
    private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .closed(closed)
                .createdDate(createdDate)
                .build();
    }

}

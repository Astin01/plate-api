package com.project.plateapi.notice.controller.dto.request;

import com.project.plateapi.notice.domain.Notice;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
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

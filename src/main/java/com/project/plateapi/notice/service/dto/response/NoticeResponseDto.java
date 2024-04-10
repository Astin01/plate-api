package com.project.plateapi.notice.service.dto.response;

import com.project.plateapi.notice.domain.Notice;


public record NoticeResponseDto(Long id, String title, String content, String imageUrl, boolean closed) {
    public NoticeResponseDto(Notice notice){
        this(notice.getId(), notice.getTitle(), notice.getContent(), notice.getImageUrl(), notice.isClosed());
    }
}

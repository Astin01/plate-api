package com.project.plateapi.notice.dto;

import lombok.Data;

@Data
public class NoticeUpdateDto {
    private String title;
    private String content;
    private String imageUrl;
}

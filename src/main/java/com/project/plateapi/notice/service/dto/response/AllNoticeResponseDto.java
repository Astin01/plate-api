package com.project.plateapi.notice.service.dto.response;

import com.project.plateapi.notice.domain.Notice;
import java.util.List;

public record AllNoticeResponseDto(List<Notice> notices) {
}

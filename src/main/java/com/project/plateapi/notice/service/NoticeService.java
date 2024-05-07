package com.project.plateapi.notice.service;

import com.project.plateapi.notice.controller.dto.request.NoticeRequestDto;
import com.project.plateapi.notice.domain.Notice;
import com.project.plateapi.notice.domain.NoticeRepository;
import com.project.plateapi.notice.service.dto.response.NoticeListResponse;
import com.project.plateapi.notice.service.dto.response.NoticeResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeResponse findNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(IllegalArgumentException::new);

        return new NoticeResponse(notice);
    }

    public NoticeListResponse findAllNotice() {
        return new NoticeListResponse(noticeRepository.findAll());
    }

    @Transactional
    public Long createNotice(NoticeRequestDto request) {
        Notice notice = Notice.builder()
                .title(request.title())
                .content(request.content())
                .imageUrl(request.imageUrl())
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .closed(false)
                .build();

        noticeRepository.save(notice);

        return notice.getId();
    }

    @Transactional
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    @Transactional
    public void editNotice(Long id, NoticeRequestDto request) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        notice.update(request);
    }
}

package com.project.plateapi.notice.service;

import com.project.plateapi.notice.controller.dto.request.NoticeCreateDto;
import com.project.plateapi.notice.controller.dto.request.NoticeUpdateDto;
import com.project.plateapi.notice.domain.Notice;
import com.project.plateapi.notice.domain.NoticeRepository;
import com.project.plateapi.notice.service.dto.response.NoticeListResponse;
import com.project.plateapi.notice.service.dto.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public ResponseEntity<NoticeResponse> findNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(IllegalArgumentException::new);

        NoticeResponse noticeResponse = new NoticeResponse(notice);

        return ResponseEntity.ok()
                .body(noticeResponse);
    }

    public ResponseEntity<NoticeListResponse> findAllNotice() {
        NoticeListResponse allNoticeResponseDto = new NoticeListResponse(noticeRepository.findAll());

        return ResponseEntity.ok()
                .body(allNoticeResponseDto);
    }

    @Transactional
    public ResponseEntity<Long> createNotice(NoticeCreateDto createDto) {
        Notice notice = createDto.toEntity();

        noticeRepository.save(notice);

        return ResponseEntity.ok()
                .body(notice.getId());
    }

    @Transactional
    public ResponseEntity<Void> deleteNotice(Long id) {
        noticeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Void> editNotice(Long id, NoticeUpdateDto updateDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        notice.setTitle(updateDto.getTitle());
        notice.setContent(updateDto.getContent());
        notice.setImageUrl(updateDto.getImageUrl());

        notice.update(notice);

        return ResponseEntity.ok().build();
    }
}

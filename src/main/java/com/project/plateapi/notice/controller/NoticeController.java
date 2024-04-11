package com.project.plateapi.notice.controller;

import com.project.plateapi.notice.service.NoticeService;
import com.project.plateapi.notice.controller.dto.request.NoticeCreateDto;
import com.project.plateapi.notice.controller.dto.request.NoticeUpdateDto;
import com.project.plateapi.notice.service.dto.response.NoticeListResponse;
import com.project.plateapi.notice.service.dto.response.NoticeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping()
    public ResponseEntity<NoticeListResponse> findAllNotice() {
        return noticeService.findAllNotice();
    }

    @PostMapping()
    public ResponseEntity<Long> createNotice(@Valid @RequestBody NoticeCreateDto createDto) {
        return noticeService.createNotice(createDto);
    }

    @GetMapping("/{notice_id}")
    public ResponseEntity<NoticeResponse> findNotice(@PathVariable Long notice_id) {
        return noticeService.findNotice(notice_id);
    }

    @PutMapping("/{notice_id}")
    public ResponseEntity<Void> editNotice(@PathVariable Long notice_id, @RequestBody NoticeUpdateDto updateDto) {
        return noticeService.editNotice(notice_id, updateDto);
    }

    @DeleteMapping("/{notice_id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long notice_id) {
        return noticeService.deleteNotice(notice_id);
    }
}
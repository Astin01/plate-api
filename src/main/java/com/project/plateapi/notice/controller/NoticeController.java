package com.project.plateapi.notice.controller;

import com.project.plateapi.notice.controller.dto.request.NoticeRequestDto;
import com.project.plateapi.notice.service.NoticeService;
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
        NoticeListResponse response = noticeService.findAllNotice();

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Void> createNotice(@Valid @RequestBody NoticeRequestDto request) {
        noticeService.createNotice(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{notice_id}")
    public ResponseEntity<NoticeResponse> findNotice(@PathVariable Long notice_id) {
        NoticeResponse response = noticeService.findNotice(notice_id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{notice_id}")
    public ResponseEntity<Void> editNotice(@PathVariable Long notice_id, @RequestBody NoticeRequestDto request) {
        noticeService.editNotice(notice_id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notice_id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long notice_id) {
        noticeService.deleteNotice(notice_id);
        return ResponseEntity.noContent().build();
    }
}
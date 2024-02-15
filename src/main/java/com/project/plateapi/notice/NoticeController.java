package com.project.plateapi.notice;

import com.project.plateapi.notice.dto.NoticeCreateDto;
import com.project.plateapi.notice.dto.NoticeUpdateDto;
import com.project.plateapi.suggestion.Suggestion;
import com.project.plateapi.suggestion.dto.SuggestionCreateDto;
import com.project.plateapi.suggestion.dto.SuggestionUpdateDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/api/notice")
    public ResponseEntity<?> findAllNotice() {
        return noticeService.findAllNotice();
    }

    @PostMapping("/api/notice")
    public ResponseEntity<Long> createNotice(@Valid @RequestBody NoticeCreateDto createDto) {
        return noticeService.createNotice(createDto);
    }

    @GetMapping("/api/notice/{notice_id}")
    public ResponseEntity<?> findNotice(@PathVariable Long notice_id) {
        return noticeService.findNotice(notice_id);
    }

    @PutMapping("/api/notice/{notice_id}")
    public ResponseEntity<?> editNotice(@PathVariable Long notice_id, @RequestBody NoticeUpdateDto updateDto) {
        return noticeService.editNotice(notice_id, updateDto);
    }

    @DeleteMapping("/api/suggestion/{notice_id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long notice_id) {
        return noticeService.deleteNotice(notice_id);
    }
}
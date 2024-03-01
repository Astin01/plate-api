package com.project.plateapi.notice;

import com.project.plateapi.notice.dto.NoticeCreateDto;
import com.project.plateapi.notice.dto.NoticeUpdateDto;
import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.restaurant.RestaurantRepository;
import com.project.plateapi.suggestion.Suggestion;
import com.project.plateapi.suggestion.dto.SuggestionCreateDto;
import com.project.plateapi.suggestion.dto.SuggestionUpdateDto;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    public ResponseEntity<?> findNotice(Long noticeId) {
         Notice notice = noticeRepository.findById(noticeId)
                 .orElseThrow(IllegalArgumentException::new);

         return new ResponseEntity<>(notice,HttpStatus.OK);
    }

    public ResponseEntity<?> findAllNotice() {
        List<Notice> notices = noticeRepository.findAll();

        return new ResponseEntity<>(notices,HttpStatus.OK);
    }

    public ResponseEntity<Long> createNotice(NoticeCreateDto createDto) {
        Notice notice = createDto.toEntity();

        noticeRepository.save(notice);

        return new ResponseEntity<>(notice.getId(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteNotice(Long id) {
        noticeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> editNotice(Long id, NoticeUpdateDto updateDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        notice.setTitle(updateDto.getTitle());
        notice.setContent(updateDto.getContent());
        notice.setImageUrl(updateDto.getImageUrl());

        notice.update(notice);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

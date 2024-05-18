package com.project.plateapi.notice.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.notice.controller.dto.request.NoticeRequestDto;
import com.project.plateapi.notice.service.dto.response.NoticeListResponse;
import com.project.plateapi.notice.service.dto.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@Sql("classpath:init.sql")
@RequiredArgsConstructor
@SpringBootTest
class NoticeServiceTest {
    private final NoticeService noticeService;

    @BeforeEach
    void setUp() {
        saveNotice("공지1", "내용1", "url1");
        saveNotice("공지2", "내용2", "url2");
    }

    @DisplayName("공지가 모두 조회되는지 테스트한다")
    @Test
    void findAllNotice() {
        NoticeListResponse response = noticeService.findAllNotice();

        assertThat(response.notices().size()).isEqualTo(2);
    }

    @DisplayName("공지가 조회되는지 테스트한다")
    @Test
    void findNotice() {
        NoticeResponse response = noticeService.findNotice(1L);

        assertThat(response.title()).isEqualTo("공지1");
        assertThat(response.content()).isEqualTo("내용1");
        assertThat(response.imageUrl()).isEqualTo("url1");
    }

    @DisplayName("공지가 생성되는지 테스트한다")
    @Test
    void createNotice() {
        NoticeRequestDto request = new NoticeRequestDto("공지3", "내용3", "url3");

        Long id = noticeService.createNotice(request);

        assertThat(id).isEqualTo(3);
    }

    @DisplayName("공지가 삭제되었는지 테스트한다")
    @Test
    void deleteNotice() {
        noticeService.deleteNotice(1L);

        assertThatThrownBy(() -> {
            noticeService.findNotice(1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("공지가 수정되는지 테스트한다")
    @Test
    void editNotice() {
        NoticeRequestDto changeRequest = new NoticeRequestDto("수정제목", "수정내용", "수정url");

        noticeService.editNotice(1L, changeRequest);
        NoticeResponse response = noticeService.findNotice(1L);

        assertThat(response.title()).isEqualTo("수정제목");
        assertThat(response.content()).isEqualTo("수정내용");
        assertThat(response.imageUrl()).isEqualTo("수정url");
    }

    private void saveNotice(String title, String content, String imageUrl) {
        NoticeRequestDto request = new NoticeRequestDto(title, content, imageUrl);
        noticeService.createNotice(request);
    }
}

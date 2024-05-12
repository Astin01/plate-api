package com.project.plateapi.notice.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.notice.domain.NoticeRepository;
import com.project.plateapi.notice.service.dto.response.NoticeListResponse;
import com.project.plateapi.notice.service.dto.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class NoticeServiceTest {
    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    @DisplayName("공지가 모두 조회되는지 테스트한다")
    @Test
    void findAllNotice() {
        NoticeListResponse response = noticeService.findAllNotice();

        assertThat(response.notices().size()).isEqualTo(2);
    }

    @DisplayName("공지가 조회되는지 테스트한다")
    @Test
    void findNotice() {
        Long id = 1L;
        NoticeResponse response = noticeService.findNotice(id);
        assertThat(response.id()).isEqualTo("밥은");
        assertThat(response.title()).isEqualTo("stockpot");
        assertThat(response.content()).isEqualTo("ko");
        assertThat(response.closed()).isEqualTo("착한 가격에 많은 양, 거리도 학교 정문에서 가깝다");
    }
}

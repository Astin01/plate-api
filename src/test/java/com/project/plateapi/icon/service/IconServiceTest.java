package com.project.plateapi.icon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.project.plateapi.icon.controller.dto.request.IconRequestDto;
import com.project.plateapi.icon.service.dto.response.IconListResponseDto;
import com.project.plateapi.icon.service.dto.response.IconResponseDto;
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
class IconServiceTest {
    private final IconService iconService;

    @BeforeEach
    void setUp() {
        saveIcon("아이콘1", "이름1", "url1");
        saveIcon("아이콘2", "이름2", "url2");
    }

    @DisplayName("아이콘이 모두 조회되는지 테스트한다")
    @Test
    void findAllIcon() {
        IconListResponseDto response = iconService.findAllIcon();

        assertThat(response.iconList().size()).isEqualTo(2);
    }


    @DisplayName("아이콘이 생성되는지 테스트한다")
    @Test
    void createIcon() {
        IconRequestDto request = new IconRequestDto("아이콘3", "이름3", "url3");

        Long id = iconService.createIcon(request);

        assertThat(id).isEqualTo(3);
    }

    @DisplayName("아이콘이 삭제되었는지 테스트한다")
    @Test
    void deleteIcon() {
        iconService.deleteIcon(1L);

        assertThatThrownBy(() -> {
            iconService.findIcon(1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("아이콘이 수정되는지 테스트한다")
    @Test
    void editIcon() {
        IconRequestDto changeRequest = new IconRequestDto("수정아이콘", "수정이름", "수정url");

        iconService.editIcon(1L, changeRequest);
        IconResponseDto response = iconService.findIcon(1L);

        assertThat(response.icon().getIcon()).isEqualTo("수정아이콘");
        assertThat(response.icon().getName()).isEqualTo("수정이름");
        assertThat(response.icon().getLink()).isEqualTo("수정url");
    }

    private void saveIcon(String icon, String name, String link) {
        IconRequestDto request = new IconRequestDto(icon, name, link);
        iconService.createIcon(request);
    }
}

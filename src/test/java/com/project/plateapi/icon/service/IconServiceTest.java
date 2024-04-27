package com.project.plateapi.icon.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.plateapi.icon.service.dto.response.IconResponseDto;
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
class IconServiceTest {
    private final IconService iconService;

    @DisplayName("아이콘 목록을 조회한다")
    @Test
    void findIcon(){
        IconResponseDto response = iconService.findAllIcon();

        assertThat(response.iconList()).hasSize(6);
    }

}

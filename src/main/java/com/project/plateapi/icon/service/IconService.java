package com.project.plateapi.icon.service;

import com.project.plateapi.icon.controller.dto.request.IconRequestDto;
import com.project.plateapi.icon.domain.Icon;
import com.project.plateapi.icon.domain.IconRepository;
import com.project.plateapi.icon.service.dto.response.IconListResponseDto;
import com.project.plateapi.icon.service.dto.response.IconResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IconService {
    private final IconRepository iconRepository;

    public IconResponseDto findIcon(Long iconId) {
        Icon icon = iconRepository.findById(iconId)
                .orElseThrow(IllegalArgumentException::new);

        return new IconResponseDto(icon);
    }

    public IconListResponseDto findAllIcon() {
        List<Icon> icons = iconRepository.findAll();

        return new IconListResponseDto(icons);
    }

    public Long createIcon(IconRequestDto request) {
        Icon icon = Icon.builder()
                .icon(request.icon())
                .name(request.name())
                .link(request.link())
                .build();

        Icon savedIcon = iconRepository.save(icon);

        return savedIcon.getId();

    }

    @Transactional
    public void editIcon(Long iconId, IconRequestDto request) {
        Icon icon = iconRepository.findById(iconId)
                .orElseThrow(IllegalArgumentException::new);

        icon.update(request.icon(), request.name(), request.link());
    }

    @Transactional
    public void deleteIcon(Long iconId) {
        iconRepository.deleteById(iconId);
    }


}

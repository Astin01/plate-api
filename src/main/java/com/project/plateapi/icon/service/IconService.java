package com.project.plateapi.icon.service;

import com.project.plateapi.icon.domain.Icon;
import com.project.plateapi.icon.domain.IconRepository;
import com.project.plateapi.icon.service.dto.response.IconResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IconService {
    private final IconRepository iconRepository;

    public ResponseEntity<IconResponseDto> findAllIcon() {
        List<Icon> icons = iconRepository.findAll();
        IconResponseDto responseDto = new IconResponseDto(icons);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}

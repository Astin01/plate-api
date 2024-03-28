package com.project.plateapi.icon.service;

import com.project.plateapi.icon.domain.Icon;
import com.project.plateapi.icon.domain.IconRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IconService {
    private final IconRepository iconRepository;

    public ResponseEntity<?> findAllIcon() {
        List<Icon> icons = iconRepository.findAll();

        return new ResponseEntity<>(icons, HttpStatus.OK);
    }

}

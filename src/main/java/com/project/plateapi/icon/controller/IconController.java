package com.project.plateapi.icon.controller;

import com.project.plateapi.icon.service.IconService;
import com.project.plateapi.icon.service.dto.response.IconResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/icon")
public class IconController {
    private final IconService service;

    @GetMapping()
    public ResponseEntity<IconResponseDto> findAllIcon() {
        IconResponseDto response = service.findAllIcon();

        return ResponseEntity.ok(response);
    }

}
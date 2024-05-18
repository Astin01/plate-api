package com.project.plateapi.icon.controller;

import com.project.plateapi.icon.controller.dto.request.IconRequestDto;
import com.project.plateapi.icon.service.IconService;
import com.project.plateapi.icon.service.dto.response.IconListResponseDto;
import com.project.plateapi.icon.service.dto.response.IconResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/icon")
public class IconController {
    private final IconService iconService;

    @GetMapping()
    public ResponseEntity<IconListResponseDto> findAllIcon() {
        IconListResponseDto response = iconService.findAllIcon();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{icon_id}")
    public ResponseEntity<IconResponseDto> findIcon(Long iconId) {
        IconResponseDto response = iconService.findIcon(iconId);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Void> createIcon(@Valid @RequestBody IconRequestDto request) {
        iconService.createIcon(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{icon_id}")
    public ResponseEntity<Void> editIcon(@PathVariable Long icon_id, @RequestBody IconRequestDto request) {
        iconService.editIcon(icon_id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{icon_id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long icon_id) {
        iconService.deleteIcon(icon_id);

        return ResponseEntity.noContent().build();
    }
}
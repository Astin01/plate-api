package com.project.plateapi.icon.controller;

import com.project.plateapi.icon.service.IconService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IconController {
    private final IconService service;

    public IconController(IconService service) {
        this.service = service;
    }

    @GetMapping("/api/icon")
    public ResponseEntity<?> findAllIcon() {
        return service.findAllIcon();
    }

}
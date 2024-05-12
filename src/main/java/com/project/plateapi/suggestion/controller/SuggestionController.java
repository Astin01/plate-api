package com.project.plateapi.suggestion.controller;

import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.suggestion.controller.dto.request.SuggestionRequest;
import com.project.plateapi.suggestion.service.SuggestionService;
import com.project.plateapi.suggestion.service.dto.response.SuggestionListResponse;
import com.project.plateapi.suggestion.service.dto.response.SuggestionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/api/suggestion")
public class SuggestionController {
    private final SuggestionService suggestionService;

    @GetMapping()
    public ResponseEntity<SuggestionListResponse> findAllSuggestion() {
        SuggestionListResponse response = suggestionService.findAllSuggestion();

        return ResponseEntity.ok(response);
    }

    @Secured({"USER", "ADMIN"})
    @PostMapping("/{restaurant_id}")
    public ResponseEntity<Void> createSuggestion(@AuthenticationPrincipal CustomUser customUser,
                                                 @PathVariable Long restaurant_id,
                                                 @Valid @RequestBody SuggestionRequest requestDto) {
        suggestionService.createSuggestion(customUser, restaurant_id, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{suggestion_id}")
    public ResponseEntity<SuggestionResponse> findSuggestion(@PathVariable Long suggestion_id) {
        SuggestionResponse response = suggestionService.findSuggestion(suggestion_id);

        return ResponseEntity.ok(response);
    }

    @Secured({"USER", "ADMIN"})
    @PutMapping("/{suggestion_id}")
    public ResponseEntity<Void> editSuggestion(@AuthenticationPrincipal CustomUser customUser,
                                               @PathVariable Long suggestion_id,
                                               @RequestBody SuggestionRequest requestDto) {
        suggestionService.editSuggestion(customUser, suggestion_id, requestDto);

        return ResponseEntity.ok().build();
    }

    @Secured({"USER", "ADMIN"})
    @DeleteMapping("/{suggestion_id}")
    public ResponseEntity<Void> deleteSuggestion(@AuthenticationPrincipal CustomUser customUser,
                                                 @PathVariable Long suggestion_id) {
        suggestionService.deleteSuggestion(customUser, suggestion_id);

        return ResponseEntity.noContent().build();
    }

    @Secured("ADMIN")
    @PutMapping("/toRestaurant/{suggestion_id}")
    public ResponseEntity<Void> putSuggestionToRestaurant(@PathVariable Long suggestion_id) {
        suggestionService.putSuggestionToRestaurant(suggestion_id);

        return ResponseEntity.ok().build();
    }
}
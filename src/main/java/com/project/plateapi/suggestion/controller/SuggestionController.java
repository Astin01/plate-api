package com.project.plateapi.suggestion.controller;

import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.suggestion.controller.dto.request.SuggestionRequest;
import com.project.plateapi.suggestion.service.SuggestionService;
import com.project.plateapi.suggestion.service.dto.response.SuggestionListResponse;
import com.project.plateapi.suggestion.service.dto.response.SuggestionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return suggestionService.findAllSuggestion();
    }

    @Secured({"USER","ADMIN"})
    @PostMapping("/{restaurant_id}")
    public ResponseEntity<Long> createSuggestion(@AuthenticationPrincipal CustomUser customUser,@PathVariable Long restaurant_id , @Valid @RequestBody SuggestionRequest requestDto) {
        return suggestionService.createSuggestion(customUser,restaurant_id, requestDto);
    }

    @GetMapping("/{suggestion_id}")
    public ResponseEntity<SuggestionResponse> findSuggestion(@PathVariable Long suggestion_id) {
        return suggestionService.findSuggestion(suggestion_id);
    }

    @Secured({"USER","ADMIN"})
    @PutMapping("/{suggestion_id}")
    public ResponseEntity<Void> editSuggestion(@AuthenticationPrincipal CustomUser customUser,@PathVariable Long suggestion_id, @RequestBody SuggestionRequest requestDto) {
        return suggestionService.editSuggestion(customUser,suggestion_id, requestDto);
    }

    @Secured({"USER","ADMIN"})
    @DeleteMapping("/{suggestion_id}")
    public ResponseEntity<Void> deleteSuggestion(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long suggestion_id) {
        return suggestionService.deleteSuggestion(customUser,suggestion_id);
    }

    @Secured("ADMIN")
    @PutMapping("/toRestaurant/{suggestion_id}")
    public ResponseEntity<Long> putSuggestionToRestaurant(@PathVariable Long suggestion_id) {
        return suggestionService.putSuggestionToRestaurant(suggestion_id);
    }
}
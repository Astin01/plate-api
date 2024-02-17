package com.project.plateapi.suggestion;

import com.project.plateapi.suggestion.dto.SuggestionCreateDto;
import com.project.plateapi.suggestion.dto.SuggestionUpdateDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestionController {
    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/api/suggestion")
    public ResponseEntity<?> findAllSuggestion() {
        return suggestionService.findAllSuggestion();
    }

    @PostMapping("/api/suggestion/{restaurant_id}")
    public ResponseEntity<Long> createSuggestion(@PathVariable Long restaurant_id ,@Valid @RequestBody SuggestionCreateDto createDto) {
        return suggestionService.createSuggestion(restaurant_id, createDto);
    }

    @GetMapping("/api/suggestion/{suggestion_id}")
    public ResponseEntity<?> findSuggestion(@PathVariable Long suggestion_id) {
        return suggestionService.findSuggestion(suggestion_id);
    }

    @PutMapping("/api/suggestion/{suggestion_id}")
    public ResponseEntity<?> editSuggestion(@PathVariable Long suggestion_id, @RequestBody SuggestionUpdateDto suggestionUpdateDto) {
        return suggestionService.editSuggestion(suggestion_id, suggestionUpdateDto);
    }

    @DeleteMapping("/api/suggestion/{suggestion_id}")
    public ResponseEntity<?> deleteSuggestion(@PathVariable Long suggestion_id) {
        return suggestionService.deleteSuggestion(suggestion_id);
    }


    @PutMapping("/api/suggestion/toRestaurant/{suggestion_id}")
    public ResponseEntity<Long> putSuggestionToRestaurant(@PathVariable Long suggestion_id) {
        return suggestionService.putSuggestionToRestaurant(suggestion_id);
    }
}
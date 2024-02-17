package com.project.plateapi.suggestion;

import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.restaurant.RestaurantRepository;
import com.project.plateapi.suggestion.dto.SuggestionCreateDto;
import com.project.plateapi.suggestion.dto.SuggestionUpdateDto;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final RestaurantRepository restaurantRepository;

    public ResponseEntity<?> findSuggestion(Long suggestionId) {
         Suggestion suggestion = suggestionRepository.findById(suggestionId)
                 .orElseThrow(IllegalArgumentException::new);
         return new ResponseEntity<>(suggestion,HttpStatus.OK);
    }

    public ResponseEntity<?> findAllSuggestion() {
        List<Suggestion> suggestionList = suggestionRepository.findAll();

        return new ResponseEntity<>(suggestionList,HttpStatus.OK);
    }

    public ResponseEntity<Long> createSuggestion(Long id ,SuggestionCreateDto createDto) {
        Suggestion suggestion = createDto.toEntity();
        Restaurant restaurant = restaurantRepository.findById(id)
                        .orElseThrow(IllegalArgumentException::new);
        suggestion.setRestaurant(restaurant);
        suggestionRepository.save(suggestion);
        return new ResponseEntity<>(suggestion.getId(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteSuggestion(Long id) {
        suggestionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> editSuggestion(Long id, SuggestionUpdateDto suggestionUpdateDto) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        suggestion.setTitle(suggestionUpdateDto.getTitle());
        suggestion.setContent(suggestionUpdateDto.getContent());

        suggestion.update(suggestion);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Long> putSuggestionToRestaurant(Long id) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Restaurant restaurant = suggestion.getRestaurant();
        if (restaurant == null) {
            throw new IllegalArgumentException();
        }

        restaurant.setContent(suggestion.getContent());

        restaurant.update(restaurant);

        return new ResponseEntity<>(restaurant.getId(),HttpStatus.OK);
    }
}

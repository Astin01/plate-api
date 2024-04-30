package com.project.plateapi.suggestion.service;

import com.project.plateapi.restaurant.domain.Restaurant;
import com.project.plateapi.restaurant.domain.RestaurantRepository;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.suggestion.controller.dto.request.SuggestionRequest;
import com.project.plateapi.suggestion.domain.Suggestion;
import com.project.plateapi.suggestion.domain.SuggestionRepository;
import com.project.plateapi.suggestion.service.dto.response.SuggestionListResponse;
import com.project.plateapi.suggestion.service.dto.response.SuggestionResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final RestaurantRepository restaurantRepository;

    public ResponseEntity<SuggestionResponse> findSuggestion(Long suggestionId) {
         Suggestion suggestion = suggestionRepository.findById(suggestionId)
                 .orElseThrow(IllegalArgumentException::new);

         if(suggestion.isClosed()){
             throw new IllegalStateException("닫힌 제안입니다");
         }

        SuggestionResponse suggestionResponse = new SuggestionResponse(suggestion);

        return ResponseEntity.ok()
                .body(suggestionResponse);
    }

    public ResponseEntity<SuggestionListResponse> findAllSuggestion() {
        SuggestionListResponse suggestionListResponse = new SuggestionListResponse(suggestionRepository.findByClosed(Boolean.FALSE));

        return ResponseEntity.ok()
                .body(suggestionListResponse);
    }

    @Transactional
    public ResponseEntity<Long> createSuggestion(CustomUser customUser, Long id , SuggestionRequest requestDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        Suggestion suggestion = Suggestion.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .closed(false)
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .user(customUser.getUser())
                .restaurant(restaurant)
                .build();

        suggestionRepository.save(suggestion);

        return ResponseEntity.ok()
                .body(suggestion.getId());
    }
    @Transactional
    public ResponseEntity<Void> editSuggestion(CustomUser customUser, Long suggestion_id, SuggestionRequest requestDto) {
        Suggestion suggestion = suggestionRepository.findById(suggestion_id)
                .orElseThrow(IllegalArgumentException::new);

        if(notUser(customUser, suggestion)){
            return ResponseEntity.badRequest().build();
        }

        suggestion.editSuggestion(requestDto);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Void> deleteSuggestion(CustomUser customUser,Long suggestion_id) {
        Suggestion suggestion = suggestionRepository.findById(suggestion_id)
                .orElseThrow(IllegalArgumentException::new);

        if(notUser(customUser,suggestion) && notAdmin(customUser)){
            return ResponseEntity.badRequest().build();
        }

        suggestion.closeSuggestion();

        return ResponseEntity.noContent().build();
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

        return ResponseEntity.ok()
                .body(restaurant.getId());
    }

    private boolean notUser(CustomUser customUser,Suggestion suggestion){
        String userId = customUser.getUsername();
        String suggestionUserId = suggestion.getUser().getUserId();

        return !userId.equals(suggestionUserId);
    }

    private boolean notAdmin(CustomUser customUser){
        Collection<? extends GrantedAuthority> auth = customUser.getAuthorities();
        for(GrantedAuthority auths : auth){
            return !auths.getAuthority().equals("ADMIN");
        }
        return true;
    }

}

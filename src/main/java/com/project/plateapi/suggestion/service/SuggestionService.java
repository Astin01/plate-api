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

    public SuggestionResponse findSuggestion(Long suggestionId) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId)
                .orElseThrow(IllegalArgumentException::new);

        if (suggestion.isClosed()) {
            throw new IllegalStateException("닫힌 제안입니다");
        }

        return new SuggestionResponse(suggestion);
    }

    public SuggestionListResponse findAllSuggestion() {

        return new SuggestionListResponse(
                suggestionRepository.findByClosed(Boolean.FALSE));
    }

    @Transactional
    public Long createSuggestion(CustomUser customUser, Long id, SuggestionRequest requestDto) {
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

        return id;
    }

    @Transactional
    public void editSuggestion(CustomUser customUser, Long suggestion_id,
                                               SuggestionRequest requestDto) {
        Suggestion suggestion = suggestionRepository.findById(suggestion_id)
                .orElseThrow(IllegalArgumentException::new);

        if (notUser(customUser, suggestion)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        suggestion.editSuggestion(requestDto);
    }

    @Transactional
    public void deleteSuggestion(CustomUser customUser, Long suggestion_id) {
        Suggestion suggestion = suggestionRepository.findById(suggestion_id)
                .orElseThrow(IllegalArgumentException::new);

        if (notUser(customUser, suggestion) && notAdmin(customUser)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        suggestion.closeSuggestion();
    }

    @Transactional
    public Long putSuggestionToRestaurant(Long id) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Restaurant restaurant = suggestion.getRestaurant();
        if (restaurant == null) {
            throw new IllegalArgumentException();
        }

        restaurant.setContent(suggestion.getContent());

        restaurant.update(restaurant);

        return id;
    }

    private boolean notUser(CustomUser customUser, Suggestion suggestion) {
        String userId = customUser.getUsername();
        String suggestionUserId = suggestion.getUser().getUserId();

        return !userId.equals(suggestionUserId);
    }

    private boolean notAdmin(CustomUser customUser) {
        Collection<? extends GrantedAuthority> auth = customUser.getAuthorities();
        for (GrantedAuthority auths : auth) {
            return !auths.getAuthority().equals("ADMIN");
        }
        return true;
    }

}

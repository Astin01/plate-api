package com.project.plateapi.suggestion;

import com.project.plateapi.restaurant.Restaurant;
import com.project.plateapi.restaurant.RestaurantRepository;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.suggestion.dto.SuggestionCreateDto;
import com.project.plateapi.suggestion.dto.SuggestionListResponseDto;
import com.project.plateapi.suggestion.dto.SuggestionResponseDto;
import com.project.plateapi.suggestion.dto.SuggestionUpdateDto;
import com.project.plateapi.user.Users;
import com.project.plateapi.user_role.UserRole;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final RestaurantRepository restaurantRepository;

    public ResponseEntity<?> findSuggestion(Long suggestionId) {
         Suggestion suggestion = suggestionRepository.findById(suggestionId)
                 .orElseThrow(IllegalArgumentException::new);

        SuggestionResponseDto responseDto = new SuggestionResponseDto(suggestion);

         return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    public ResponseEntity<?> findAllSuggestion() {
        List<Suggestion> suggestionList = suggestionRepository.findAll();

        SuggestionListResponseDto responseDto = new SuggestionListResponseDto(suggestionList);

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Long> createSuggestion(CustomUser customUser, Long id , SuggestionCreateDto createDto) {
        Suggestion suggestion = createDto.toEntity();
        Users user = customUser.getUser();
        Restaurant restaurant = restaurantRepository.findById(id)
                        .orElseThrow(IllegalArgumentException::new);

        suggestion.setRestaurant(restaurant);
        suggestion.setUser(user);
        suggestionRepository.save(suggestion);

        return new ResponseEntity<>(suggestion.getId(), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<?> editSuggestion(CustomUser customUser, Long suggestion_id, SuggestionUpdateDto suggestionUpdateDto) {
        Suggestion suggestion = suggestionRepository.findById(suggestion_id)
                .orElseThrow(IllegalArgumentException::new);

        if(notUser(customUser, suggestion)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        suggestion.setTitle(suggestionUpdateDto.getTitle());
        suggestion.setContent(suggestionUpdateDto.getContent());
        suggestion.update(suggestion);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteSuggestion(CustomUser customUser,Long suggestion_id) {
        Suggestion suggestion = suggestionRepository.findById(suggestion_id)
                .orElseThrow(IllegalArgumentException::new);

        if(notAdmin(customUser)){
            suggestionRepository.deleteById(suggestion_id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        if(notUser(customUser, suggestion)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       suggestionRepository.deleteById(suggestion_id);

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

    private boolean notUser(CustomUser customUser,Suggestion suggestion){
        String userId = customUser.getUsername();
        String suggestionUserId = suggestion.getUser().getUserId();

        return !userId.equals(suggestionUserId);
    }

    private boolean notAdmin(CustomUser customUser){
        Collection<? extends GrantedAuthority> auth = customUser.getAuthorities();
        for(GrantedAuthority auths : auth){
            return auths.getAuthority().equals("ADMIN");
        }
        return false;
    }

}

package com.project.plateapi.suggestion.service.dto.response;

import com.project.plateapi.suggestion.domain.Suggestion;

public record SuggestionResponse(Long id, String title, String content, boolean closed, String userId) {
    public SuggestionResponse(Suggestion suggestion){
        this(suggestion.getId(), suggestion.getTitle(), suggestion.getContent(), suggestion.isClosed(), suggestion.getUser().getUserId());
    }
}

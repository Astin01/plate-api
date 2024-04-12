package com.project.plateapi.suggestion.service.dto.response;

import com.project.plateapi.suggestion.domain.Suggestion;
import java.util.List;
import java.util.stream.Collectors;

public record SuggestionListResponse(List<Suggestion> suggestionList) {
    public SuggestionListResponse(List<Suggestion> suggestionList){
        this.suggestionList= suggestionList.stream().map(suggestion -> {
            Suggestion suggestions = new Suggestion();
            suggestions.setId(suggestion.getId());
            suggestions.setTitle(suggestion.getTitle());
            suggestions.setClosed(suggestion.isClosed());
            return suggestions;
        }).collect(Collectors.toList());
    }
}

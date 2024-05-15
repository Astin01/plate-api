package com.project.plateapi.suggestion.service.dto.response;

import com.project.plateapi.suggestion.domain.Suggestion;
import java.util.List;
import java.util.stream.Collectors;

public record SuggestionListResponse(List<Suggestion> suggestionList) {
    public SuggestionListResponse(List<Suggestion> suggestionList) {
        this.suggestionList = suggestionList.stream().map(suggestion -> Suggestion.builder()
                .id(suggestion.getId())
                .title(suggestion.getTitle())
                .content(suggestion.getContent())
                .closed(suggestion.isClosed())
                .build()).collect(Collectors.toList());
    }
}

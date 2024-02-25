package com.project.plateapi.suggestion.dto;

import com.project.plateapi.suggestion.Suggestion;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class SuggestionResponseDto {
    private Long id;
    private String title;
    private String content;
    private boolean closed;
    private String userId;

    public SuggestionResponseDto(Suggestion suggestion){
        this.id= suggestion.getId();
        this.title= suggestion.getTitle();
        this.content= suggestion.getContent();
        this.closed= suggestion.isClosed();
        this.userId= suggestion.getUser().getUserId();
    }
}

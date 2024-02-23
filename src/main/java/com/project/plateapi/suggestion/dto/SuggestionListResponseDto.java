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
@Builder
public class SuggestionListResponseDto {
    private List<Suggestion> suggestionDto;

    public SuggestionListResponseDto(List<Suggestion> suggestionList){
        this.suggestionDto= suggestionList.stream().map(suggestion -> {
            Suggestion suggestions = new Suggestion();
            suggestions.setId(suggestion.getId());
            suggestions.setTitle(suggestion.getTitle());
            suggestions.setClosed(suggestion.isClosed());
           return suggestions;
        }).collect(Collectors.toList());
    }
    @Getter
    @Setter
    public static class ModifySuggestionDto {
        private Long id;
        private String title;
        private boolean closed;

        }
//
//        @Getter
//        @Setter
//        public class DiscussionCommentDto{
//            private long id;
//            private String comment;
//            private String userNickname;
//        }

//    }

}

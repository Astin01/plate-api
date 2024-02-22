package com.project.plateapi.discussion.dto;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.discussion.Discussion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionListResponseDto {
    private discussionDto discussions;

    public DiscussionListResponseDto(List<Discussion> discussionList){
        this.discussions = new discussionDto(discussionList);
    }

    @Getter
    public static class discussionDto {
        private List<Discussion> discussions;
        public discussionDto(List<Discussion> discussionList) {
            this.discussions = discussionList.stream().map(discussion1 -> {
                Discussion discussion = new Discussion();
                discussion.setId(discussion1.getId());
                discussion.setTitle(discussion1.getTitle());
                discussion.setContent(discussion1.getContent());
                discussion.setClosed(discussion1.isClosed());
                return discussion;
            }).collect(Collectors.toList());
        }
    }


}

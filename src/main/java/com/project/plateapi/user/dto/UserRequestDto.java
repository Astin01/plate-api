package com.project.plateapi.user.dto;

import com.project.plateapi.user.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private Long id;
    private String userId;
    private String userPassword;
    private String name;
    private String nickname;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String deletedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    public User toEntity() {
        User user = User.builder()
                .id(id)
                .userId(userId)
                .userPassword(userPassword)
                .name(name)
                .nickname(nickname)
                .createdDate(createdDate)
                .deletedDate(deletedDate)
                .build();
        return user;
    }
}

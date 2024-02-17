package com.project.plateapi.user.dto;

import com.project.plateapi.user.Users;
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
    private String userId;
    private String userPassword;
    private String email;
    private String name;
    private String nickname;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String deletedDate = null;

    public Users toEntity() {
        return Users.builder()
                .userId(userId)
                .userPassword(userPassword)
                .name(name)
                .nickname(nickname)
                .email(email)
                .createdDate(createdDate)
                .deletedDate(deletedDate)
                .build();
    }
}

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
    private long id;
    private String userId;
    private String userPassword;
    private String name;
    private String nickname;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String deletedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    public Users toEntity() {
        Users user = Users.builder()
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

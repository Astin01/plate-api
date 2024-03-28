package com.project.plateapi.user.dto;

import com.project.plateapi.user.domain.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String userId;

    @NotBlank
    private String userPassword;

    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

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

package com.project.plateapi.user.service.dto.response;

import com.project.plateapi.role.domain.Role;
import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user_role.domain.UserRole;
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
public class UserInfoResponse {
    private Long id;
    private String userId;
    private String name;
    private String nickname;
    private String email;
    private UserRoleDto userRoles;

    public UserInfoResponse(Users user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.userRoles = new UserRoleDto(user.getUserRoles());
    }

    @Getter
    public static class UserRoleDto {
        private final List<Role> userRole;

        public UserRoleDto(List<UserRole> userRoleLists) {
            this.userRole = userRoleLists.stream().map(role -> role.getRole()).collect(Collectors.toList());
        }
    }

}

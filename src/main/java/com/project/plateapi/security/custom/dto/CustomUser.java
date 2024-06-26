package com.project.plateapi.security.custom.dto;

import com.project.plateapi.user.domain.Users;
import com.project.plateapi.user_role.domain.UserRole;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Slf4j
@Transactional
public class CustomUser implements UserDetails {

    private Users user;

    public CustomUser(Users user) {
        this.user = user;
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> userRoles = user.getUserRoles();

        //SimpleGrantedAuthority() - "USER"
        Collection<SimpleGrantedAuthority> roleList = userRoles.stream()
                .map((auth) -> new SimpleGrantedAuthority(auth.getRole().getRole()))
                .collect(Collectors.toList());

        return roleList;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}

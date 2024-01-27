package com.project.plateapi.security.custom;

import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.UserRepository;
import com.project.plateapi.user.Users;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load user by username " + username);

        Users user = userRepository.findByUserId(username);

        if (user == null) {
            log.info("사용자 없음- 일치하는 아이디 없음");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다" + username);
        }

        log.info("user : " + user.toString());

        //Users-> CustomUser
        CustomUser customUser = new CustomUser(user);

        log.info("customUser : ");
        log.info(customUser.toString());

        return customUser;
    }
}

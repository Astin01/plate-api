package com.project.plateapi.config;

import com.project.plateapi.security.custom.CustomUserDetailService;
import com.project.plateapi.security.jwt.filter.JwtAuthenticationFilter;
import com.project.plateapi.security.jwt.filter.JwtRequestFilter;
import com.project.plateapi.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = false,securedEnabled = true)// preAuthorize , @postAuthorize, @Secured 활성화
public class WebSecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 폼 기반 로그인 비활성화
        http.formLogin(login -> login.disable());

        // HTTP 기본 인증 비활성화
        http.httpBasic(basic -> basic.disable());

        // CSRF(Cross-Site Request Forgery) 공격 방어 기능 비활성화
        http.csrf(csrf -> csrf.disable());

        // 세션 관리 정책 설정: STATELESS로 설정하면 서버는 세션을 생성하지 않음
        // 🔐 세션을 사용하여 인증하지 않고,  JWT 를 사용하여 인증하기 때문에, 세션 불필요
        http.sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //필터 설정
        http.addFilterAt(new JwtAuthenticationFilter(authenticationManager,jwtTokenProvider)
                        , UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider)
                        , UsernamePasswordAuthenticationFilter.class);

        //인가 설정
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() //정적 자원 인가
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/users/signIn").permitAll()
//                        .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        );

        //인증 방식 설정
        http.userDetailsService(customUserDetailService);

        // 구성이 완료된 SecurityFilterChain을 반환
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }

}

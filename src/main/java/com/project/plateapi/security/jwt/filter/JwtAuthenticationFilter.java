package com.project.plateapi.security.jwt.filter;

import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.security.jwt.constants.JwtConstants;
import com.project.plateapi.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * client -> filter -> server
 *  username , password 인증 시도 (attemptAuthentication)
 *      인증 실패 : response > status : 401 인증
 * 성공(successfulAuthentication)
 * -> jwt 생성
 * -> response > header > authorization : jwt
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        //필터 url 경로 설정
        setFilterProcessesUrl(JwtConstants.AUTH_LOGIN_URL);
    }


    /**
     * 인증 시도 메서드
     * :/login 으로 경로 요청하면 필터로 걸러서 인증 시도
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info("username : " + username + " password : " + password);

        // 사용자 인증정보 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        log.info("authentication : " + authentication);
        // 사용자 인증 (로그인)
        authentication = authenticationManager.authenticate(authentication);

        log.info("인증여부" + authentication.isAuthenticated());

        if (!authentication.isAuthenticated()) {
            log.info("인증 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
            response.setStatus(401); // UNAUTHORIZED 인증실패
        }

        return authentication;
    }

    /**
     * 인증 성공 메서드
     * - jwt 생성
     * - jwt 를 응답 헤더에 설정
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        log.info("인증 성공");

        CustomUser user = (CustomUser) authentication.getPrincipal();
        long userNo = user.getUser().getId();
        int convUserNo = Long.valueOf(userNo).intValue();
        String userId = user.getUser().getUserId();

        List<String> roles = user.getUser().getUserRoles().stream()
                .map((role) -> role.getRole().getRole())
                .collect(Collectors.toList());

        // Jwt
        String jwt = jwtTokenProvider.createToken(convUserNo, userId, roles);

        // { Authorization : Bearer + {jwt} }
        response.addHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX + jwt);
        response.setStatus(200);
    }
}

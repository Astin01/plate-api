package com.project.plateapi.security.jwt.provider;

import com.project.plateapi.constants.SecurityConstants;
import com.project.plateapi.props.JwtProps;
import com.project.plateapi.role.Role;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.security.jwt.constants.JwtConstants;
import com.project.plateapi.user.UserRepository;
import com.project.plateapi.user.Users;
import com.project.plateapi.user_role.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * jwt 토큰 생성 토근 해석 토큰 유효성 검사
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    private JwtProps jwtProps;
    @Autowired
    private UserRepository userRepository;

    public String createToken(int userNo, String userId, List<String> roles) {
        // JWT 토큰 생성
        String jwt = Jwts.builder()
                .signWith(getShaKey(), Jwts.SIG.HS512)      // 서명에 사용할 키와 알고리즘 설정
                .header()                                                      // update (version : after 1.0)
                .add("typ", SecurityConstants.TOKEN_TYPE)              // 헤더 설정
                .and()
                .expiration(new Date(System.currentTimeMillis() + 864000000))  // 토큰 만료 시간 설정 (10일)
                .claim("uno", userNo)                                    // 클레임 설정: 사용자 고유번호
                .claim("uid", userId)                                   // 클레임 설정: 사용자 아이디
                .claim("rol", roles)                                      // 클레임 설정: 역할 정보
                .compact();                                                    // 최종적으로 토큰 생성
        log.info("jwt :" + jwt);
        return jwt;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {
        if (authHeader == null || authHeader.length() == 0) {
            return null;
        }
        try {
            String jwt = authHeader.replace(JwtConstants.TOKEN_PREFIX, ""); // "Bearer " + jwt  ➡ jwt 추출

            Jws<Claims> parsedToken = Jwts.parser() //jwt 파싱
                    .verifyWith(getShaKey())
                    .build()
                    .parseSignedClaims(jwt);
            log.info("parsedToken : " + parsedToken);

            //인증된 사용자 번호
            String userNo = parsedToken.getPayload().get("uno").toString();
            Long no = (userNo == null ? 0 : Long.parseLong(userNo));
            log.info("userNo : " + userNo);
            //인증된 사용자 아이디
            String userId = parsedToken.getPayload().get("uid").toString();
            log.info("userId : " + userId);
            //인증된 사용자 권한
            Claims claims = parsedToken.getPayload();
            Object roles = claims.get("rol");
            log.info("roles : " + roles);

            if (userId == null || userId.length() == 0) {
                return null;
            }

            Users user = new Users();
            Role role = new Role();
            UserRole userRole = new UserRole();
            user.setId(no);
            user.setUserId(userId);
            userRole.setUser(user);
            role.setId(1L);
            // 유저에 권한 담기
            List<UserRole> userRoles = ((List<?>) roles)
                    .stream()
                    .map(auth -> {
                        role.setRole(auth.toString());
                        userRole.setRole(role);
                        return userRole;
                    }).collect(Collectors.toList());
            user.setUserRoles(userRoles);

            //customUser 에 권한 담기
            List<SimpleGrantedAuthority> authorities = userRoles.stream()
                    .map((auth) -> new SimpleGrantedAuthority(auth.getRole().getRole()))
                    .collect(Collectors.toList());

            // 토큰 유효하면 name, nickname 담아주기
            try {
                Users userInfo = userRepository.findById(no)
                        .orElseThrow(() -> new IllegalArgumentException("no such id exist"));
                user.setName(userInfo.getName());
                user.setNickname(userInfo.getNickname());
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("토큰 유효 -> db 추가 정보 조회시 에러 발생");
            }

            UserDetails userDetails = new CustomUser(user);

            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        } catch (ExpiredJwtException e) {
            log.warn("expired jwt : {} failed : {}", authHeader, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("unsupported jwt : {} failed : {}", authHeader, e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("invalid jwt : {} failed : {}", authHeader, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("empty jwt : {} failed : {}", authHeader, e.getMessage());
        }
        return null;
    }

    /**
     * 토큰 유효성 검사 true : 유효 false : 만료 - 만료기한이 넘었는지 판단 유효 -> true 만료 -> false
     */

    public boolean validateToken(String jwt) {
        try {
            Jws<Claims> parsedToken = Jwts.parser() //jwt 파싱
                    .verifyWith(getShaKey())
                    .build()
                    .parseSignedClaims(jwt);

            log.info("토큰 만료기간");
            log.info("->" + parsedToken.getPayload().getExpiration());

            Date exp = parsedToken.getPayload().getExpiration();

            return !exp.before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("Token expired"); // 토큰 만료
            return false;
        } catch (JwtException e) {
            log.error("Token Tampered"); // 토큰 손상
            return false;
        } catch (NullPointerException e) {
            log.error("Token is null"); // 토큰 없음
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    //secret key -> signing key
    private byte[] getSigningKey() {
        return jwtProps.getSecretKey().getBytes();
    }

    //secret key -> (hmac - sha algorithm) -> signing key
    private SecretKey getShaKey() {
        return Keys.hmacShaKeyFor(getSigningKey());
    }
}

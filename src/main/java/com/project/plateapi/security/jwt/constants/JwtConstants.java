package com.project.plateapi.security.jwt.constants;


/**
 * - 로그인 필터 경로 - 토큰 헤더 - 토큰 헤더의 접두사 - 토큰 타입
 */
public class JwtConstants {
    public static final String AUTH_LOGIN_URL = "/login";
    public static final String TOKEN_HEADER = "Authorization";     // JWT 토큰을 HTTP 헤더에서 식별하는 데 사용되는 헤더 이름
    public static final String TOKEN_PREFIX = "Bearer ";     // JWT 토큰의 접두사. 일반적으로 "Bearer " 다음에 실제 토큰이 옴
    public static final String TOKEN_TYPE = "JWT";     // JWT 토큰의 타입을 나타내는 상수

}

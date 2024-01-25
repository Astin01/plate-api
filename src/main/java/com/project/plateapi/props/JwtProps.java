package com.project.plateapi.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 해당 클래스는 Spring Boot의 `@ConfigurationProperties`
// 어노테이션을 사용하여, application.properties(속성 설정 파일) 로부터
// JWT 관련 프로퍼티를 관리하는 프로퍼티 클래스입니다.
@Data
@Component
@ConfigurationProperties("com.project.plateapi")       // 경로 하위 속성들을 지정
public class JwtProps {

    // com.project.plateapi.secret-key ➡ secretKey : {인코딩된 시크릿 키} 주입
    private String secretKey;


}
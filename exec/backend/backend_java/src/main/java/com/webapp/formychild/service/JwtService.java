package com.webapp.formychild.service;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtService {

    public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private String signature = "VUETOKEN";
    private Long expireMin = 5L;

    static JwtBuilder jwtBuilder;

    // 로그인 성공시 부모 정보를 기반으로 JWTtoken을 생성하여 반환한다.
    public String createParent(ParentDto parent) {
        jwtBuilder = Jwts.builder();

        // Header 설정
        // 토큰의 타입으로 고정 값
        jwtBuilder.setHeaderParam("typ", "JWT");

        // Payload 설정
        jwtBuilder.setSubject("부모 로그인토큰") // 토큰 제목 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin)) // 유효기간 설정
                .claim("parent_id", parent.getParent_id()) // 담고싶은 정보 설정
                .claim("parent_email",parent.getParent_email());

        // signature 설정
        jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());

        // 직렬화 처리
        String jwt = jwtBuilder.compact();
        logger.info("Parent jwt : {" + jwt + "}");

        return jwt;
    }

    // 로그인 성공시 자식 정보를 기반으로 JWTtoken을 생성하여 반환한다.
    public String createChild(ChildDto child) {
        jwtBuilder = Jwts.builder();

        // Header 설정
        // 토큰의 타입으로 고정 값
        jwtBuilder.setHeaderParam("typ", "JWT");

        // Payload 설정
        jwtBuilder.setSubject("자식 로그인토큰") // 토큰 제목 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin)) // 유효기간 설정
                .claim("child_id", child.getChild_id());

        // signature 설정
        jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());

        // 직렬화 처리
        String jwt = jwtBuilder.compact();
        logger.info("child jwt : {" + jwt + "}");

        return jwt;
    }

    // 전달 받은 토큰이 제대로 생성된 것인지 확인, 문제가 있다면 RuntimeException 발생
    public void checkValid(String jwt){
        // 예외가 발생하지 않는다면 OK
        Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
    }

    // JWT Token을 분석하여 필요한 정보를 반환
    public Map<String, Object> get(String jwt){
        Jws<Claims> claims = null;

        try {
            claims = Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
        }catch (final Exception e){
            throw new RuntimeException();
        }

        logger.info("claims : {}", claims);

        // Claims는 Map의 구현체
        return claims.getBody();
    }
}

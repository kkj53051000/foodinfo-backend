package com.kp.foodinfo.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class JwtService {

    final String key = "hello";

    public static void main(String[] args) throws UnsupportedEncodingException {
        JwtService jwtService = new JwtService();

        String jwt = jwtService.createToken(2L);
        System.out.println(jwt);

        System.out.println(jwtService.verifyJWT(jwt));
    }

    public String createToken(long user_id) {

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("user_id", user_id);

        Long expiredTime = 1000 * 60L * 2L; // 토큰 유효 시간 (2시간)

        Date ext = new Date();
        ext.setTime(ext.getTime() + expiredTime);

        //토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user")
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return jwt;
    }

    //토큰 검증
    public Map<String, Object> verifyJWT(String jwt) throws UnsupportedEncodingException {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
                    .getBody();

            claimMap = claims;

            //Date expiration = claims.get("exp", Date.class);
            //String data = claims.get("data", String.class);

        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            System.out.println(e);
        } catch (Exception e) { // 그외 에러났을 경우
            System.out.println(e);
        }
        return claimMap;
    }

}

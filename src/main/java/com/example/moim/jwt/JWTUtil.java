package com.example.moim.jwt;

import com.example.moim.exception.InvalidTokenException;
import com.example.moim.user.dto.userDetailsImpl;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JWTUtil {
    
    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;
    
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;
    
    private final SecretKey secretKey;
    
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String ROLE_CLAIM = "role";
    
    public JWTUtil(@Value("${jwt.secretKey}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    
    public String createAccessToken(userDetailsImpl userDetails) {
        return Jwts.builder()
                .subject(ACCESS_TOKEN_SUBJECT)
                .claim(ID_CLAIM, userDetails.getUserId())
                .claim(ROLE_CLAIM, userDetails.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationPeriod))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }
    
    public String createRefreshToken(userDetailsImpl userDetails){
        return Jwts.builder()
                .subject(REFRESH_TOKEN_SUBJECT)
                .claim(ID_CLAIM, userDetails.getUserId())
                .claim(ROLE_CLAIM, userDetails.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }
    
    public Long getUserId(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(ID_CLAIM, Long.class);
        } catch (Exception e) {
            throw new InvalidTokenException("유효하지 않은 엑세스 토큰입니다.");
        }
    }

    public String getRole(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(ROLE_CLAIM, String.class);
        } catch (Exception e) {
            throw new InvalidTokenException("유효하지 않은 엑세스 토큰입니다.");
        }
    }
    
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}
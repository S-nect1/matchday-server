package com.example.moim.jwt;

import com.example.moim.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {
    @Value("${jwt.secretKey}")
    private String secret;
    
    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;
    
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;
    
    private SecretKey secretKey;
    
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    
    public JWTService(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    
    private String createAccessToken(final Long userId){
        Date now = new Date();
        Map<String, Object> userClaim = new HashMap<>();
        userClaim.put(ID_CLAIM, userId);
        
        return Jwts.builder()
                .subject(ACCESS_TOKEN_SUBJECT)
                .expiration(new Date(now.getTime() + accessTokenExpirationPeriod))
                .claims(userClaim)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
        
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
    
    public String createRefreshToken(){
        Date now = new Date();
        
        return Jwts.builder()
                .subject(REFRESH_TOKEN_SUBJECT)
                .expiration(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }
    
    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            
            claims.getPayload()
                    .getExpiration()
                    .before(new Date());
            
            return true;
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    };
    
    public Optional<Long> extractId(String accessToken){
        validateToken(accessToken);
        
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
            
            Long id = claims.get(ID_CLAIM, Long.class);
            return id != null ? Optional.of(id) : Optional.empty();
        } catch (Exception e) {
            throw new InvalidTokenException("유효하지 않은 엑세스 토큰입니다.");
        }
    }
    
    public String getUsername(String token) {
        
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }
    
    public String getRole(String token) {
        
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }
    
    public Boolean isExpired(String token) {
        
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    
    public String createJwt(String username, String role, Long expiredMs) {
        
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
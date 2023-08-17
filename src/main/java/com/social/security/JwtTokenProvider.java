package com.social.security;

import com.social.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiration.in.ms}")
    private long jwtExpirationInMs;

    private static final String ROLE = "role";
    private static final String USER_ID = "user_id";

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Map<String, Object> tokenData = generateTokenData(user);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setClaims(tokenData)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        if (claims.getExpiration().before(new Date())) {
            throw new CredentialsExpiredException("JWT token expired");
        }
        return Long.valueOf(String.valueOf(claims.get(USER_ID)));
    }

    public boolean validateToken(String accessToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(accessToken);
            return true;
        } catch (SignatureException ex) {
            throw new UnsupportedJwtException("Invalid JWT signature");
        } catch (ExpiredJwtException ex) {
            throw new CredentialsExpiredException("Expired JWT");
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new UnsupportedJwtException("Invalid JWT");
        }
    }

    public boolean isExpiredAccessToken(String accessToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(accessToken);
            return false;
        } catch (ExpiredJwtException ex) {
            log.info("Expired JWT");
            return true;
        }
    }

    private Map<String, Object> generateTokenData(User user) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put(USER_ID, user.getId());
        tokenData.put(ROLE, user.getRole().getName());
        return tokenData;
    }
}
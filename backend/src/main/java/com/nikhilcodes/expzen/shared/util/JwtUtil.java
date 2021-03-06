package com.nikhilcodes.expzen.shared.util;

import com.nikhilcodes.expzen.constants.NumberConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(String value) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, value, NumberConstants.JWT_AT_EXPIRY);
    }

    public String generateRefreshToken(String value) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, value, NumberConstants.JWT_RT_EXPIRY);
    }

    private String createToken(Map<String, Object> claims, String subject, int expiry) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + 1000L * expiry))
          .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
          .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

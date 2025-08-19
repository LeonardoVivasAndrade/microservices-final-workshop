package org.example.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.token.expiration.time}")
  private Integer expirationTime;

  public String generateToken(Authentication authentication) {
    return Jwts.builder()
      .setSubject(authentication.getName())
      .setIssuedAt(Date.from(Instant.now()))
      .setExpiration(new Date(new Date().getTime() + expirationTime))
      .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
      .compact();
  }
}

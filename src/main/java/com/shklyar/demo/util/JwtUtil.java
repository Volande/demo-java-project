package com.shklyar.demo.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

   @Value("${jwt.token.secret}")
   private String secret;

   @Value("${token-lifetime}")
   private int tokenLifetime;



   @PostConstruct
   protected void init() {
      secret = Base64.getEncoder().encodeToString(secret.getBytes());

      while(secret.getBytes().length<256){
         secret = secret + Base64.getEncoder().encodeToString(secret.getBytes());
      }
      System.out.println(secret);
   }



   public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
   }

   public Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
   }

   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
   }

   private Claims extractAllClaims(String token) {
      return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
   }

   public Boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
   }

   public String generateToken(UserDetails userDetails) {
      Map<String, Object> claims = new HashMap<>();
      return createToken(claims, userDetails.getUsername());
   }

   public String createToken(Map<String, Object> claims, String subject) {
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(subject)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() +tokenLifetime))
              .signWith(SignatureAlgorithm.HS256, secret).compact();
   }

   public boolean validateToken(String token, UserDetails userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }
}
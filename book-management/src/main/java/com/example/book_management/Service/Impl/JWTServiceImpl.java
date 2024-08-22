package com.example.book_management.Service.Impl;

import com.example.book_management.Service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {



    public String generateToken(UserDetails userDetails, Long userId) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date());
        Date date = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date newDate = calendar.getTime();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId", userId) // Include userId as a claim
                .setIssuedAt(date)
                .setExpiration(newDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefrechToken(HashMap<String, Object> extraClaims, UserDetails userDetails, Long userId) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date());
        Date date = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date newDate = calendar.getTime();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("userId", userId) // Include userId as a claim
                .setIssuedAt(date)
                .setExpiration(newDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> Long.parseLong(claims.get("userId").toString()));
    }



    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }


    private <T> T extractClaim (String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims (token);
        return claimsResolvers.apply(claims);
    }



    private Key getSignKey() {
        byte[] key= Decoders.BASE64.decode("413F4428472B4B6250655368566D597033733676397924422645294840406351");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setAllowedClockSkewSeconds(600000).setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date());
        Date date = calendar.getTime();
        return extractClaim(token,Claims::getExpiration).before(date);
    }


}

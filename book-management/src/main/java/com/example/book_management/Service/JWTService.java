package com.example.book_management.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;


public interface JWTService {

    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token,UserDetails userDetails);

    String generateRefrechToken(HashMap<String,Object> extraClaims, UserDetails userDetails);


}

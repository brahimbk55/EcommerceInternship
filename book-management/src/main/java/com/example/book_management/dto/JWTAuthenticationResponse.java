package com.example.book_management.dto;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {


    private String token;

    private String RefrechToken;


}

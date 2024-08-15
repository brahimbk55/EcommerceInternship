package com.example.book_management.Service;

import com.example.book_management.Entities.App_user;
import com.example.book_management.dto.JWTAuthenticationResponse;
import com.example.book_management.dto.RefrechTokenRequest;
import com.example.book_management.dto.SignInRequest;
import com.example.book_management.dto.SignUpRequest;

public interface AuthenticationService {


    App_user signup(SignUpRequest signUpRequest, Boolean isAdmin);

    JWTAuthenticationResponse signin(SignInRequest signInRequest);

    JWTAuthenticationResponse refrechToken(RefrechTokenRequest refrechTokenRequest);


}

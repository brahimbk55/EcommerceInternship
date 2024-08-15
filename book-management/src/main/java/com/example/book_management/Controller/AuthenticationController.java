package com.example.book_management.Controller;

import com.example.book_management.Entities.App_user;
import com.example.book_management.Service.AuthenticationService;
import com.example.book_management.dto.JWTAuthenticationResponse;
import com.example.book_management.dto.RefrechTokenRequest;
import com.example.book_management.dto.SignInRequest;
import com.example.book_management.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<App_user> sigup(@RequestBody SignUpRequest signUpRequest){

        return ResponseEntity.ok(authenticationService.signup(signUpRequest, false));

    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthenticationResponse>signin(@RequestBody SignInRequest signInRequest){

        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @PostMapping("/refrech")
    public ResponseEntity<JWTAuthenticationResponse>refrech(@RequestBody RefrechTokenRequest refrechTokenRequest){

        return ResponseEntity.ok(authenticationService.refrechToken(refrechTokenRequest));
    }


}

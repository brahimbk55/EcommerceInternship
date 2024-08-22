package com.example.book_management.Service.Impl;

import com.example.book_management.Entities.App_user;
import com.example.book_management.Entities.Role;
import com.example.book_management.Repository.UserRepository;
import com.example.book_management.Service.AuthenticationService;
import com.example.book_management.Service.JWTService;
import com.example.book_management.dto.JWTAuthenticationResponse;
import com.example.book_management.dto.RefrechTokenRequest;
import com.example.book_management.dto.SignInRequest;
import com.example.book_management.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public App_user signup(SignUpRequest signUpRequest, Boolean isAdmin){
        App_user appuser =new App_user();

        appuser.setEmail(signUpRequest.getEmail());
        appuser.setFirstname(signUpRequest.getFirstname());
        appuser.setLastname(signUpRequest.getLastname());
        if (isAdmin)
            appuser.setRole(Role.ADMIM);
        else
            appuser.setRole(Role.USER);
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        System.out.println("Encoded Password: " + encodedPassword);
        appuser.setPassword(encodedPassword);

        return userRepository.save(appuser);
    }


    public JWTAuthenticationResponse signin(SignInRequest signInRequest){
        System.out.println("Signin attempt for email: " + signInRequest.getEmail());

        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("invalid email or password"));

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            System.out.println("Retrieved empty password for user: " + signInRequest.getEmail());
        } else {
            System.out.println("Retrieved encoded password: " + user.getPassword());
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );

        var jwt = jwtService.generateToken(user,user.getId().longValue());
        var refreshToken = jwtService.generateRefrechToken(new HashMap<>(), user, user.getId().longValue());

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefrechToken(refreshToken);

        return jwtAuthenticationResponse;
    }


    public JWTAuthenticationResponse refrechToken(RefrechTokenRequest refrechTokenRequest){

        String userEmail=jwtService.extractUsername(refrechTokenRequest.getToken());
        App_user appuser =userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refrechTokenRequest.getToken(), appuser)){

            var jwt=jwtService.generateToken(appuser, appuser.getId().longValue());

            JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefrechToken(refrechTokenRequest.getToken());

            return jwtAuthenticationResponse;

        }
        return null;
    }

}

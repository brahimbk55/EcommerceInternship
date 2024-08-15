package com.example.book_management.Controller;

import com.example.book_management.Entities.App_user;
import com.example.book_management.Service.AuthenticationService;
import com.example.book_management.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String>SayHello(){
        return ResponseEntity.ok("H1 Admin");
    }

    @PostMapping("/signup")
    public ResponseEntity<App_user> sigup(@RequestBody SignUpRequest signUpRequest){

        return ResponseEntity.ok(authenticationService.signup(signUpRequest, true));

    }

}


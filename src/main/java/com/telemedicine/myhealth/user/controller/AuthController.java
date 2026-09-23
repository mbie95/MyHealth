package com.telemedicine.myhealth.user.controller;

import com.telemedicine.myhealth.res.Response;
import com.telemedicine.myhealth.user.dto.LoginRequest;
import com.telemedicine.myhealth.user.dto.LoginResponse;
import com.telemedicine.myhealth.user.dto.RegistrationRequest;
import com.telemedicine.myhealth.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<String >> register(@RequestBody @Valid RegistrationRequest registrationRequest){
        return ResponseEntity.ok(authService.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}

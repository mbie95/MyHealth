package com.telemedicine.myhealth.user.service;

import com.telemedicine.myhealth.res.Response;
import com.telemedicine.myhealth.user.dto.LoginRequest;
import com.telemedicine.myhealth.user.dto.LoginResponse;
import com.telemedicine.myhealth.user.dto.RegistrationRequest;
import com.telemedicine.myhealth.user.dto.ResetPasswordRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface AuthService {
    Response<String> register(RegistrationRequest request);
    Response<LoginResponse> login(LoginRequest loginRequest);
    Response<?> forgetPassword(String email);
    Response<?> updatePasswordViaResetCode(ResetPasswordRequest resetPasswordRequest);
    Response<LoginResponse> loginRegisterByGoogleOAuth2(OAuth2AuthenticationToken authenticationToken);
}

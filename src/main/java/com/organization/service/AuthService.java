package com.organization.service;

import com.organization.dto.LoginDto;
import com.organization.dto.RegisterDto;
import com.organization.dto.ChangePasswordRequest;
import com.organization.dto.JWTAuthResponse;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);

    void changePassword(ChangePasswordRequest changePasswordRequest);
}

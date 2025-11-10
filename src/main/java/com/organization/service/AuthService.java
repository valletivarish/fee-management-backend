package com.organization.service;

import com.organization.dto.LoginDto;
import com.organization.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}

package ecommerce_backend.service;

import ecommerce_backend.dto.LoginRequestDto;
import ecommerce_backend.dto.LoginResponseDto;
import ecommerce_backend.dto.RefreshTokenRequestDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);

    LoginResponseDto refreshToken(RefreshTokenRequestDto request);

    void logout(RefreshTokenRequestDto request);
}

package ecommerce_backend.controller;

import ecommerce_backend.dto.LoginRequestDto;
import ecommerce_backend.dto.LoginResponseDto;
import ecommerce_backend.dto.RefreshTokenRequestDto;
import ecommerce_backend.dto.UserResponseDto;
import ecommerce_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto request){
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponseDto refreshToken(
            @Valid @RequestBody RefreshTokenRequestDto request) {

        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public void logout(
            @Valid @RequestBody RefreshTokenRequestDto request) {

        authService.logout(request);
    }


}

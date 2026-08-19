package ecommerce_backend.service.impl;

import ecommerce_backend.dto.LoginRequestDto;
import ecommerce_backend.dto.LoginResponseDto;
import ecommerce_backend.dto.RefreshTokenRequestDto;
import ecommerce_backend.dto.UserResponseDto;
import ecommerce_backend.entity.RefreshToken;
import ecommerce_backend.entity.User;
import ecommerce_backend.mapper.UserMapper;
import ecommerce_backend.repository.UserRepository;
import ecommerce_backend.security.JwtService;
import ecommerce_backend.service.AuthService;
import ecommerce_backend.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ecommerce_backend.exception.InvalidCredentialsException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           RefreshTokenService refreshTokenService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        UserResponseDto userResponse = UserMapper.toResponseDto(user);

        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getId());


        LoginResponseDto response = LoginResponseDto.builder()
                .user(userResponse)
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();

        return response;
        }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto request) {

        RefreshToken oldRefreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken());

        User user = oldRefreshToken.getUser();

        refreshTokenService.revokeRefreshToken(oldRefreshToken);

        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user.getId());

        String newAccessToken =
                jwtService.generateToken(user.getEmail());

        UserResponseDto userResponse =
                UserMapper.toResponseDto(user);

        LoginResponseDto response = LoginResponseDto.builder()
                .user(userResponse)
                .token(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();

        return response;
    }

    @Override
    public void logout(RefreshTokenRequestDto request) {
        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken());

        refreshTokenService.revokeRefreshToken(refreshToken);

    }
}



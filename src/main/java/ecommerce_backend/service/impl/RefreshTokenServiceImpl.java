package ecommerce_backend.service.impl;

import ecommerce_backend.entity.RefreshToken;
import ecommerce_backend.entity.User;
import ecommerce_backend.exception.InvalidRefreshTokenException;
import ecommerce_backend.exception.ResourceNotFoundException;
import ecommerce_backend.repository.RefreshTokenRepository;
import ecommerce_backend.repository.UserRepository;
import ecommerce_backend.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .createdAt(LocalDateTime.now())
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new InvalidRefreshTokenException(
                                        "Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException(
                    "Refresh token has been revoked");
        }

        if (refreshToken.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            throw new InvalidRefreshTokenException(
                    "Refresh token has expired");
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public void revokeRefreshToken(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}
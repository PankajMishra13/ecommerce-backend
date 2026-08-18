package ecommerce_backend.service;

import ecommerce_backend.entity.RefreshToken;


public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyRefreshToken(String token);

    void revokeRefreshToken(RefreshToken refreshToken);


}

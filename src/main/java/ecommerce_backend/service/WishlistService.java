package ecommerce_backend.service;

import ecommerce_backend.dto.WishlistResponseDto;

public interface WishlistService {

    WishlistResponseDto getMyWishlist();

    WishlistResponseDto addItem(Long productId);

    void removeItem(Long productId);

    void clearWishlist();
}
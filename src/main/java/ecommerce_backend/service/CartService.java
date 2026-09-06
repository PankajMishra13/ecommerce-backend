package ecommerce_backend.service;

import ecommerce_backend.dto.CartItemRequestDto;
import ecommerce_backend.dto.CartResponseDto;

public interface CartService {

    CartResponseDto getMyCart();

    CartResponseDto addItemToCart(CartItemRequestDto request);

    CartResponseDto updateCartItem(Long cartItemId, Integer quantity);

    void removeCartItem(Long cartItemId);

    void clearCart();
}